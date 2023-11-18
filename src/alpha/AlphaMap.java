package alpha;

import java.util.*;

public class AlphaMap<T> implements Map<Character, T> {
    private int size;
    private Object[] map;

    private void assertCharacter(Character ch) {
        if (!Alpha.isValid(ch))
            throw new IllegalArgumentException();
    }

    public AlphaMap() {
        size = 0;
        map = new Object[26];
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
    public boolean containsKey(Object key) {
        if (!(key instanceof Character))
            throw new IllegalArgumentException("Key must be a character");
        Character ch = (Character) key;
        assertCharacter(ch);
        return map[Alpha.code(ch)] != null;
    }

    @Override
    public boolean containsValue(Object value) {
        for (Object entry : map) {
            if (entry != null && entry.equals(value)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public T get(Object key) {
        if (!(key instanceof Character))
            throw new IllegalArgumentException("Key must be a character");
        Character ch = (Character) key;
        assertCharacter(ch);
        return (T) map[Alpha.code(ch)];
    }

    @Override
    public T put(Character key, T value) {
        assertCharacter(key);
        int index = Alpha.code(key);
        T oldValue = (T) map[index];
        map[index] = value;
        if (oldValue == null) {
            size++;
        }
        return oldValue;
    }

    @Override
    public T remove(Object key) {
        if (!(key instanceof Character))
            throw new IllegalArgumentException("Key must be a character");
        Character ch = (Character) key;
        assertCharacter(ch);
        int index = Alpha.code(ch);
        T oldValue = (T) map[index];
        if (oldValue != null) {
            map[index] = null;
            size--;
        }
        return oldValue;
    }

    @Override
    public void putAll(Map<? extends Character, ? extends T> m) {
        for (Entry<? extends Character, ? extends T> entry : m.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void clear() {
        size = 0;
        map = new Object[26];
    }

    @Override
    public Set<Character> keySet() {
        AlphaSet keySet = new AlphaSet();
        for (int i = 0; i < 26; i++) {
            if (map[i] != null) {
                keySet.add(Alpha.toChar(i));
            }
        }
        return keySet;
    }

    @Override
    public Collection<T> values() {
        List<T> values = new ArrayList<>(size);
        for (int i = 0; i < 26; i++) {
            if (map[i] != null) {
                values.add((T) map[i]);
            }
        }
        return values;
    }

    @Override
    public Set<Entry<Character, T>> entrySet() {
        Set<Entry<Character, T>> entrySet = new HashSet<>(size);
        for (int i = 0; i < 26; i++) {
            if (map[i] != null) {
                entrySet.add(new AbstractMap.SimpleEntry<>(Alpha.toChar(i), (T) map[i]));
            }
        }
        return entrySet;
    }

}
