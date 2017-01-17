package cs240.byu.edu.spellcorrector_startingcode_android.StudentPackage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Cory on 9/12/16.
 */
public class mySpellCorrector implements ISpellCorrector {
    Dictionary myDictionary = new Dictionary();

    public void useDictionary(InputStreamReader dictionaryFile) {
        BufferedReader br = new BufferedReader(dictionaryFile);
        try {
            myDictionary.clear();
            String line;
            while ((line = br.readLine()) != null) {
                myDictionary.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String suggestSimilarWord(String inputWord) throws NoSimilarWordFoundException{
        myDictionary.wordsOneEditDistance(inputWord);   // Do the best word algorithm
        String best = myDictionary.getBestWord();       // Store best word
//        myDictionary = new Dictionary();                // Reset dictionary
        if (best == null) throw new NoSimilarWordFoundException();  // See if best word is null
        return best;
    }
}
