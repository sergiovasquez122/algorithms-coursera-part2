import edu.princeton.cs.algs4.*;

public class WordNet {
    public WordNet(String synsets, String hypernyms){
        if(synsets == null || hypernyms == null){
            throw new IllegalArgumentException();
        }

        In synset_file = new In(synsets);
        while(!synset_file.isEmpty()){
            String[] a = synset_file.readLine().split(",");
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
        WordNet wordNet = new WordNet("/home/sergio/algorithms-coursera-part2/WordNet/src/synsets.txt", "");
    }
}
