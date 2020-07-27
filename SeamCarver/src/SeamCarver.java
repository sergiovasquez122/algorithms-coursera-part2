import edu.princeton.cs.algs4.Picture;
import java.awt.Color;

public class SeamCarver {
    private Picture picture;
    private int width;
    private int height;

    /**
     * Create a seam carver object based on the given picture
     * @param picture
     */
    public SeamCarver(Picture picture){
        if(picture == null){
            throw new IllegalArgumentException();
        }
        this.picture = new Picture(picture);
        this.height = picture.height();
        this.width = picture.width();
    }

    /**
     * @return the current
     */
    public Picture picture(){
        return new Picture(picture);
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
            transpose();
            int ret[] = findVerticalSeam();
            transpose();
            return ret;
    }

    /**
     * @return sequence of indices for vertical seam
     */
    public int[] findVerticalSeam(){
        double energy[][] = new double[height][width];
        if(width == 1){
            return new int[height];
        }
        for(int i = 0;i < width;++i){
            energy[0][i] = 1000;
        }
        for(int i = 1;i < height;++i){
            for(int j = 0;j < width;++j){
                if(j == 0){
                    energy[i][j] = energy(j, i) + Math.min(energy[i - 1][j], energy[i - 1][j + 1]);
                } else if(j == width - 1){
                    energy[i][j] = energy(j, i) + Math.min(energy[i - 1][j], energy[i - 1][j - 1]);
                } else {
                    energy[i][j] = energy(j, i) + Math.min(energy[i - 1][j], Math.min(energy[i - 1][j - 1], energy[i - 1][j + 1]));
                }
            }
        }
        int[] seam = new int[height];
        int min_idx = 0;
        double min_value = Double.POSITIVE_INFINITY;
        for(int i = 0;i < width;++i){
            if(energy[height - 1][i] < min_value){
                min_idx = i;
                min_value = energy[height - 1][i];
            }
        }
        seam[height - 1] = min_idx;
        int back_tracking = height - 1;
        while(back_tracking > 0){
            int prev_idx = seam[back_tracking];
            int final_choice = prev_idx;
            if(prev_idx - 1 >= 0 && energy[back_tracking - 1][prev_idx - 1] < energy[back_tracking - 1][final_choice]){
                final_choice = prev_idx-1;
            } if(prev_idx + 1 < width && energy[back_tracking - 1][prev_idx + 1] < energy[back_tracking - 1][final_choice]){
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
        transpose();
        removeVerticalSeam(seam);
        transpose();
    }

    /**
     * @param seam the vertical seam to be removed from the image
     */
    public void removeVerticalSeam(int[] seam)
    {
        if(seam==null) throw new IllegalArgumentException();
        if(height()<=1) throw new IllegalArgumentException();
        if(seam.length != height) throw new IllegalArgumentException();
        for(int i = 0;i < seam.length;++i){
            if(seam[i] < 0 || seam[i] >= width - 1){
                throw new IllegalArgumentException();
            }
        }
        for(int i = 1;i < seam.length;++i){
            if(Math.abs(seam[i] - seam[i - 1]) > 1){
                throw new IllegalArgumentException();
            }
        }
        Picture pic = new Picture(width-1,height);
        for(int i = 0;i<height;++i){
            int write_index = 0;
            for(int j = 0;j<width;++j){
                if(j==seam[i]) continue;
                pic.set(write_index++,i,picture.get(j,i));
            }
        }
        picture = pic;
        width = pic.width();
        height = pic.height();
    }

    private void transpose(){
        Picture pic = new Picture(height(),width());
        for(int y = 0;y<height();++y){
            for(int x = 0;x<width();++x){
                pic.set(y,x,picture.get(x,y));
            }
        }
        picture = pic;
        this.width = pic.width();
        this.height = pic.height();
    }


    public static void main(String[] args) {
    }
}
