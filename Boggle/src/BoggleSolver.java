import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdOut;

public class BoggleSolver {

    private TrieSET ts;
    /**
     *   // Initializes the data structure using the given array of strings as the dictionary.
     *   // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
     * @param dictionary
     */
    public BoggleSolver(String[] dictionary){
        ts = new TrieSET();
        for(String s : dictionary)
            ts.add(s);
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board){
        return null;
    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word){
        if(!ts.contains(word) || word.length() <= 2) return 0;
        int l = word.length();
        int add_one = word.charAt(0) == 'Q' ? 1 : 0;
        if( l == 3 || l == 4 ) return 1 + add_one;
        else if( l == 6 ) return 3 + add_one;
        else if( l == 7 ) return 5 + add_one;
        else return 11 + add_one;
    }
}
