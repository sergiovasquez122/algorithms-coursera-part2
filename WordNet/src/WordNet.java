import edu.princeton.cs.algs4.*;

import java.util.HashMap;

public class WordNet {

    private final HashMap<Integer, String> synsets;
    private final HashMap<String, Bag<Integer>> synMap;
    /**
     * Constructor that takes the name of two input files
     * @param synsets the file contains all noun synsets in WordNet
     * @param hypernyms the file contains all hypernym relationships
     */
    public WordNet(String synsets, String hypernyms){
        if(synsets == null || hypernyms == null){
            throw new IllegalArgumentException();
        }

    }

    /**
     *
     * @return all WordNetNouns
     */
    public Iterable<String> nouns(){
        return null;
    }

    /**
     * @param word a nonnull String
     * @return true if the word is a noun in the synset false otherwise
     */
    public boolean isNoun(String word){
        if(word == null){
            throw new IllegalArgumentException();
        }
        return false;
    }

    /**
     * @param nounA
     * @param nounB
     * @return distance between nounA and nounB in the Wordnet Graph
     */
    public int distance(String nounA, String nounB){
        if(!isNoun(nounA) || !isNoun(nounB)){
            throw new IllegalArgumentException();
        }
        return 0;
    }

    /**
     * @param nounA
     * @param nounB
     * @return a synset that is the common ancestor two nounA and nounB in a shortest
     * ancestral path
     */
    public String sap(String nounA, String nounB){
        if(nounA == null || nounB == null){
            throw new IllegalArgumentException();
        }
        return "";
    }


    public static void main(String[] args) {
        WordNet wordNet = new WordNet("/home/sergio/algorithms-coursera-part2/WordNet/src/synsets.txt", "");
    }
}
