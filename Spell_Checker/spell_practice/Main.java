package spell_practice;

import java.io.IOException;

/**
 * Created by Cory on 2/3/17.
 */
public class Main {

    public static void main(String[] args) throws IOException, SpellCorrector.NoSimilarWordFoundException {
        String dictionaryFile = args[0];
        String word = args[1];

        SpellCorrector corrector = new SpellCorrector();
        corrector.useDictionary(dictionaryFile);

        String suggestion = corrector.suggestSimilarWord(word);
        System.out.println("Suggestion is: " + suggestion);
    }


}
