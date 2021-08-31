/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Picture;

import java.util.Arrays;

public class SeamCarver {
    private Picture picture;
    private double[][] energy;
    private int[][] colors;
    private int width;
    private int height;
    private int len;
    private int len1;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if (picture == null)
            throw new IllegalArgumentException("NU");
        this.picture = picture;
        this.width = picture.width();
        this.height = picture.height();
        energy = new double[height][width];
        len = height;
        len1 = width;
        colors = new int[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                colors[i][j] = picture.getRGB(j, i);

            }
        }
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {

                energy[i][j] = energy(j, i);
            }
        }

    }

    // current picture
    public Picture picture() {
        Picture pic = new Picture(width, height);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                pic.setRGB(j, i, colors[i][j]);
            }
        }
        return pic;
    }

    // width of current picture
    public int width() {
        return width;
    }

    // height of current picture
    public int height() {
        return height;
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        if (x < 0 || y < 0 || x > width - 1 || y > height - 1)
            throw new IllegalArgumentException(x + " " + y);
        if (x == 0 || y == 0 || x == width - 1 || y == height - 1)
            return 1000;
        int color1 = colors[y][x + 1];
        int blue1 = color1 & 0xff;
        int green1 = (color1 & 0xff00) >> 8;
        int red1 = (color1 & 0xff0000) >> 16;
        int color2 = colors[y][x - 1];
        int blue2 = color2 & 0xff;
        int green2 = (color2 & 0xff00) >> 8;
        int red2 = (color2 & 0xff0000) >> 16;
        double deltaX = (red1 - red2) * (red1 - red2) + (green1 - green2) * (green1 - green2)
                + (blue1 - blue2) * (blue1 - blue2);

        int color3 = colors[y + 1][x];
        int blue3 = color3 & 0xff;
        int green3 = (color3 & 0xff00) >> 8;
        int red3 = (color3 & 0xff0000) >> 16;
        int color4 = colors[y - 1][x];
        int blue4 = color4 & 0xff;
        int green4 = (color4 & 0xff00) >> 8;
        int red4 = (color4 & 0xff0000) >> 16;
        double deltaY = (red3 - red4) * (red3 - red4) + (green3 - green4) * (green3 - green4)
                + (blue3 - blue4) * (blue3 - blue4);
        return Math.sqrt(deltaX + deltaY);

    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        double[][] energy1 = new double[width][height];
        energy1 = transposeMatrix(energy);
        energy = new double[width][height];
        energy = energy1;
        int aux = len;
        len = len1;
        len1 = aux;
        int[] rez = findVerticalSeam();
        energy = new double[height][width];
        energy = transposeMatrix(energy1);
        int aux1 = len;
        len = len1;
        len1 = aux1;
        return rez;
    }

    private double[][] transposeMatrix(double[][] m) {
        double[][] temp = new double[m[0].length][m.length];
        for (int i = 0; i < m.length; i++)
            for (int j = 0; j < m[0].length; j++)
                temp[j][i] = m[i][j];
        return temp;
    }

    private int[][] transposeMatrixInt(int[][] m) {
        int[][] temp = new int[m[0].length][m.length];
        for (int i = 0; i < m.length; i++)
            for (int j = 0; j < m[0].length; j++)
                temp[j][i] = m[i][j];
        return temp;
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        double[][] A = Arrays.stream(energy).map(double[]::clone).toArray(double[][]::new);
        int n = len;
        //StdOut.println(n);

        int m = len1;
        //StdOut.println(m);


        for (int R = n - 2; R >= 0; --R) {
            for (int C = 0; C < m; ++C) {

                // best = min(A[R+1][C-1], A[R+1][C], A[R+1][C+1])
                double best = A[R + 1][C];
                if (C > 0)
                    best = Math.min(best, A[R + 1][C - 1]);
                if (C + 1 < m)
                    best = Math.min(best, A[R + 1][C + 1]);
                A[R][C] = A[R][C] + best;

            }


        }
       /* StdOut.println();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                StdOut.print(A[i][j] + " ");
            }
            StdOut.println();
        }*/

        int[] rez = new int[n];
        double mini = Double.POSITIVE_INFINITY;
        int index = 0;
        for (int C = 0; C < m; C++) {
            //StdOut.println(A[4][C]);
            if (A[0][C] < mini) {
                mini = A[0][C];
                index = C;
                //
            }
        }
        // StdOut.println(index);
        rez[0] = index;
        if (n > 1)
            rez[1] = index;

        int C = index;
        for (int R = 0; R < n - 1; R++) {


            // best = min(A[R+1][C-1], A[R+1][C], A[R+1][C+1])
            double best = A[R + 1][C];

            if (C > 0)
                best = Math.min(best, A[R + 1][C - 1]);
            if (C + 1 < m)
                best = Math.min(best, A[R + 1][C + 1]);
            if (best == A[R + 1][C])
                rez[R + 1] = C;
            if (C + 1 < m)
                if (best == A[R + 1][C + 1]) {
                    rez[R + 1] = C + 1;
                    C++;
                }
            if (C > 0)
                if (best == A[R + 1][C - 1]) {
                    rez[R + 1] = C - 1;
                    C--;
                }
            //StdOut.println(best);
        }
        if (n > 1)
            rez[n - 1] = rez[n - 2];
        return rez;
    }

    private boolean isSeam(int[] seam, int n) {
        for (int i = 0; i < seam.length; i++) {
            if (seam[i] < 0 || seam[i] > n - 1)
                return false;
            if (i != seam.length - 1)
                if (Math.abs(seam[i] - seam[i + 1]) > 1)
                    return false;
        }
        return true;
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        if (height <= 1)
            throw new IllegalArgumentException("NU");
        if (seam == null || !isSeam(seam, height))
            throw new IllegalArgumentException("NU");
        if (seam.length != width)
            throw new IllegalArgumentException("NU");
        height--;
        len--;
        int[][] colors1 = new int[width][height];
        colors1 = transposeMatrixInt(colors);
        colors = new int[width][height];
        colors = colors1;
        //StdOut.println(colors[0].length);
        for (int i = 0; i < seam.length; i++) {
            for (int j = seam[i]; j < height; j++) {
                colors[i][j] = colors[i][j + 1];
            }

        }
        colors = new int[height][width];
        colors = transposeMatrixInt(colors1);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {

                energy[i][j] = energy(j, i);
            }
        }
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        if (width <= 1)
            throw new IllegalArgumentException("NU");
        if (seam == null || !isSeam(seam, width))
            throw new IllegalArgumentException("NU");
        if (seam.length != height)
            throw new IllegalArgumentException("nu");
        width--;
        len1--;
        for (int i = 0; i < seam.length; i++) {
            for (int j = seam[i]; j < width; j++) {
                colors[i][j] = colors[i][j + 1];
            }

        }

        for (int i = 0; i < height; i++) {

            for (int j = 0; j < width; j++) {

                energy[i][j] = energy(j, i);
            }
        }


    }

    //  unit testing (optional)
    public static void main(String[] args) {

    }

}
