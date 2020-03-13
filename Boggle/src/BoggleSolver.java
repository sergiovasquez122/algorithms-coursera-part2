import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdOut;

public class BoggleSolver {
    private TrieSET ts = new TrieSET();
    private BoggleBoard board;
    private boolean visited[][];
    private static int positions[][] = {{1,1},{1,0},{1,-1},{-1,1},{0,1},{-1,0},{0,-1},{-1,-1}};

    public BoggleSolver(String [] dictionary)
    {
       for(String s : dictionary) ts.add(s);
    }

    public Iterable<String> getAllValidWords(BoggleBoard board){
        SET<String> set = new SET<>();
        this.board = board;
        visited = new boolean[board.rows()][board.cols()];
        for(int i = 0;i<board.rows();++i)
            for(int j = 0;j<board.cols();++j) {
                dfs(i,j, "", set);
            }
        return set;
    }

    private void dfs(int i,int j, String pre,SET<String> q){
        if(visited[i][j]) return;

        char letter = board.getLetter(i,j);
        pre = pre+ (letter=='Q' ? "QU" : letter);

        if(pre.length()>2 && ts.contains(pre)) q.add(pre);

        if(!ts.isPrefix(pre)) return;

        visited[i][j] = true;

        for(int pos[] : positions)
        {
            int newX = i+pos[0];
            int newy = j+pos[1];
            if(valid(newX,newy)) dfs(newX,newy,pre,q);
        }
        visited[i][j] = false;
    }

    private boolean valid(int x,int y)
    {
        return (x>=0 && x< board.rows()) && (y>=0 && y< board.cols());
    }

    public int scoreOf(String word){
        if(ts.contains(word)){
            int l = word.length();
            if(l == 3 || l == 4)return 1;
            else if(l==5) return 2;
            else if(l==6) return 3;
            else if(l==7) return 5;
            else if(l>=8) return 11;
        }
        return 0;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        String [] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard(args[1]);
        int score = 0;
        int size = 0;
        for(String word : solver.getAllValidWords(board)){
            StdOut.println(word);
            score += solver.scoreOf(word);
            size++;
        }
        StdOut.println("Score = " + score);
        StdOut.println(size);
    }
}
