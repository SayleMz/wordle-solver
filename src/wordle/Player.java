package wordle;

import java.util.Optional;
import java.util.Set;

import wordle.Judge.Correctness;

public abstract class Player {
    protected Set<String> words;
    protected int wordSize;

    public Player() {
    }

    protected abstract void setup();

    // alternative constructor method, to be called by Game instance
    public void constructor(int wordSize, Set<String> words) {
        this.wordSize = wordSize;
        this.words = words;
        setup();
    }

    protected abstract String initialGuess();
    protected abstract String guess(Correctness[] results);

    public String play(Optional<Correctness[]> results) {
        return results.isPresent() ? guess(results.get()) : initialGuess();
    }

    // Called when the game is finished
    protected abstract void close();

    public void refresh() {
        setup();
    }
}