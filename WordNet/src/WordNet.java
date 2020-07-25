import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.DirectedCycle;

public class WordNet {
    public WordNet(String synsets, String hypernyms){
        if(synsets == null || hypernyms == null){
            throw new IllegalArgumentException();
        }
    }

    public Iterable<String> nouns(){
        return null;
    }

    public boolean isNoun(String word){
        if(word == null){
            throw new IllegalArgumentException();
        }
        return false;
    }

    public int distance(String nounA, String nounB){
        if(!isNoun(nounA) || !isNoun(nounB)){
            throw new IllegalArgumentException();
        }
        return 0;
    }

    public String sap(String nounA, String nounB){
        if(nounA == null || nounB == null){
            throw new IllegalArgumentException();
        }
        return "";
    }

    public static void main(String[] args) {

    }
}
