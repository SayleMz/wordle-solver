import java.util.Random;
import java.util.Set;
import dictionary.Dictionary;
import wordle.Game;
import wordle.Interface;
import wordle.interfaces.Colored;
import wordle.judges.Automatic;
import wordle.players.Bot;

/**
 * Demo.java is a simple demonstration of the Wordle Solver algorithm.
 * It chooses a random word from the French word set dictionary,
 * and lets the solver try to guess it.
 */
public class Demo {
    public static String choice(Set<String> words) {
        Random rand = new Random();
        int index = rand.nextInt(words.size());
        int i = 0;
        String secret = null;
        for (String word : words) {
            if (index == i) {
                secret = word;
                break;
            }
            i++;
        }
        return secret;
    }

    public static void main(String[] args) throws Exception {
        Set<String> words = Dictionary.read("./fr.txt");
        String secret = choice(words);
        System.out.println("Secret word: " + secret);
        Bot player = new Bot(Bot.FREQUENCY | Bot.UNIQUE);
        Automatic judge = new Automatic(secret);
        Interface ui = new Colored();
        new Game(words, judge, player, ui).run();
    }
}
