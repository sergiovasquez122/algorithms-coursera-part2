import edu.princeton.cs.algs4.Picture;

import java.awt.Color;

public class SeamCarver {
    private Picture picture;
    private int width;
    private int height;

    public SeamCarver(Picture picture){
        this.picture = new Picture(picture);
        this.height = picture.height();
        this.width = picture.width();
    }

    public Picture picture(){
        return new Picture(this.picture);
    }

    public int width(){
        return width;
    }

    public int height(){
        return height;
    }

    public double energy(int x, int y){
        if(x < 0 || x >= width() || y < 0 || y >= height()){
            throw new IllegalArgumentException();
        }
        return 0;
    }

    public int[] findHorizontalSeam(){
        return null;
    }

    public int[] findVerticalSeam(){
        return null;
    }

    public void removeHorizontalSeam(int[] seam){
        if(seam == null){
            throw new IllegalArgumentException();
        }
    }

    public void removeVerticalSeam(int[] seam){
        if(seam == null){
            throw new IllegalArgumentException();
        }
    }

    public static void main(String[] args) {
    }
}
