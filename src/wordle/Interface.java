package wordle;

import wordle.Judge.Correctness;

public interface Interface {
    public void initial();
    public void ask();
    public void invalid(String message);
    public void inputted(String proposition);
    public void display(String word, Correctness[] results);
    public void won(int attempts);
    public void lost();
}
