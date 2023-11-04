package alpha;

import java.util.NoSuchElementException;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class AlphaSet implements Set<Character> {
    private int set;
    private int size;

    private void assertCharacter(Character ch) {
        if (!Alpha.isValid(ch))
            throw new IllegalArgumentException();
    }

    public AlphaSet() {
        set = 0;
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        if (!(o instanceof Character))
            throw new IllegalArgumentException("Value must be a character");
        Character ch = (Character) o;
        assertCharacter(ch);
        int key = 1 << Alpha.code(ch);
        return (set & key) != 0;
    }

    @Override
    public Iterator<Character> iterator() {
        return new Iterator<Character>() {
            int current = 0;
            int bitSet = set;

            @Override
            public boolean hasNext() {
                return current < 26;
            }

            @Override
            public Character next() {
                while (current < 26) {
                    if ((bitSet & 1) == 1) {
                        bitSet >>>= 1;
                        current++;
                        return Alpha.toChar(current - 1);
                    }
                    bitSet >>>= 1;
                    current++;
                }
                throw new NoSuchElementException();
            }
        };
    }

    @Override
    public Object[] toArray() {
        Character[] arr = new Character[size];
        int bitSet = set;
        int index = 0;
        for (int i = 0; i < 26; i++) {
            if ((bitSet & 1) == 1) {
                arr[index] = Alpha.toChar(i);
                index++;
            }
            bitSet >>>= 1;
        }
        return arr;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length < size) {
            a = (T[]) Array.newInstance(a.getClass().getComponentType(), size);
        }
        Character[] result = (Character[]) a;
        int bitSet = set;
        int index = 0;
        for (int i = 0; i < 26; i++) {
            if ((bitSet & 1) == 1) {
                result[index] = Alpha.toChar(i);
                index++;
            }
            bitSet >>>= 1;
        }
        if (index < a.length) {
            a[index] = null;
        }
        return a;
    }

    @Override
    public boolean add(Character ch) {
        assertCharacter(ch);
        int key = 1 << Alpha.code(ch);
        boolean had = (set & key) == 0;
        set |= key;
        if (had) {
            size++;
        }
        return had;
    }

    @Override
    public boolean remove(Object o) {
        if (!(o instanceof Character)) {
            throw new IllegalArgumentException("Value must be a character");
        }
        Character ch = (Character) o;
        assertCharacter(ch);
        int key = 1 << Alpha.code(ch);
        boolean had = (set & key) != 0;

        if (had) {
            set &= ~key;
            size--;
        }

        return had;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!contains(o)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends Character> c) {
        boolean modified = false;
        for (Character ch : c) {
            if (add(ch)) {
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean modified = false;
        Iterator<Character> it = iterator();
        while (it.hasNext()) {
            Character ch = it.next();
            if (!c.contains(ch)) {
                it.remove();
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean modified = false;
        for (Object o : c) {
            if (remove(o)) {
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public void clear() {
        set = 0;
        size = 0;
    }
}

