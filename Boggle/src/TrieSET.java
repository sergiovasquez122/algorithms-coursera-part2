import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

public class TrieSET {

    static int R = 26;
    private Node root;
    int size = 0;

    private static class Node{
        boolean isString = false;
        Node next[]  = new Node[R];
    }

    public void add(String key){
        root = add(root,key,0);
    }

    public boolean isPrefix(String prefix){
        return get(prefix) != null;
    }

    public Node add(Node x,String key,int d){
        if(x==null) x = new Node();
        if(d==key.length()) {x.isString = true;return x;}
        char c = key.charAt(d);
        x.next[c-'A'] = add(x.next[c-'A'],key,d+1);
        return x;
    }

    private Node get(String key){
        Node x = root;
        for(int d = 0;d<key.length();++d){
            if(x==null) return null;
            char c = key.charAt(d);
            x = x.next[c-'A'];
        }
        return x;
    }

    public boolean contains(String key){
        Node x = get(key);
        if(x==null) return false;
        return x.isString;
    }

    public boolean isEmpty(){
        return size==0;
    }

    public int size(){
        return size;
    }

    private void collect(Node x, String pre,Queue<String> q) {
        if(x==null) return;
        if(x.isString) q.enqueue(pre);
        for(char c = 'A';c<='Z';c++) collect(x.next[c-'A'],pre+c,q);
    }

    public Iterable<String> keysWithPrefix(String prefix){
        Queue<String> q = new Queue<>();
        collect(get(prefix),prefix,q);
        return q;
    }

    public Iterable<String> keys(){
        return keysWithPrefix("");
    }


    public Iterable<String> keysThatMatch(String pat){
            Queue<String> q = new Queue<>();
            collect(root,"",pat,q);
            return q;
    }

    private void collect(Node x,String pre,String pat,Queue<String> q){
        if(x==null) return;
        int d = pre.length();
        if(pat.length()==d&&x.isString) q.enqueue(pre);
        if(pat.length()==d)return;
        for(char c = 'A';c<='Z';c++)
            if(c==pat.charAt(d) || pat.charAt(d) == '.')
                collect(x.next[c],pre+c,pat,q);

    }

    public String longestPrefixOf(String s)
    {
        int length = search(root, s, 0, 0);
        return s.substring(0, length);
    }

    private int search(Node x, String s, int d, int length)
    {
        if (x == null) return length;
        if (x.isString) length = d;
        if (d == s.length()) return length;
        char c = s.charAt(d);
        return search(x.next[c], s, d+1, length);
    }

    public void delete(String key){
        root = delete(root,key,0);
    }

    private Node delete(Node x,String key,int d){
        if(x==null)return null;
        if(d==key.length())
            x.isString = false;
        else{
            char c = key.charAt(d);
            x.next[c-'A'] = delete(x.next[c-'A'],key,d+1);
        }

        if(x.isString) return x;

        for(char c = 'A';c<='Z';c++){
            Node y = x.next[c-'A'];
            if(y.isString ) return x;
        }

        return null;
    }

    public static void main(String[] args) {
        TrieSET set = new TrieSET();
        In in = new In(args[0]);
        String [] dictionary = in.readAllStrings();

        for(String word : dictionary)
            set.add(word);

        int count = 0;
        for(String word : set.keys()) {
            StdOut.println(word);
            count++;
        }
        StdOut.println(count);
    }

}
