/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;

public class CircularSuffixArray {
    private int len;
    private ArrayList<Integer> idx = new ArrayList<Integer>();


    // circular suffix array of s
    public CircularSuffixArray(String s) {
        if (s == null)
            throw new IllegalArgumentException();

        len = s.length();

        for (int i = 0; i < len; i++) {
            idx.add(i);
        }
        ArrayList<String> suf = new ArrayList<String>();
        suf.add(s);
        int n = len;
        String str = s;

        while (n > 1) {
            StringBuilder sb = new StringBuilder();
            char last = str.charAt(0);
            for (int i = 1; i < len; i++)
                sb.append(str.charAt(i));
            sb.append(last);

            str = sb.toString();

            suf.add(str);
            n--;
        }


        QuickSort qs = new QuickSort();
        qs.sort(suf, 0, len - 1, idx);


    }

    // length of s
    public int length() {
        return len;
    }

    // returns index of ith sorted suffix
    public int index(int i) {
        if (i < 0 || i > this.length()) {
            throw new IllegalArgumentException();
        }
        return idx.get(i);
    }

    // unit testing (required)
    public static void main(String[] args) {
        CircularSuffixArray circ = new CircularSuffixArray(args[0]);
        StdOut.println(circ.length());
    }

}
