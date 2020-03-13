import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;

public class SAP {

    private BreadthFirstDirectedPaths bfs[];
    private Digraph graph;

    public SAP(Digraph G){
        if(G == null) throw new IllegalArgumentException();
        bfs = new BreadthFirstDirectedPaths[G.V()];
        graph = new Digraph(G);
    }

    public int length(int v,int w){
        if(graph.V()<v || v<0)
            throw new IllegalArgumentException();

        if(graph.V()<w || w<0)
            throw new IllegalArgumentException();

        if(bfs[v] == null)
            bfs[v] = new BreadthFirstDirectedPaths(graph,v);

        if(bfs[w] == null)
            bfs[w] = new BreadthFirstDirectedPaths(graph,w);


        int length = -1;

        for(int i = 0;i<graph.V();++i)
        {
            if(bfs[v].hasPathTo(i) && bfs[w].hasPathTo(i)){
                int currlength = bfs[v].distTo(i) + bfs[w].distTo(i);
                if(length == -1 || (currlength!=-1&&currlength<length)) length = currlength;
            }
        }

        bfs[w] = null;
        bfs[v] = null;

        return length;
    }

    public int ancestor(int v,int w){
        if(graph.V()<v || v<0)
            throw new IllegalArgumentException();

        if(graph.V()<w || w<0)
            throw new IllegalArgumentException();

        if(bfs[v] == null)
            bfs[v] = new BreadthFirstDirectedPaths(graph,v);

        if(bfs[w] == null)
            bfs[w] = new BreadthFirstDirectedPaths(graph,w);


        int length = -1;
        int ancestor = -1;

        for(int i = 0;i<graph.V();++i)
        {
            if(bfs[v].hasPathTo(i) && bfs[w].hasPathTo(i)){
                int currlength = bfs[v].distTo(i) + bfs[w].distTo(i);
                if(length == -1 || (currlength!=-1&&currlength<length)) {length = currlength;ancestor = i;}
            }
        }

        bfs[w] = null;
        bfs[v] = null;

        return ancestor;


    }

    public int length(Iterable<Integer> v,Iterable<Integer> w){
        if(v == null || w == null) throw new IllegalArgumentException();
        int length = -1;

        for(int i : v){
            for(int j : w){
               int currlength = length(i,j);
               if(length == -1 || (currlength!=-1&&currlength<length))  length = currlength;
            }
        }

        return length;
    }

    public int ancestor(Iterable<Integer> v,Iterable<Integer> w){
        if(v == null || w == null) throw new IllegalArgumentException();
        int length = -1;
        int ancestor = -1;
        for(int i : v){
            for(int j : w){
                int currlength = length(i,j);
                if(length == -1 || (currlength!=-1&&currlength<length)){
                    length = currlength;
                    ancestor = ancestor(i,j);
                }
            }
        }
        return ancestor;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length   = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }

}
