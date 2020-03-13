import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.Stack;
import java.awt.Color;

public class SeamCarver {
    private Picture picture;


    public SeamCarver(Picture picture){
        this.picture = new Picture(picture);

    }

    public Picture picture(){
       return new Picture(picture);
    }

    public int width(){
        return picture.width();
    }

    public int height(){
        return picture.height();
    }

    public double energy(int x,int y){
        if(!valid(x,y)) throw new IllegalArgumentException();
        if(borderImage(x,y)) return 1000;
        double x_grad_squared = x_gradient(x,y);
        double y_grad_square = y_gradient(x,y);
        return Math.sqrt(x_grad_squared+y_grad_square);
    }

    private double x_gradient(int x,int y){
        Color x_minus_1 = picture.get(x-1,y);
        Color x_plus_1 = picture.get(x+1,y);
        int red = x_plus_1.getRed()-x_minus_1.getRed();
        int green = x_plus_1.getGreen()-x_minus_1.getGreen();
        int blue = x_plus_1.getBlue()-x_minus_1.getBlue();
        return red*red+green*green+blue*blue;
    }

    private double y_gradient(int x,int y){
        Color y_minus_1 = picture.get(x,y-1);
        Color y_plus_1 = picture.get(x,y+1);
        int red = y_plus_1.getRed()-y_minus_1.getRed();
        int green = y_plus_1.getGreen()-y_minus_1.getGreen();
        int blue = y_plus_1.getBlue()-y_minus_1.getBlue();
        return red*red+green*green+blue*blue;
    }

    private boolean valid(int x,int y){
        return (x>=0 && x<width()) && (y>=0 && y<height());
    }

    private boolean borderImage(int x,int y){
        return (x-1<0 || x+1>=width()) || (y-1<0 || y+1>=height());
    }

    public int[] findHorizontalSeam()
    {
        transpose();
        int ret[] = findVerticalSeam();
        transpose();
        return ret;
    }

    public int[] findVerticalSeam()
    {
        double energies[][] = new double[width()][height()];
            for(int y = 0;y<height();++y){
                for(int x = 0;x<width();++x){
                energies[x][y] = energy(x,y);
            }
        }

        double disto[][] = new double[width()][height()];
        for(int y = 1;y<height();++y){
            for(int x = 0;x<width();++x){
                if(x==0) disto[x][y] = energies[x][y] + Double.min(disto[x][y-1],disto[x+1][y-1]);
                else if (x==width()-1) disto[x][y] = energies[x][y] + Double.min(disto[x][y-1],disto[x-1][y-1]);
                else disto[x][y] = energies[x][y] + Double.min(disto[x-1][y-1],Double.min(disto[x][y-1],disto[x+1][y-1]));
            }
        }



        /*
        for(int y = 0;y<height();++y){
            for(int x = 0;x<width();++x)
            {
                StdOut.print( Math.round(disto[x][y]) + " ");
            }
            StdOut.println();
        }
        */
        int pointer = findsmallestindex(disto);

        Stack<Integer> seam = new Stack<>();
        int height = height()-1;
        int positions[] = {-1,0,1};
        while(height!=0){
            seam.push(pointer);
            for(int pos : positions){
                int x_pos = pos + pointer;
                if (valid(x_pos,height-1)&&Double.compare(disto[pointer][height],energies[pointer][height]+disto[x_pos][height-1])==0){
                    pointer = x_pos;
                    break;
                }
            }
            height--;
        }

        seam.push(pointer);

        int seamToArray[] = new int[seam.size()];
        for(int i = 0;i<seamToArray.length;++i)seamToArray[i] = seam.pop();

        return seamToArray;
    }


    private int findsmallestindex(double arr[][]){
        int smallest = 0;
        for(int i = 0;i<arr.length;++i)
            if(arr[smallest][height()-1]>arr[i][height()-1]) smallest = i;
        return smallest;
    }

    private void transpose(){
        Picture pic = new Picture(height(),width());
            for(int y = 0;y<height();++y){
                for(int x = 0;x<width();++x){
                pic.set(y,x,picture.get(x,y));
            }
        }
        picture = pic;
    }

    public void removeHorizontalSeam(int[] seam)
    {
        transpose();
        removeVerticalSeam(seam);
        transpose();
    }

    public void removeVerticalSeam(int[] seam)
    {
        if(seam==null) throw new IllegalArgumentException();
        if(height()<=1) throw new IllegalArgumentException();
        if(seam.length!=height()) throw new IllegalArgumentException();
        //still have to implement test to determine if valid seam
        Picture pic = new Picture(width()-1,height());
        for(int i = 0;i<height();++i){
            int write_index = 0;
            for(int j = 0;j<width();++j){
                if(j==seam[i]) continue;
                pic.set(write_index++,i,picture.get(j,i));
            }
        }
        picture = pic;
    }

    public static void main(String[] args) {
        SeamCarver carver = new SeamCarver(new Picture("logo.png"));
        carver.findVerticalSeam();
    }
}
