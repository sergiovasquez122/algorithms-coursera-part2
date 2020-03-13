import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class BurrowsWheeler {

    public static void transform(){
        String s = BinaryStdIn.readString();
        CircularSuffixArray cfa = new CircularSuffixArray(s);
        StringBuilder sb = new StringBuilder();
        int pre=-1;
        for(int i = 0;i<cfa.length();++i) {
            int index = cfa.index(i);
            if(index==0) pre = i;
            sb.append(s.charAt((index+(cfa.length()-1))%cfa.length()));
        }
        if(pre==-1) throw new RuntimeException();
        BinaryStdOut.write(pre);
        BinaryStdOut.write(sb.toString());
        BinaryStdOut.flush();
    }

    public static void inverseTransform(){
        int R = 256;
        int first = BinaryStdIn.readInt();
        String s = BinaryStdIn.readString();
        int N = s.length();
        int [] aux = new int[N];
        int []count = new int[R+1];

        for(int i = 0;i<N;++i)
            count[s.charAt(i)+1]++;

        for(int r = 0;r<R;++r)
            count[r+1] += count[r];

        for(int i = 0;i<N;++i)
            aux[count[s.charAt(i)]++] = i;

        StringBuilder sb = new StringBuilder();

        for(int i=0,elem = first;i<N;++i)
        {
            sb.append(s.charAt(aux[elem]));
            elem = aux[elem];
        }

        BinaryStdOut.write(sb.toString());

        BinaryStdOut.flush();
    }

    public static void main(String[] args) {
        if(args[0].compareTo("-")==0) transform();
        else if(args[0].compareTo("+")==0) inverseTransform();
        else throw new IllegalArgumentException();
    }
}
