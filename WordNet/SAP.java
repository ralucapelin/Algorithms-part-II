import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */
public class SAP {
    private final Digraph G;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if (G == null)
            throw new IllegalArgumentException("NU");
        this.G = new Digraph(G);

    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        if (v < 0 || w < 0 || v >= G.V() || w >= G.V())
            throw new IllegalArgumentException("NU");

        BreadthFirstDirectedPaths bfs1 = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths bfs2 = new BreadthFirstDirectedPaths(G, w);
        int mini = 99999;
        int ok = 1;
        for (int i = 0; i < G.V(); i++) {
            if (bfs1.hasPathTo(i) && bfs2.hasPathTo(i)) {
                if (bfs1.distTo(i) + bfs2.distTo(i) < mini) {
                    mini = bfs1.distTo(i) + bfs2.distTo(i);
                    ok = 0;
                }

            }
        }
        if (ok == 1)
            return -1;
        return mini;

    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        if (v < 0 || w < 0 || v >= G.V() || w >= G.V())
            throw new IllegalArgumentException("NU");

        BreadthFirstDirectedPaths bfs1 = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths bfs2 = new BreadthFirstDirectedPaths(G, w);
        int mini = 99999;
        int anc = -1;
        for (int i = 0; i < G.V(); i++) {
            if (bfs1.hasPathTo(i) && bfs2.hasPathTo(i)) {
                if (bfs1.distTo(i) + bfs2.distTo(i) < mini) {
                    mini = bfs1.distTo(i) + bfs2.distTo(i);
                    anc = i;
                }

            }
        }
        return anc;
    }

    private int size(Iterable<Integer> v) {
        int count = 0;
        Iterator<Integer> iterator = v.iterator();
        while (iterator.hasNext()) {
            iterator.next();
            count++;
        }
        return count;
    }

   /* private boolean isNull(Iterable<Integer> v) {
        for (Integer i : v) {
            if (i.equals(null))
                return true;
        }
        return false;
    }

    */

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if (w == null || v == null)
            throw new IllegalArgumentException("NU");
        /*for (Integer i : v)
            if (i == null)
                throw new IllegalArgumentException("NU");
        for (Integer i : w)
            if (i == null)
                throw new IllegalArgumentException("NU");*/
        // if (isNull(v) || isNull(w))
        // throw new IllegalArgumentException("NU");
        if (size(v) == 0 || size(w) == 0)
            return -1;
        BreadthFirstDirectedPaths bfs1 = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths bfs2 = new BreadthFirstDirectedPaths(G, w);
        int mini = 999999;
        for (int i = 0; i < G.V(); i++) {
            if (bfs1.hasPathTo(i) && bfs2.hasPathTo(i)) {
                if (bfs1.distTo(i) + bfs2.distTo(i) < mini)
                    mini = bfs1.distTo(i) + bfs2.distTo(i);
            }
        }
        return mini;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if (w == null || v == null)
            throw new IllegalArgumentException("NU");
        /*for (Integer i : v)
            if (i == null)
                throw new IllegalArgumentException("NU");
        for (Integer i : w)
            if (i == null)
                throw new IllegalArgumentException("NU");*/
        // if (isNull(v) || isNull(w))
        //   throw new IllegalArgumentException("NU");
        if (size(v) == 0 || size(w) == 0)
            return -1;
        BreadthFirstDirectedPaths bfs1 = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths bfs2 = new BreadthFirstDirectedPaths(G, w);
        int mini = 999999;
        int anc = 0;
        for (int i = 0; i < G.V(); i++) {
            if (bfs1.hasPathTo(i) && bfs2.hasPathTo(i)) {
                if (bfs1.distTo(i) + bfs2.distTo(i) < mini) {
                    mini = bfs1.distTo(i) + bfs2.distTo(i);
                    anc = i;
                }

            }
        }
        return anc;
    }

    // do unit testing of this class
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            StdOut.printf("merge\n");
            int v = StdIn.readInt();
            int w = StdIn.readInt();

            int length = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}
