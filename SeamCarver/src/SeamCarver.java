import edu.princeton.cs.algs4.Picture;

import java.awt.Color;
import java.util.Collections;

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
        return picture;
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
        // boundary entries have an energy function of 1000
        if(x == 0 || x == width - 1 || y == 0 || y == height - 1){
            return 1000;
        }
    }

    private double calculateXGradient(int x, int y){
        Color c1 = picture.get(x - 1, y);
        Color c2 = picture.get(x + 1, y);
        double redDiff = Math.pow(c1.getRed() - c2.getRed(), 2);
        double blueDiff = Math.pow(c1.getBlue() - c2.getBlue(), 2);
        double greenDiff = Math.pow(c1.getGreen() - c2.getGreen(), 2);
        return redDiff + blueDiff + greenDiff;
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
