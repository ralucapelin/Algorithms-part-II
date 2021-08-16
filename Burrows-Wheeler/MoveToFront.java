/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;


public class MoveToFront {
    private static int getIndex(char[] seq, char a) {
        int i = 0;
        while (seq[i] != a) {
            i++;
        }
        return i;
    }

    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {
        char[] seq = new char[256];

        for (int j = 0; j < 256; j++) {
            seq[j] = (char) j;
        }


        while (!BinaryStdIn.isEmpty()) {
            char c = BinaryStdIn.readChar();
            int idx = getIndex(seq, c);
            BinaryStdOut.write((char) idx);
            //StdOut.println(idx);
            for (int i = idx; i > 0; i--)
                seq[i] = seq[i - 1];
            seq[0] = c;
        }
        BinaryStdOut.close();
    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {
        char[] seq = new char[256];

        for (int j = 0; j < 256; j++) {
            seq[j] = (char) j;
        }


        while (!BinaryStdIn.isEmpty()) {
            char c = BinaryStdIn.readChar();
            char s = seq[(int) c];
            BinaryStdOut.write(s);
            for (int i = (int) c; i > 0; i--)
                seq[i] = seq[i - 1];
            seq[0] = s;
        }
        BinaryStdOut.close();
    }

    // if args[0] is "-", apply move-to-front encoding
    // if args[0] is "+", apply move-to-front decoding
    public static void main(String[] args) {
        if (args[0].equals("-"))
            encode();
        else decode();
    }

}
