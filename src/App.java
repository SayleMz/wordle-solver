import java.util.Set;
import dictionary.Dictionary;
import wordle.Game;
import wordle.Interface;
import wordle.interfaces.Colored;
import wordle.judges.Automatic;
import wordle.players.Bot;

public class App {
    public static void main(String[] args) throws Exception {
        Set<String> words = Dictionary.read("./fr.txt");
        Bot player = new Bot(Bot.FREQUENCY | Bot.UNIQUE);
        Automatic judge = new Automatic("FRANC");
        Interface ui = new Colored();
        new Game(words, judge, player, ui).run();
    }
}
