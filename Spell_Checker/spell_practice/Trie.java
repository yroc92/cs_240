package spell_practice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

/**
 * Created by Cory on 2/3/17.
 */

  public class Trie implements ITrie {
    private int wordCount;
    private int nodeCount;

    public WordNode rootNode = new WordNode();
    public String bestWord;

    public HashSet<String> goodWords = new HashSet<>();
    public HashSet<String> badWords = new HashSet<>();
    private HashSet<String> dictionary = new HashSet<>();

    public Trie() {
        wordCount = 0;
        nodeCount = 1;
    }

    public void add(String word) {
        String wordL = word.toLowerCase();
        WordNode currNode = rootNode;
        dictionary.add(wordL);
        // iterate through length of word
        for (int i = 0; i < wordL.length(); i++) {
            int charVal = wordL.charAt(i) - 'a';
            if (currNode.children[charVal] == null) {
                currNode.children[charVal] = new WordNode();
                nodeCount++;
            }
            currNode = currNode.children[charVal];
            if (i == wordL.length() - 1) {
                if (currNode.frequency == 0) {
                    wordCount++;
                }
                currNode.frequency++;
            }
        }
    }
    /**
     * Searches the trie for the specified word
     *
     * @param word The word being searched for
     *
     * @return A reference to the trie node that represents the word,
     * 			or null if the word is not in the trie
     */
    public ITrie.INode find(String word) {
        String wordL = word.toLowerCase();
        WordNode currNode = rootNode;
        for (int i = 0; i < wordL.length(); i++) {
            if (currNode.children[wordL.charAt(i) - 'a'] == null) {
                return null;
            } else {
                currNode = currNode.children[wordL.charAt(i) - 'a'];
            }
        }
        if (currNode.getValue() == 0) return null;
        return currNode;
    }

    public int getWordCount() { return wordCount; }

    public int getNodeCount() { return nodeCount; }

    @Override
    public String toString() {
        ArrayList<String> sortedDictionary = new ArrayList<>(dictionary);
        Collections.sort(sortedDictionary);
        StringBuilder sb = new StringBuilder();
        for (Object obj : sortedDictionary) {
            sb.append(obj).append("\n");
        }
        return sb.toString();
    }

    @Override
    public int hashCode() { return this.wordCount * this.wordCount * 29 + this.nodeCount; }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (this.getClass() != o.getClass()) return false;
        Trie obj = (Trie) o;
        if (obj.getNodeCount() != this.getNodeCount()) return false;
        if (obj.getWordCount() != this.getWordCount()) return false;
        return (this.rootNode.equals(obj.rootNode));
    }

    public void wordsOneEditDistance(String word) {
        goodWords.clear();
        badWords.clear();
        INode myNode = find(word);
        if (myNode != null && myNode.getValue() !=0) {
            bestWord = word;
            return;
        }

        deleteCheck(word);
        insertCheck(word);
        transpositionCheck(word);
        alterationCheck(word);

        setBestWord();

        if (bestWord == null) {
            HashSet<String> previous = new HashSet<>(badWords);
            goodWords.clear();
            for (String s : previous) {
                deleteCheck(s);
                insertCheck(s);
                transpositionCheck(s);
                alterationCheck(s);
            }
            setBestWord();
        }
    }

    void setBestWord() {
        bestWord = null;
        if (goodWords.isEmpty()) return;
        for (String candidate : goodWords) {
            if (bestWord == null) bestWord = candidate;
            else {
                INode bestNode = find(bestWord);
                INode candidateNode = find(candidate);
                if (bestNode.getValue() < candidateNode.getValue() ||
                        (bestNode.getValue() == candidateNode.getValue() && bestWord.compareTo(candidate) > 0)) {
                    bestWord = candidate;
                }
            }
        }
    }

    private void alterationCheck(String word) {
        String wordL = word.toLowerCase();
        StringBuilder copy = new StringBuilder(wordL);
        for (int i = 0; i < wordL.length(); i++) {
            for (int j = 0; j < 26; j++) {
                copy.setCharAt(i, (char)(j + 'a'));
                ITrie.INode node = find(copy.toString());
                if (node != null && node.getValue() != 0) {
                    goodWords.add(copy.toString());
                }
                badWords.add(copy.toString());
                copy = new StringBuilder(wordL);

            }
        }
    }

    private void transpositionCheck(String word) {
        String wordL = word.toLowerCase();
        StringBuilder copy = new StringBuilder(wordL);
        for (int i = 0; (i+1) < wordL.length(); i++) {
            StringBuilder transposed = new StringBuilder();
            transposed.append(copy.substring(0, i));
            transposed.append(copy.substring(i+1));
            transposed.append(copy.substring(i));
            transposed.append(copy.substring(i+2, wordL.length()));


            ITrie.INode node = find(transposed.toString());
            if (node != null && node.getValue() != 0) {
                goodWords.add(copy.toString());
            }
            badWords.add(copy.toString());
            copy = new StringBuilder(wordL);


        }
    }

    private void insertCheck(String word) {
        String wordL = word.toLowerCase();
        StringBuilder copy = new StringBuilder(wordL);
        for (int i = 0; i <= wordL.length(); i++) {
            for (int j = 0; j < 26; j++) {
                copy.insert(i, (char)(j + 'a'));
                ITrie.INode node = find(copy.toString());
                if (node != null && node.getValue() != 0) {
                    goodWords.add(copy.toString());
                }
                badWords.add(copy.toString());
                copy = new StringBuilder(wordL);

            }
        }
    }

    private void deleteCheck(String word) {
        String wordL = word.toLowerCase();
        StringBuilder copy = new StringBuilder(wordL);
        for (int i = 0; i < wordL.length(); i++) {
            copy.deleteCharAt(i);
            ITrie.INode node = find(copy.toString());
            if (node != null && node.getValue() != 0) {
                goodWords.add(copy.toString());
            }
            badWords.add(copy.toString());
            copy = new StringBuilder(wordL);
        }
    }



  public class WordNode implements ITrie.INode {
      WordNode[] children = new WordNode[26];
      int frequency;

      public int getValue() {
          return frequency;
      }

      public boolean equals(Object o) {
          if (o == null) return false;
          if (o.getClass() != this.getClass()) return false;
          WordNode obj = (WordNode) o;
          if (obj.getValue() != this.getValue()) return false;
          return (Arrays.deepEquals(this.children, obj.children));
      }
  }


}
