package wordle.interfaces;

import wordle.Interface;
import wordle.Judge.Correctness;

public class Shadow implements Interface {
    public void initial() {
    }

    public void ask() {
    }

    public void invalid(String message) {
    }

    public void inputted(String proposition) {
    }

    public void display(String word, Correctness[] results) {
    }

    public void won(int attempts) {
    }

    public void lost() {
    }
}

