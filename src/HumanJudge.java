import java.util.Set;
import dictionary.Dictionary;
import wordle.Game;
import wordle.Interface;
import wordle.interfaces.Monochromatic;
import wordle.judges.Human;
import wordle.players.Bot;

/**
 * Human-feedback driven Wordle Solver algorithm based on the French wordlist.
 * Can be tested on https://wordle.louan.me which uses the same dictionary.
 * To give feedback, simply write for each character:
 * - 'C' for correctly placed character
 * - 'M' for misplaced character
 * - 'I' for incorrect character
 */
public class HumanJudge {
    public static void main(String[] args) {
        Set<String> words = Dictionary.read("./fr.txt");
        Bot player = new Bot(Bot.FREQUENCY | Bot.UNIQUE);
        Human judge = new Human();
        Interface ui = new Monochromatic();
        new Game(words, judge, player, ui).run();
    }
}
