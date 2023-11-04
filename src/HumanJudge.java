import java.util.Set;
import dictionary.Dictionary;
import wordle.Game;
import wordle.Interface;
import wordle.interfaces.Monochromatic;
import wordle.judges.Human;
import wordle.players.Bot;

public class HumanJudge {
    public static void main(String[] args) {
        Set<String> words = Dictionary.read("./eng.txt");
        Bot player = new Bot(Bot.FREQUENCY | Bot.UNIQUE);
        Human judge = new Human();
        Interface ui = new Monochromatic();
        new Game(words, judge, player, ui).run();
    }
}
