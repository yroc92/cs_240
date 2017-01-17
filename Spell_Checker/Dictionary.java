package cs240.byu.edu.spellcorrector_startingcode_android.StudentPackage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

/**
 * Created by Cory on 9/12/16.
 */
public class Dictionary implements ITrie {
    private int wordcount;
    private int nodecount;

    public Node rootNode = new Node();
    public String bestWord;

    private HashSet<String> goodWords = new HashSet<>();
    private HashSet<String> badWords = new HashSet<>();
    private HashSet<String> dictionary = new HashSet<>();

    public Dictionary () {
        wordcount = 0;
        nodecount = 1;
    }
    public void add(String word) {
        String wordL = word.toLowerCase();  // convert to lowercase
        Node currNode = rootNode;         // create you current node
        dictionary.add(wordL);              // recursively add your lowercase word

        // iterate through the length of the word
        for (int i = 0; i < wordL.length(); i++) {
            int charVal = wordL.charAt(i) - 'a';            // simplify
            if (currNode.children[charVal] == null) {       // if the child node is empty
                currNode.children[charVal] = new Node();    // ... make it a new node
                nodecount++;                                // increment the node count
            }
            currNode = currNode.children[charVal];          // set current node to the child

            if (i == wordL.length() - 1) {      // If iteration reaches end of word
                if (currNode.frequency == 0) {      // ... if this word doesn't exist in the dict
                    wordcount++;                        // ... increment word count
                }
                currNode.frequency++;           // Increment frequency of this node
            }
        }

    }

    public ITrie.INode find(String word) {
        String wordL = word.toLowerCase();
        Node currNode = rootNode;
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

    public int getWordCount() {
        return wordcount;
    }

    public int getNodeCount() {
        return nodecount;
    }

    public String getBestWord() {
        return bestWord;
    }



    @Override
    public String toString() {
        ArrayList<String> sortedDict = new ArrayList<>(dictionary);
        Collections.sort(sortedDict);
        StringBuilder sb = new StringBuilder();
        for (Object obj : sortedDict) {
            sb.append(obj).append("\n");
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        return wordcount * wordcount * 29 + nodecount;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (o.getClass() != this.getClass()) return false;

        Dictionary obj = (Dictionary) o;
        if (obj.getNodeCount() != this.getNodeCount()) return false;
        if (obj.getWordCount() != this.getWordCount()) return false;
        if (!this.rootNode.equals(obj.rootNode)) return false;

        return true;
    }

    public void clear () {
        dictionary.clear();
    }

    private void deleteCheck(String word) {
        String wordL = word.toLowerCase();
        StringBuilder copy = new StringBuilder(wordL);
        for (int i = 0; i < wordL.length(); i++) {
            copy.deleteCharAt(i);

            ITrie.INode myNode = find(copy.toString());
            if (myNode != null) {
                if (myNode.getValue() != 0) {
                    goodWords.add(copy.toString());
                }
            }
            badWords.add(copy.toString());
            copy = new StringBuilder(wordL);
        }
    }

    private void transpositionCheck(String word) {
        String wordL = word.toLowerCase();
        StringBuilder copy = new StringBuilder(wordL);
        for (int i = 0; (i+1) < wordL.length(); i++) {
            StringBuilder transposed = new StringBuilder();
            transposed.append(copy.substring(0,i));
            transposed.append(copy.charAt(i+1));
            transposed.append(copy.charAt(i));
            transposed.append(copy.substring(i+2, wordL.length()));

            ITrie.INode myNode = find(transposed.toString());
            if (myNode != null) {
                if (myNode.getValue() != 0) {
                    goodWords.add(transposed.toString());
                }
            }
            badWords.add(transposed.toString());
        }
    }

    private void alterationCheck(String word) {
        String wordL = word.toLowerCase();
        StringBuilder copy = new StringBuilder(wordL);
        for (int i = 0; i < wordL.length(); i++) {
            for (int z = 0; z < 26; z++) {
                copy.setCharAt(i, (char) ('a'+z));
                ITrie.INode myNode = find(copy.toString());
                if (myNode != null) {
                    if(myNode.getValue() != 0) {
                        goodWords.add(copy.toString());
                    }
                }
                badWords.add(copy.toString());
                copy = new StringBuilder(wordL);
            }
        }
    }

    private void insertionCheck(String word) {
        String wordL = word.toLowerCase();
        StringBuilder copy = new StringBuilder(wordL);
        for (int i = 0; i <= wordL.length(); i++) {
            for (int z = 0; z < 26; z++) {
                copy.insert(i, (char) ('a'+z));
                ITrie.INode myNode = find(copy.toString());
                if (myNode != null) {
                    if(myNode.getValue() != 0) {
                        goodWords.add(copy.toString());
                    }
                }
                badWords.add(copy.toString());
                copy = new StringBuilder(wordL);
            }
        }
    }

    private void setBestWord() {
        bestWord = null;
        if (goodWords.isEmpty()) return;
        for (String candidate : goodWords) {
            if (bestWord == null) bestWord = candidate;
            else {
                INode bestNode = find(bestWord);
                INode candidateNode = find(candidate);
                if (bestNode.getValue() < candidateNode.getValue() ||
                        (bestNode.getValue() == candidateNode.getValue() &&
                                bestWord.compareTo(candidate) > 0)) {
                    bestWord= candidate;
                }
            }
        }
    }

    public void wordsOneEditDistance(String word) {
        goodWords.clear();
        badWords.clear();
        INode myNode = find(word);

        if (myNode != null && myNode.getValue() != 0) {
            bestWord = word;
            return;
        }

        deleteCheck(word);
        insertionCheck(word);
        transpositionCheck(word);
        alterationCheck(word);

        setBestWord();

        if (bestWord == null) {
            HashSet<String> previous = new HashSet<>(badWords);
            goodWords.clear();
            for (String s : previous) {
                deleteCheck(s);
                insertionCheck(s);
                transpositionCheck(s);
                alterationCheck(s);
            }
            setBestWord();
        }
    }



    public class Node implements ITrie.INode {
        Node[] children = new Node[26];
        int frequency;

        public Node() {
            frequency = 0;
        }
        public int getValue() {
            return frequency;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null) return false;
            if (this.getClass() != o.getClass()) return false;

            Node obj = (Node) o;
            if (obj.frequency != this.frequency) return false;
            return Arrays.deepEquals(this.children, obj.children);
        }
    }

}
