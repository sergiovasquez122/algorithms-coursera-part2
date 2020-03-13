import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.DirectedCycle;

public class WordNet {

    private ST<Integer,String> idMap;
    private ST<String,SET<Integer>> nounMap;
    private Digraph graph;
    private SAP sap;


    public WordNet(String synsets, String hypernyms){
        if(synsets == null || hypernyms == null)
            throw new IllegalArgumentException();

        In in = new In(synsets);
        idMap = new ST<>();
        nounMap = new ST<>();
        while(in.hasNextLine()){
            String[] fields = in .readLine().split(",");
            String[] nouns = fields[1].split(" ");
            int id = Integer.parseInt(fields[0]);
            idMap.put(id,fields[1]);
            for(String s : nouns) {
                if(!nounMap.contains(s))nounMap.put(s,new SET<>());
                nounMap.get(s).add(id);
            }
        }

        in = new In(hypernyms);
        graph = new Digraph(nounMap.size());

        while(in.hasNextLine()){
            String items[] = in.readLine().split(",");
            int id = Integer.parseInt(items[0]);
            for(int i = 1;i<items.length;++i)
                graph.addEdge(id,Integer.parseInt(items[i]));
        }

        DirectedCycle cyclefinder = new DirectedCycle(graph);

        if(cyclefinder.hasCycle()) throw new IllegalArgumentException();
        sap = new SAP(graph);
    }

    /**
     * return all wordnet nouns
     */
    public Iterable<String> nouns(){
        return nounMap.keys();
    }

    /**
     * return true if the word is in the wordnet
     * database else false
     */
    public boolean isNoun(String word){
        return nounMap.contains(word);
    }

    public int distance(String nounA,String nounB){
        if(!nounMap.contains(nounA) || !nounMap.contains(nounB))
            throw new IllegalArgumentException();

        SET<Integer> a = nounMap.get(nounA);
        SET<Integer> b = nounMap.get(nounB);
        return sap.length(a,b);
    }

    public String sap(String nounA,String nounB){
        if(!nounMap.contains(nounA) || !nounMap.contains(nounB))
            throw new IllegalArgumentException();

        SET<Integer> a = nounMap.get(nounA);
        SET<Integer> b = nounMap.get(nounB);
        return idMap.get(sap.ancestor(a,b));
    }

    public static void main(String[] args) {
        WordNet wordnet = new WordNet("synsets.txt","hypernyms.txt");


    }
}
