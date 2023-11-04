package wordle.players;

import java.util.Scanner;
import wordle.Player;
import wordle.Judge.Correctness;

public class Human extends Player {
    private Scanner scanner;
    private String prompt;

    public Human(String prompt) {
        this.prompt = prompt == null ? "" : prompt;
    }

    protected void setup() {
        scanner = new Scanner(System.in);
    }

    private String baseGuess() {
        System.out.print(prompt);
        String word = scanner.nextLine();
        return word;
    }

    public String initialGuess() {
        return baseGuess();
    }

    public String guess(Correctness[] results) {
        return baseGuess();
    }

    public void close() {
        scanner.close();
    }
}
