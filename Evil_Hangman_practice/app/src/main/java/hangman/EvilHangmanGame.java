package hangman;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Cory on 2/4/17.
 */
public class EvilHangmanGame implements IEvilHangmanGame {
    public int numGuesses;
    public String currentWord;
    public int numGuessesLeft;
    public char currentGuess;
    public ArrayList<String> myDictionary = new ArrayList<>();
    public Set<Character> usedLetters = new HashSet<>();
    public HashMap<String, HashSet<String>> partitions = new HashMap<>();

    public enum GAME_STATUS{PLAYER_WON,PLAYER_LOST, NORMAL;}

    public EvilHangmanGame() {
        currentWord = "";
    }

    @Override
    public void startGame(File dictionary, int wordLength) {
        createCurrentWord(wordLength);
        try {
            FileReader fr = new FileReader(dictionary);
            BufferedReader br = new BufferedReader(fr);
            myDictionary.clear();
            String line;
            while((line = br.readLine()) !=null) {
                if (line.length() == wordLength) {
                    myDictionary.add(line.toLowerCase());
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setNumGuessesLeft(int guessesToStart) {
        numGuessesLeft = guessesToStart;
    }

    public GAME_STATUS getGameStatus() {
        if (numGuessesLeft > 0) {
            return GAME_STATUS.NORMAL;
        } else if (numGuessesLeft == 0) {
            for (int i = 0; i < currentWord.length(); i++) {
                if (currentWord.charAt(i) == '-') {
                    return GAME_STATUS.PLAYER_LOST;
                }
            }
            return GAME_STATUS.PLAYER_WON;
        }
        return GAME_STATUS.PLAYER_LOST;
    }

    public boolean guessMade(char guess) {
        if (usedLetters.contains(guess)) {
            return true;
        } else {
            usedLetters.add(guess);
            return false;
        }
    }

    public void printUsedLetters() {
        StringBuilder sb = new StringBuilder();
        System.out.println("Used Letters:\n");
        for (char c : usedLetters) {
            sb.append(c).append(" ");
        }
        System.out.println(sb.toString());
    }

    @Override
    public Set<String> makeGuess(char guess) throws GuessAlreadyMadeException {
        currentGuess = guess;
        if (guessMade(guess)) throw new GuessAlreadyMadeException();
        partitions.clear();
        numGuessesLeft--;
        int numCorrect = 0;
        StringBuilder keyBuild = new StringBuilder();
        for (int i = 0; i< myDictionary.size(); i++) {
            String currWord = myDictionary.get(i);
            for (int j = 0; j < currWord.length(); j++) {
                if (currWord.charAt(j) == guess) {
                    keyBuild.append(guess);
                    numCorrect++;
                } else {
                    keyBuild.append('-');
                }
            }
            addToPartition(keyBuild.toString(), currWord);
            keyBuild = new StringBuilder();
        }
        updateDictionary();

        return null;
    }
    public void updateDictionary() {
        String maxKey = null;
        HashSet<String> maxSet = null;
        for (Map.Entry<String, HashSet<String>> entry : partitions.entrySet()) {
            if (entry.getValue().size() > maxSet.size()) {
                maxKey = entry.getKey();
                maxSet = entry.getValue();
            } else if (entry.getValue().size() == maxSet.size()) {
                maxKey = entry.getKey();
                maxSet = entry.getValue();
            }
        }

    }

    private boolean isBlank() {

    }

    private boolean rightCompareIndex(String entryKey, String maxKey) {
        int entryIndex = 0;
        int maxIndex = 0;

        for (int i = entryKey.length() -1; i >= 0; i--) {
            if (entryKey.charAt(i) != '-') {
                entryIndex = i;
                break;
            }
        }
        for (int j = maxKey.length() -1; j >=0; j--) {
            if (entryKey.charAt(j) != '-') {
                maxIndex = j;
                break;

            }
        }
        if (entryIndex == maxIndex) {
            return rightCompareIndex(entryKey.substring(0, entryIndex), maxKey.substring(0, entryIndex));
        }
        return entryIndex > maxIndex;
    }

    private void createCurrentWord(int wordLength) {
        StringBuilder currentWord = new StringBuilder();
        for (int i = 0; i < wordLength; i++) {
            currentWord.append('-');
        }
        this.currentWord = currentWord.toString();
    }

    private void addToPartition(String key, String currWord) {
        if (partitions.containsKey(key)) {
            partitions.get(key).add(currWord);
        } else {
            HashSet<String> set = new HashSet<>();
            set.add(currWord.toLowerCase());
            partitions.put(key, set);
        }
    }

}
