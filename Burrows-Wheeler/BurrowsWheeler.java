/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class BurrowsWheeler {


    // apply Burrows-Wheeler transform,
    // reading from standard input and writing to standard output
    public static void transform() {
        int first = -1;
        String str = BinaryStdIn.readString();
        CircularSuffixArray cSuf = new CircularSuffixArray(str);
        for (int i = 0; i < cSuf.length(); i++) {
            if (cSuf.index((i)) == 0) {
                first = i;
            }
        }
        BinaryStdOut.write(first);
        for (int i = 0; i < cSuf.length(); i++) {
            BinaryStdOut.write(nthRotation(str, cSuf.index(i)));
        }
        BinaryStdOut.close();
    }

    private static char nthRotation(String str, int n) {
        if (n == 0)
            return str.charAt(str.length() - 1);
        else return str.charAt(n - 1);
    }


    // apply Burrows-Wheeler inverse transform,
    // reading from standard input and writing to standard output
    public static void inverseTransform() {
        int first = BinaryStdIn.readInt();
        String str = BinaryStdIn.readString();
        int len = str.length();
        char[] t = new char[len];
        for (int i = 0; i < len; i++) {
            t[i] = str.charAt(i);
        }
        int[] count = new int[257];
        for (int i = 0; i < len; i++) {
            count[t[i] + 1]++;
        }

        for (int r = 0; r < 256; r++) {
            count[r + 1] += count[r];
        }

        char[] aux = new char[len];
        int[] next = new int[len];
        for (int i = 0; i < len; i++) {
            aux[count[t[i]]] = t[i];
            next[count[t[i]]] = i;
            count[t[i]]++;
        }
        for (int i = 0; i < len; i++) {
            BinaryStdOut.write(aux[first]);
            first = next[first];
        }
        BinaryStdOut.close();


    }

    // if args[0] is "-", apply Burrows-Wheeler transform
    // if args[0] is "+", apply Burrows-Wheeler inverse transform
    public static void main(String[] args) {
        if (args[0].equals("-"))
            transform();
        else inverseTransform();
    }

}
