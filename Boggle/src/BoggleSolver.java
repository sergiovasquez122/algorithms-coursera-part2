import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdOut;

public class BoggleSolver {

    private TrieSET ts;
    private TrieSET words;
    private boolean[][] marked;
    BoggleBoard b;
    /**
     *   // Initializes the data structure using the given array of strings as the dictionary.
     *   // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
     * @param dictionary
     */
    public BoggleSolver(String[] dictionary){
        ts = new TrieSET();
        words = new TrieSET();
        for(String s : dictionary)
            ts.add(s);
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board){
        int m = board.rows();
        int n = board.cols();
        marked = new boolean[m][n];
        this.b = board;
        for(int i = 0;i < m;i++){
            for(int j = 0;j < n;j++){
                dfs("", i, j);
            }
        }
        return words.keys();
    }

    private void dfs(String pre, int x, int y){
        marked[x][y] = true;
        pre += b.getLetter(x, y) == 'Q' ? "Qu"  : b.getLetter(x, y);
        if(pre.length() > 2 && ts.contains(pre)) words.add(pre);
        if(!ts.isPrefix(pre)) return;
        int[][] positions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
        for(int[] pos : positions){
            int new_x = pos[0];
            int new_y = pos[1];
            if(new_x >= 0 && new_x < b.rows() && new_y >= 0 && new_y < b.cols() && !marked[new_x][new_y]){
                dfs(pre, new_x, new_y);
            }
        }
        marked[x][y] = false;
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


    public static void main(String[] args) {
        In in = new In(args[0]);
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard(args[1]);
        int score = 0;
        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);
    }

}
