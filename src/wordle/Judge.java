package wordle;

import java.util.Set;

import alpha.Alpha;

public abstract class Judge {
    static public enum Correctness {
        CORRECT,
        MISPLACED,
        INCORRECT
    }

    protected Set<String> words;
    protected int wordSize;
    protected boolean finished;
    private int attempts;

    protected abstract void setup();

    public void constructor(int wordSize, Set<String> words) {
        this.wordSize = wordSize;
        this.words = words;
        this.finished = false;
        attempts = 0;
        setup();
    }

    protected abstract Correctness[] judgeWord(String word);

    public Correctness[] judge(String word) {
        if (!words.contains(word)) {
            throw new IllegalArgumentException("unknown word");
        }
        if (word.length() != wordSize) {
            throw new IllegalArgumentException("word " + word + " do not match the specified word size");
        }

        if (!Alpha.isValid(word)) {
            throw new IllegalArgumentException("word must contain only uppercase alphabetic characters");
        }

        attempts++;

        return judgeWord(word);
    }

    public Set<String> getWords() {
        return words;
    }

    public int getAttempts() {
        return attempts;
    }

    public int getWordSize() {
        return wordSize;
    }

    public boolean isFinished() {
        return finished;
    }
}
