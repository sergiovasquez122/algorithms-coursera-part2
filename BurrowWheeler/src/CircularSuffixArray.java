import java.util.Arrays;
import java.util.Comparator;

public class CircularSuffixArray {
    private String s;
    private Integer sortedSuffixes[];
    private static int R = 256;

    public CircularSuffixArray(String s)
    {
        if(s==null) throw new IllegalArgumentException();
        this.s = s;
        sortedSuffixes = new Integer[s.length()];
        for(int i = 0;i<length();++i)
            sortedSuffixes[i] = i;

        Arrays.sort(sortedSuffixes,new theSort());
    }

    public int length(){
        return s.length();
    }

    public int index(int i){
        if(i<0 ||i>=length()) throw new IllegalArgumentException();
        return  sortedSuffixes[i];
    }

    private class theSort implements Comparator<Integer> {
        public int compare(Integer ith, Integer jth) {
            for(int i = 0;i<length();++i) {
                if (s.charAt((ith + i) % length()) < s.charAt((jth + i) % length()))
                    return -1;
                else if(s.charAt((ith+i)%length())> s.charAt((jth+i)%length()))
                    return 1;
            }
            return 0;
        }
    }

    public static void main(String[] args) {
    }

}
