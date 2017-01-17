package cs240.byu.edu.evilhangman_android.StudentPackage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;



/**
 * Created by Cory on 9/19/16.
 */
public class EvilHangman implements StudentEvilHangmanGameController {
    public ArrayList<String> myDictionary = new ArrayList<>();
    private Set<Character> usedLetters = new HashSet<>();

    private HashMap<String, HashSet<String>> partitions = new HashMap<>();

    private String currentWord;

    public int numGuesses;


    public EvilHangman () {
        currentWord = "";
    }    // Constructor. Need parameters?

    @Override
    public GAME_STATUS getGameStatus() {
        if (numGuesses > 0) {
            return GAME_STATUS.NORMAL;
        } else if (numGuesses == 0) {
           for (int i = 0; i < currentWord.length(); i++ ) {
               if (currentWord.charAt(i) == '-') {
                   return GAME_STATUS.PLAYER_LOST;
               }
           }
            return GAME_STATUS.PLAYER_WON;
        }
        return GAME_STATUS.PLAYER_LOST;

    }

    @Override
    public int getNumberOfGuessesLeft() {
        return numGuesses;
    }

    @Override
    public String getCurrentWord() {
        return currentWord;
    }

    private void createCurrentWord(int wordLength) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < wordLength; i++) {
            sb.append('-');
        }
        currentWord = sb.toString();
    }

    @Override
    public Set<Character> getUsedLetters() {
        return usedLetters;
    }

    @Override
    public void setNumberOfGuesses(int numberOfGuessesToStart) {
        numGuesses = numberOfGuessesToStart;
    }

    @Override
    public void startGame(InputStreamReader dictionary, int wordLength) {
        createCurrentWord(wordLength);
        BufferedReader br = new BufferedReader(dictionary);
        try {
            myDictionary.clear();
            String line;
            while ((line = br.readLine()) != null) {
                if (line.length() == wordLength) {
                    myDictionary.add(line.toLowerCase());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public Set<String> makeGuess(char guess) throws GuessAlreadyMadeException {

        if (guessMade(guess)) throw new GuessAlreadyMadeException();
        partitions.clear();
        numGuesses--;

        StringBuilder key = new StringBuilder();
        for (int i = 0; i < myDictionary.size(); i++) {
            String currWord = myDictionary.get(i);
            for (int j = 0; j < currWord.length(); j++) {
                if (currWord.charAt(j) == guess) {
                    key.append(guess);
                } else {
                    key.append('-');
                }
            }
            addToPartition(key.toString(), currWord);
            key = new StringBuilder();
        }
        updateDictionary();

        Set<String> returnSet = new HashSet<>();
        returnSet.addAll(myDictionary);
        return returnSet;
    }

    private void updateDictionary() {
        int maxSetSize = 0;
        String maxKey = null;
        HashSet<String> maxSet = null;
        for (Map.Entry<String, HashSet<String>> entry : partitions.entrySet()) {
            if (entry.getValue().size() > maxSetSize) {
                maxSetSize = entry.getValue().size();
                maxKey = entry.getKey();
                maxSet = entry.getValue();
            } else if (entry.getValue().size() == maxSetSize) {
                if (isBlank(entry.getKey())) {
                    maxKey = entry.getKey();
                    maxSet = entry.getValue();
                } else if (getNumGuessedLetters(entry.getKey()) < getNumGuessedLetters(maxKey)) {
                    maxKey = entry.getKey();
                    maxSet = entry.getValue();
                } else if (getNumGuessedLetters(entry.getKey()) == getNumGuessedLetters(maxKey)) {
                    // rightmost comparison
                    if (rightCompareIndex(entry.getKey(), maxKey)) {
                        maxKey = entry.getKey();
                        maxSet = entry.getValue();
                    }
                    // otherwise don't change max key/val
                }
            }
        }

        ArrayList<String> newDict = new ArrayList<>();
        newDict.addAll(maxSet);
        myDictionary = newDict;
        updateCurrentWord(maxKey);
    }

    private void updateCurrentWord (String key) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < currentWord.length(); i++) {
            if (key.charAt(i) == '-') {
                sb.append(currentWord.charAt(i));
            } else {
                sb.append(key.charAt(i));
            }
        }
        currentWord = sb.toString();
    }

    private boolean rightCompareIndex(String entryKey, String maxKey) {
        int entryIndex = 0;
        int maxIndex = 0;
        for (int i = entryKey.length()-1; i>=0; i--) {
            if (entryKey.charAt(i) != '-') {
                entryIndex = i;
                break;
            }
        }
        for (int i = maxKey.length()-1; i>=0; i--) {
            if (maxKey.charAt(i) != '-') {
                maxIndex = i;
                break;
            }
        }

        if (entryIndex == maxIndex)
            return rightCompareIndex(entryKey.substring(0, entryIndex), maxKey.substring(0, maxIndex));

        return entryIndex > maxIndex;
    }
    private boolean isBlank(String entryKey) {
        StringBuilder blankString = new StringBuilder();
        for (int i = 0; i < entryKey.length(); i++) {
            blankString.append('-');
        }
        return entryKey == blankString.toString();
    }

    private int getNumGuessedLetters(String key) {
        int count = 0;
        for (int i = 0; i < key.length(); i++) {
            if (key.charAt(i) != '-') {
                count++;
            }
        }
        return count;
    }

    private boolean guessMade(char guess) {
        if (usedLetters.contains(guess)) {
            return true;
        } else {
            usedLetters.add(guess);
            return false;
        }
    }

    private void addToPartition(String keyString, String currWord) {
        if (partitions.containsKey(keyString)) {
            partitions.get(keyString).add(currWord.toLowerCase());
        } else {
            HashSet<String> set = new HashSet<>();
            set.add(currWord.toLowerCase());
            partitions.put(keyString, set);
        }
    }
}