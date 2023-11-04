package wordle.players;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import wordle.Player;
import wordle.Judge.Correctness;

public class Bot extends Player {
    class FrequencyComparator implements Comparator<String> {
        private int[] freqs;

        public FrequencyComparator(int[] freqs) {
            this.freqs = freqs;
        }

        private int calculatePoints(String word) {
            int points = 0;
            for (int i = 0; i < word.length(); i++)
                points += freqs[word.charAt(i) - 'A'];
            return points;
        }

        @Override
        public int compare(String word1, String word2) {
            return Integer.compare(calculatePoints(word2), calculatePoints(word1));
        }
    }

    class UniqueCharacterComparator implements Comparator<String> {
        private int calculateUniqueCharacters(String word) {
            HashSet<Character> freqs = new HashSet<>();

            for (int i = 0; i < word.length(); i++)
                freqs.add(word.charAt(i));

            return freqs.size();
        }

        @Override
        public int compare(String word1, String word2) {
            int unique1 = calculateUniqueCharacters(word1);
            int unique2 = calculateUniqueCharacters(word2);
            return Integer.compare(unique2, unique1);
        }
    }

    public static final int DEFAULT = 1 << 0;
    public static final int SHUFFLE = 1 << 1;
    public static final int LEXICOGRAPHIC = 1 << 2;
    public static final int FREQUENCY = 1 << 3;
    public static final int UNIQUE = 1 << 4;

    private Set<Character>[] constraints;
    private String[] possibilities;
    private String[] cache;

    private final int sortingOptions;

    public Bot() {
        cache = null;
        sortingOptions = DEFAULT;
    }

    public Bot(int sortingOptions) {
        cache = null;
        this.sortingOptions = sortingOptions;
    }

    public Bot(String[] words, int sortingOptions) {
        this.sortingOptions = sortingOptions;
        cache = words;
        sort(cache);
    }

    private void sort(String[] array) {
        if ((sortingOptions & SHUFFLE) != 0) {
            Random rand = new Random();
            for (int i = array.length - 1; i > 0; i--) {
                int j = rand.nextInt(i + 1);
                String temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }

        if ((sortingOptions & LEXICOGRAPHIC) != 0)
            Arrays.sort(array);

        if ((sortingOptions & FREQUENCY) != 0) {
            int[] freqs = new int[26];

            for (int i = 0; i < array.length; i++)
                for (int j = 0; j < wordSize; j++)
                    freqs[array[i].charAt(j) - 'A']++;

            Arrays.sort(array, new FrequencyComparator(freqs));
        }

        if ((sortingOptions & UNIQUE) != 0)
            Arrays.sort(array, new UniqueCharacterComparator());
    }

    protected void setup() {
        constraints = new Set[wordSize];
        for (int i = 0; i < wordSize; i++) {
            constraints[i] = new HashSet<Character>();
            for (char ch = 'A'; ch <= 'Z'; ch++) {
                constraints[i].add(ch);
            }
        }

        if (cache != null) {
            possibilities = cache.clone();
        } else {
            possibilities = new String[words.size()];
            int i = 0;
            for (String word : words) {
                possibilities[i++] = word;
            }
            sort(possibilities);
        }
    }

    public String initialGuess() {
        return possibilities[0];
    }

    public String guess(Correctness[] results) {
        // fetch last used words
        String word = possibilities[0];

        Map<Character, Integer> freq = new HashMap<>();
        Set<Character> misplaceds = new HashSet<>();

        for (int i = 0; i < results.length; i++) {
            final char ch = word.charAt(i);
            switch (results[i]) {
                case CORRECT:
                    Set<Character> locked = new HashSet<>();
                    locked.add(ch);
                    constraints[i] = locked;
                    freq.put(ch, freq.getOrDefault(ch, 0) + 1);
                    break;

                case MISPLACED:
                    constraints[i].remove(ch);
                    freq.put(ch, freq.getOrDefault(ch, 0) + 1);
                    misplaceds.add(ch);
                    break;

                case INCORRECT:
                    for (int j = 0; j < constraints.length; j++) {
                        if (constraints[j].size() > 1) {
                            constraints[j].remove(ch);
                        }
                    }
                    break;
            }
        }

        // worst case: 5 misplaceds chars --> O(N²)
        for (Character misplaced : misplaceds) {
            for (int i = 0; i < wordSize; i++) {
                if (misplaced != word.charAt(i) && constraints[i].size() > 1) {
                    constraints[i].add(misplaced);
                }
            }
        }

        int size = possibilities.length;
        int newSize = 0;
        String[] matching = new String[size];
        for (int i = 0; i < size; i++) {
            boolean match = true;
            for (int j = 0; j < wordSize; j++) {
                if (!constraints[j].contains(possibilities[i].charAt(j))) {
                    match = false;
                    break;
                }
            }

            // break outer loop
            if (!match)
                continue;

            Map<Character, Integer> wordFreq = new HashMap<>();
            for (int j = 0; j < wordSize; j++) {
                final char ch = possibilities[i].charAt(j);
                wordFreq.put(ch, wordFreq.getOrDefault(ch, 0) + 1);
            }

            for (Map.Entry<Character, Integer> entry : freq.entrySet()) {
                Character letter = entry.getKey();
                Integer occurences = entry.getValue();
                if (occurences > wordFreq.getOrDefault(letter, 0)) {
                    match = false;
                    break;
                }
            }

            if (!match)
                continue;

            matching[newSize++] = possibilities[i];
        }

        possibilities = new String[newSize];
        for (int i = 0; i < newSize; i++) {
            possibilities[i] = matching[i];
        }

        return possibilities[0];
    }

    protected void close() {

    }
}
