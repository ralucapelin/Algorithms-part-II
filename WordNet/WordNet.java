/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class WordNet {
    private final HashMap<String, Set<Integer>> h1 = new HashMap<>();
    private final SAP sp;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null)
            throw new IllegalArgumentException("NU I VOIE");
        In in = new In(hypernyms);
        int c = 0;
        String[][] str = new String[83000][1000];
        while (in.hasNextLine()) {
            String s = in.readLine();
            str[c] = s.split(",");
            c++;
        }
        Digraph G = new Digraph(c);
        for (int i = 0; i < c; i++) {
            for (int j = 1; j < str[i].length; j++) {
                G.addEdge(Integer.parseInt(str[i][0]), Integer.parseInt(str[i][j]));
            }
        }

        if (!isDAG(G))
            throw new IllegalArgumentException("NU I VOIE");
        sp = new SAP(G);
        In scanner = new In(synsets);
        while (scanner.hasNextLine()) {
            String line = scanner.readLine();
            String[] str1 = line.split(",");
            String[] arr = str1[1].split(" ");
            for (int i = 0; i < arr.length; i++) {

                Set<Integer> val = h1.get(arr[i]);
                if (val == null) {
                    val = new HashSet<>();
                    val.add(Integer.parseInt(str1[0]));
                    h1.put(arr[i], val);
                }
                else {
                    val.add(Integer.parseInt(str1[0]));
                }
            }
        }
        scanner.close();


    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return h1.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null)
            throw new IllegalArgumentException("NU I VOIE");
        Set<Integer> val = h1.get(word);
        if (val == null)
            return false;
        return true;
    }

    private boolean isDAG(Digraph G) {
        DirectedCycle dc = new DirectedCycle(G);

        if (dc.hasCycle())
            return false;
        return true;
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (nounB == null || nounA == null || !isNoun(nounA) || !isNoun(nounB))
            throw new IllegalArgumentException("NU I VOIE");
        return sp.length(h1.get(nounA), h1.get(nounB));
    }

    private String getKey(HashMap<String, Set<Integer>> map, int value) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Set<Integer>> entry : map.entrySet()) {
            for (int i : entry.getValue()) {
                if (i == value) {
                    String str = entry.getKey();
                    sb.append(str);
                    sb.append(" ");
                }
            }


        }
        String s = sb.toString();
        if (s.length() >= 1)
            s = s.substring(0, s.length() - 1);
        return s;

    }


    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (nounA == null || nounB == null || !isNoun(nounA) || !isNoun(nounB))
            throw new IllegalArgumentException("NU I VOIE");
        return getKey(h1, sp.ancestor(h1.get(nounA), h1.get(nounB)));
    }


    // do unit testing of this class
    public static void main(String[] args) {
        //test

    }
}
