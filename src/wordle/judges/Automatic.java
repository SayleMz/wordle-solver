package wordle.judges;

import java.util.HashMap;
import java.util.Map;

import wordle.Judge;

public class Automatic extends Judge {

    private final String secret;

    public Automatic(String secret) {
        this.secret = secret;
    }

    protected void setup() {
        if (!words.contains(secret))
            throw new IllegalArgumentException("secret word do not exists in words");
    }

    @Override
    protected Correctness[] judgeWord(String word) {
        Correctness[] results = new Correctness[wordSize];

        // establish the frequency map of the secret word
        Map<Character, Integer> freq = new HashMap<>();
        for (int i = 0; i < wordSize; i++) {
            char ch = secret.charAt(i);
            freq.put(ch, freq.getOrDefault(ch, 0) + 1);
        }

        // first distinguish correctly placed letters from others
        boolean found = true;
        for (int i = 0; i < wordSize; i++) {
            char ch = word.charAt(i);
            if (ch == secret.charAt(i)) {
                results[i] = Correctness.CORRECT;
                freq.put(ch, freq.get(ch) - 1);
            } else {
                results[i] = Correctness.INCORRECT;
                found = false;
            }
        }

        // if never met an incorrect letter we can assume word is the secret word
        if (found) {
            finished = true;
            return results;
        }

        // using the previously established frequency map, compute the misplaced characters
        for (int i = 0; i < wordSize; i++) {
            if (results[i] == Correctness.CORRECT)
                continue;
            char ch = word.charAt(i);
            if (freq.getOrDefault(ch, 0) > 0) {
                results[i] = Correctness.MISPLACED;
                freq.put(ch, freq.get(ch) - 1);
            }
        }

        return results;
    }
}
