package wordle.interfaces;

import wordle.Interface;
import wordle.Judge.Correctness;

public class Colored implements Interface {
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String GRAY = "\u001B[37m";

    public void initial() {
        System.out.println("Welcome! ~ using Colored Terminal Interface");
    }

    public void ask() {
    }

    public void invalid(String message) {
        System.out.println(RED + "[!] invalid - " + message + RESET);
    }

    public void inputted(String proposition) {
    }

    public void display(String word, Correctness[] results) {
        for (int i = 0; i < results.length; i++) {
            switch (results[i]) {
                case CORRECT:
                    System.out.print(GREEN);
                    break;
                case MISPLACED:
                    System.out.print(YELLOW);
                    break;
                case INCORRECT:
                    System.out.print(GRAY);
                    break;
            }
            System.out.print(word.charAt(i) + RESET + ' ');
        }
        System.out.println();
    }

    public void won(int attempts) {
        System.out.println(GREEN + ":) You found the word in " + attempts + " guesses!" + RESET);
    }

    public void lost() {
        System.out.println(RED + ":( You lost" + RESET);
    }
}
