package wordle.interfaces;

import wordle.Interface;
import wordle.Judge.Correctness;

public class Monochromatic implements Interface {
    public void initial() {
        System.out.println("Welcome! ~ using Monochromatic Terminal Interface");
    }

    public void ask() {

    }

    public void invalid(String message) {
        System.out.println("[!] invalid - " + message);
    }

    public void inputted(String proposition) {
        System.out.println(proposition);
    }

    public void display(String word, Correctness[] results) {
        for (Correctness result : results) {
            switch (result) {
                case CORRECT:
                    System.out.print('+');
                    break;
                case MISPLACED:
                    System.out.print('~');
                    break;
                case INCORRECT:
                    System.out.print('x');
                    break;
            }
        }
        System.out.println();
    }

    public void won(int attempts) {
        System.out.println("You found the word in " + attempts + " guesses!");
    }

    public void lost() {
        System.out.println("You lost");
    }
}
