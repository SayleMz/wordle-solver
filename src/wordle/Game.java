package wordle;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import alpha.Alpha;
import wordle.Judge.Correctness;

public class Game {
    private Judge judge;
    private Player player;
    private Interface userInterface;
    private Set<String> words;
    private int wordSize;

    public Game(Collection<String> words, Judge judge, Player player, Interface userInterface) {
        if (words.size() == 0)
            throw new IllegalArgumentException("words can't be empty");

        wordSize = words.iterator().next().length();
        this.words = new HashSet<>();
        for (String word : words) {
            if (word.length() != wordSize)
                throw new IllegalArgumentException("every words must be the same length");
            if (!Alpha.isValid(word))
                throw new IllegalArgumentException(word + " is not a valid word");
            this.words.add(word);
        }

        this.judge = judge;
        this.player = player;
        this.userInterface = userInterface;

        this.judge.constructor(wordSize, this.words);
        this.player.constructor(wordSize, this.words);
    }

    public int run() {
        Correctness[] results = null;
        userInterface.initial();
        while (!judge.isFinished()) {
            userInterface.ask();
            String word = player.play(Optional.ofNullable(results));
            userInterface.inputted(word);
            try {
                results = judge.judge(word);
            } catch (Exception error) {
                userInterface.invalid(error.getMessage());
                continue;
            }
            userInterface.display(word, results);
        }
        userInterface.won(judge.getAttempts());
        return judge.getAttempts();
    }
}
