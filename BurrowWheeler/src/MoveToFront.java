import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class MoveToFront {

    public static void encode(){
        int R = 256;
        char alpha[] = new char[R];

        for(char i = 0;i<256;++i)
            alpha[i] = i;

        while(!BinaryStdIn.isEmpty()){
            char c = BinaryStdIn.readChar();
            for(int i = 0;i<alpha.length;++i)
                if(c==alpha[i]){
                BinaryStdOut.write((char)i);
                for(int j = i-1;j>=0;j--){
                    alpha[j+1] = alpha[j];
                }
                    alpha[0] = c;
            }
        }
        BinaryStdOut.flush();
    }

    public static void decode()
    {

        int R = 256;
        char alpha[] = new char[R];
        for(char i = 0;i<R;++i)
            alpha[i] = i;

        while (!BinaryStdIn.isEmpty())
        {
            char c = BinaryStdIn.readChar();
            int index = (int)c;
            BinaryStdOut.write(alpha[index]);

            char tmp = alpha[0];
            for(int i = 0;i < index;i++)
            {
                char t = alpha[i+1];
                alpha[i+1] = tmp;
                tmp = t;
            }
            alpha[0] = tmp;
        }
        BinaryStdOut.flush();
    }


    public static void main(String[] args) {
        if(args[0].compareTo("-")==0) encode();
        else if(args[0].compareTo("+")==0)decode();
        else throw new IllegalArgumentException();
    }
}
