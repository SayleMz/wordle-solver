import java.util.Set;
import dictionary.Dictionary;
import wordle.Game;
import wordle.Interface;
import wordle.interfaces.Shadow;
import wordle.judges.Automatic;
import wordle.players.Bot;

/**
 * BotStats runs the Wordle Solver algorithm on a word sample defined by WORDS_SAMPLE.
 * Words used are defined by WORDS_FP, which corresponds to a filepath to a text file representing a set of words.
 * Data are then printed on the terminal as follows:
 > * TESTED: The total number of words tested (WORDS_SAMPLE)
 > * SOLVED: The number of words that the algorithm solved in fewer than 6 attempts
 > * FAILED: The number of words that the algorithm failed to solve
 > * FAULTS: The number of unexpected errors the algorithm generated
 > * RATE %: The success rate in %
 > * ~TRIES: The average number of attempts
 */
public class BotStats {
    public static void main(String[] args) throws Exception {
        final String WORDS_FP = "eng.txt";
        final int WORD_SAMPLE = 2500;

        Set<String> words = Dictionary.read(WORDS_FP);
        assert (WORD_SAMPLE > 0 && WORD_SAMPLE <= words.size()) : "invalid word sample";

        // For better performance, we use a cache that will not need to be sorted for each word.
        String[] cache = new String[words.size()];
        int i = 0;
        for (String word : words)
            cache[i++] = word;

        int attempts = 0;
        int failed = 0;

        Interface ui = new Shadow();
        Bot player = new Bot(cache, Bot.FREQUENCY | Bot.UNIQUE);
        for (i = 0; i < WORD_SAMPLE; i++) {
            final String secret = cache[i];

            Automatic judge = new Automatic(secret);
            int score = new Game(words, judge, player, ui).run();
            if (score > 6)
                failed++;
            attempts += score;
            player.refresh();
        }

        StringBuilder report = new StringBuilder();
        report.append(String.format("* TESTED: %d%n", WORD_SAMPLE));
        report.append(String.format("* SOLVED: %d%n", WORD_SAMPLE - failed));
        report.append(String.format("* FAILED: %d%n", failed));
        report.append(String.format("* FAULTS: %d%n", 0));
        report.append(String.format("* RATE %%: %.2f%%%n", (double) (WORD_SAMPLE - failed) / WORD_SAMPLE * 100));
        report.append(String.format("* ~TRIES: ~%.2f%n", (double) attempts / WORD_SAMPLE));
        System.out.println(report.toString());

    }
}
