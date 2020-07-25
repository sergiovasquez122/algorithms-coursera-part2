import edu.princeton.cs.algs4.*;

import java.util.HashMap;

public class WordNet {

    private HashMap<Integer, String> synSets;
    private HashMap<String, Bag<Integer>> synMap;
    private Digraph G;
    /**
     * Constructor that takes the name of two input files
     * @param synsets the file contains all noun synsets in WordNet
     * @param hypernyms the file contains all hypernym relationships
     */
    public WordNet(String synsets, String hypernyms){
        if(synsets == null || hypernyms == null){
            throw new IllegalArgumentException();
        }
        synSets = new HashMap<>();
        synMap = new HashMap<>();
        int V = readSynsets(synsets);
        G = new Digraph(V);
        readHypernyms(hypernyms);
        DirectedCycle cycleFinder = new DirectedCycle(G);
        if(cycleFinder.hasCycle())
            throw new IllegalArgumentException();
        int vertices_with_degree_zero = 0;
        for(int v = 0;v < G.V();++v){
            if(G.outdegree(v) == 0){
                vertices_with_degree_zero++;
            }
        }
        if(vertices_with_degree_zero != 1){
            throw new IllegalArgumentException();
        }
    }

    private void readHypernyms(String hypernyms){
        In in = new In(hypernyms);
        while(in.hasNextLine()){
            String[] parts = hypernyms.split(",");
            int v = Integer.parseInt(parts[0]);
            for(int i = 1;i < parts.length;++i){
                G.addEdge(v, Integer.parseInt(parts[i]));
            }
        }
    }

    private int readSynsets(String synsets){
        In in = new In(synsets);
        int count = 0;
        while(in.hasNextLine()){
            count++;
            String[] parts = in.readLine().split(",");
            String[] nouns = parts[1].split(" ");
            int id = Integer.parseInt(parts[0]);

            synSets.put(id, parts[1]);
            for(String noun : nouns){
                if(synMap.containsKey(noun)){
                    synMap.get(noun).add(id);
                } else{
                    synMap.put(noun, new Bag<>());
                    synMap.get(noun).add(id);
                }
            }
        }
        return count;
    }

    /**
     *
     * @return all WordNetNouns
     */
    public Iterable<String> nouns(){
        return synMap.keySet();
    }

    /**
     * @param word a nonnull String
     * @return true if the word is a noun in the synset false otherwise
     */
    public boolean isNoun(String word){
        if(word == null){
            throw new IllegalArgumentException();
        }
        return synMap.containsKey(word);
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
        WordNet wordNet = new WordNet("/home/sergio/algorithms-coursera-part2/WordNet/src/synsets.txt", "/home/sergio/algorithms-coursera-part2/WordNet/src/hypernyms.txt");
    }
}
