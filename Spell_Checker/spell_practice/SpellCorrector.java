package spell_practice;

/**
 * Created by Cory on 2/3/17.
 */


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class SpellCorrector implements  ISpellCorrector{
    Trie myTrie = new Trie();

    @SuppressWarnings("serial")
    public static class NoSimilarWordFoundException extends Exception {
    }

    /**
     * Tells this <code>ISpellCorrector</code> to use the given file as its dictionary
     * for generating suggestions.
     * @param dictionaryFileName File containing the words to be used
     * @throws IOException If the file cannot be read
     */
    public void useDictionary(String dictionaryFileName) throws IOException {
        FileReader fr = new FileReader(new File(dictionaryFileName));
        BufferedReader br = new BufferedReader(fr);
        String line;
        while ((line = br.readLine()) != null) {
            myTrie.add(line);
        }
    }

    /**
     * Suggest a word from the dictionary that most closely matches
     * <code>inputWord</code>
     * @param inputWord
     * @return The suggestion
     * @throws NoSimilarWordFoundException If no similar word is in the dictionary
     */
    public String suggestSimilarWord(String inputWord) throws NoSimilarWordFoundException {
        myTrie.wordsOneEditDistance(inputWord);
        String best = myTrie.bestWord;
        if (best == null) throw new NoSimilarWordFoundException();
        System.out.println("Goodowords:");
        for (String s : myTrie.goodWords) {
            System.out.println(s);

        }
        System.out.println("Badwords:");
        for (String s : myTrie.badWords) {
            System.out.println(s);
        }
        return best;
    }

}

