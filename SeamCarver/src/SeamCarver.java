import edu.princeton.cs.algs4.Picture;

import java.awt.Color;
import java.util.Collections;

public class SeamCarver {
    private Picture picture;
    private int width;
    private int height;

    /**
     * Create a seam carver object based on the given picture
     * @param picture
     */
    public SeamCarver(Picture picture){
        this.picture = new Picture(picture);
        this.height = picture.height();
        this.width = picture.width();
    }

    /**
     * @return the current
     */
    public Picture picture(){
        return picture;
    }

    /**
     * @return width of current picture
     */
    public int width(){
        return width;
    }

    /**
     * @return height of current picture
     */
    public int height(){
        return height;
    }

    /**
     * @return energy of pixel at column x and row y
     */
    public double energy(int x, int y){
        if(x < 0 || x >= width() || y < 0 || y >= height()){
            throw new IllegalArgumentException();
        }
        // boundary entries have an energy function of 1000
        if(x == 0 || x == width - 1 || y == 0 || y == height - 1){
            return 1000;
        }
        double xGradient = calculateXGradient(x, y);
        double yGradient = calculateYGradient(x, y);
        return Math.sqrt(xGradient + yGradient);
    }

    /**
     * @return gradient of the pixel (x,y) in the column direction
     */
    private double calculateXGradient(int x, int y){
        Color c1 = picture.get(x - 1, y);
        Color c2 = picture.get(x + 1, y);
        double redDiff = Math.pow(c1.getRed() - c2.getRed(), 2);
        double blueDiff = Math.pow(c1.getBlue() - c2.getBlue(), 2);
        double greenDiff = Math.pow(c1.getGreen() - c2.getGreen(), 2);
        return redDiff + blueDiff + greenDiff;
    }

    /**
     * @return gradient of the pixel (x, y) in the row direction
     */
    private double calculateYGradient(int x, int y){
        Color c1 = picture.get(x, y - 1);
        Color c2 = picture.get(x, y + 1);
        double redDiff = Math.pow(c1.getRed() - c2.getRed(), 2);
        double blueDiff = Math.pow(c1.getBlue() - c2.getBlue(), 2);
        double greenDiff = Math.pow(c1.getGreen() - c2.getGreen(), 2);
        return redDiff + blueDiff + greenDiff;
    }

    /**
     * @return sequence of indices for horizontal seam
     */
    public int[] findHorizontalSeam(){
        return null;
    }

    /**
     * @return sequence of indices for vertical seam
     */
    public int[] findVerticalSeam(){
        double energy[][] = new double[height][width];
        for(int i = 0;i < height;++i){
            for(int j = 0;j < width;++j){
                if(i == 0 || j == 0 || i == height - 1 || j == width - 1){
                    energy[i][j] = 1000;
                } else {
                    energy[i][j] = energy(j, i) + Math.min(energy[i - 1][j], Math.min(energy[i- 1][j - 1], energy[i-1][j+1]));
                }
            }
        }
        int[] seam = new int[height];
        int min_idx = 0;
        double min_value = Double.POSITIVE_INFINITY;
        for(int i = 0;i < width;++i){
            if(energy[height - 2][i] < min_value){
                min_idx = i;
                min_value = energy[height - 2][i];
            }
        }
        seam[height - 2] = min_idx;
        seam[height - 1] = min_idx;
        int back_tracking = height - 2;
        while(back_tracking > 0){
            int prev_idx = seam[back_tracking];
            int final_choice = prev_idx;
            if(prev_idx - 1 > 0 && energy[back_tracking][prev_idx - 1] < energy[back_tracking][prev_idx]){
                final_choice = prev_idx-1;
            } if(prev_idx + 1 < width && energy[back_tracking][prev_idx + 1] < energy[back_tracking][prev_idx]){
                final_choice = prev_idx + 1;
            }
            seam[--back_tracking] = final_choice;
        }
        return seam;
    }


    /**
     * @param seam the horizontal seam to be removed from the image
     */
    public void removeHorizontalSeam(int[] seam){
    }

    /**
     * @param seam the vertical seam to be removed from the image
     */
    public void removeVerticalSeam(int[] seam){
        if(seam == null || seam.length != height || height <= 1) {
            throw new IllegalArgumentException();
        }
        for(int i = 0;i < seam.length;i++){
            if(seam[i] < 0 || seam[i] > width){
                throw new IllegalArgumentException();
            }
        }
        for(int i = 1;i < seam.length;i++){
            if(Math.abs(seam[i] - seam[i - 1]) > 1){
                throw new IllegalArgumentException();
            }
        }
        Picture p = new Picture(width - 1, height);
        for(int i = 0;i < height;++i){
            int write_idx = 0;
            for(int j = 0;j < width;++j){
                if(seam[i] != j){
                    p.set(write_idx++,i, picture.get(j, i));
                }
            }
        }
        picture = p;
    }

    public static void main(String[] args) {
    }
}
