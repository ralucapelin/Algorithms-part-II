import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */
public class Outcast {
    private final WordNet wordnet;

    public Outcast(WordNet wordnet) {
        this.wordnet = wordnet;
    }      // constructor takes a WordNet object

    public String outcast(String[] nouns) {
        Integer[] dist = new Integer[nouns.length];
        int i = 0;
        int maxi = 0;
        String m = null;
        for (String noun :
                nouns) {
            dist[i] = 0;
            for (String noun2 :
                    nouns) {
                dist[i] += wordnet.distance(noun, noun2);
            }
            if (dist[i] > maxi) {
                maxi = dist[i];
                m = noun;
            }
            i++;
        }
        return m;

    }  // given an array of WordNet nouns, return an outcast

    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }  // see test client below
}
