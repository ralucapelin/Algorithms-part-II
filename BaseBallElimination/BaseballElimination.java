/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;

public class BaseballElimination {
    private final int noTeams;
    private final ArrayList<String> Teams = new ArrayList<String>();
    private final int[] w = new int[100];
    private final int[] l = new int[100];
    private final int[] r = new int[100];
    private final int[][] g = new int[100][100];
    private final int[] trivEl = new int[100];
    private final int[] isEl = new int[100];


    private int trivialElim(int x) {

        for (int j = 0; j < noTeams; j++) {
            if (w[x] + r[x] < w[j]) {
                return 1;
            }
        }
        return 0;
    }


    private int combination(int n) {
        int com = n * (n - 1) / 2;
        return com;
    }

    private FlowNetwork creatFlow(int x) {
        int noVertex = noTeams - 1 + combination(noTeams - 1) + 2;
        FlowNetwork fn = new FlowNetwork(noVertex);
        int k = noTeams;

        for (int i = 0; i < noTeams; i++) {
            for (int j = i + 1; j < noTeams; j++) {
                if (i != j && i != x && j != x) {

                    FlowEdge e = new FlowEdge(x, k, g[i][j]);
                    fn.addEdge(e);
                    e = new FlowEdge(k, j, Double.POSITIVE_INFINITY);
                    fn.addEdge(e);
                    e = new FlowEdge(k, i, Double.POSITIVE_INFINITY);
                    fn.addEdge(e);
                    k++;
                }
            }
        }

        for (int i = 0; i < noTeams; i++) {
            if (i != x) {

                FlowEdge e = new FlowEdge(i, noVertex - 1, w[x] + r[x] - w[i]);

                fn.addEdge(e);
            }
        }

        return fn;

    }

    public BaseballElimination(String filename) {
        In in = new In(filename);
        noTeams = in.readInt();
        for (int i = 0; i < noTeams; i++) {
            Teams.add(in.readString());
            w[i] = in.readInt();
            l[i] = in.readInt();
            r[i] = in.readInt();
            for (int j = 0; j < noTeams; j++) {
                g[i][j] = in.readInt();
            }
        }
        for (int i = 0; i < noTeams; i++) {
            trivEl[i] = trivialElim(i);

        }

        for (int i = 0; i < noTeams; i++) {
            if (trivEl[i] == 0) {
                FlowNetwork fn = creatFlow(i);
                //StdOut.println("i " + i);
                int noVertex = noTeams - 1 + combination(noTeams - 1) + 2;
                FordFulkerson ford = new FordFulkerson(fn, i, noVertex - 1);
                for (int j = 0; j < noTeams; j++) {
                    //StdOut.println("inceput for cu j " + j);
                    if (j != i) {
                        if (ford.inCut(j)) {
                            isEl[i] = 1;
                            // StdOut.println("OK");
                        }

                    }

                }

            }

        }

    }                   // create a baseball division from given filename in format specified below

    public int numberOfTeams() {
        return noTeams;
    }                      // number of teams

    public Iterable<String> teams() {
        return Teams;
    }                        // all teams

    public int wins(String team) {
        if (!Teams.contains(team))
            throw new IllegalArgumentException("NU E BINE");
        return w[Teams.indexOf(team)];
    }                     // number of wins for given team

    public int losses(String team) {
        if (!Teams.contains(team))
            throw new IllegalArgumentException("NU E BINE");
        return l[Teams.indexOf(team)];
    }                // number of losses for given team

    public int remaining(String team) {
        if (!Teams.contains(team))
            throw new IllegalArgumentException("NU E BINE");
        return r[Teams.indexOf(team)];
    }           // number of remaining games for given team

    public int against(String team1, String team2) {
        if (!Teams.contains(team1) || !Teams.contains(team2))
            throw new IllegalArgumentException("NU E BINE");
        return g[Teams.indexOf(team1)][Teams.indexOf(team2)];
    }  // number of remaining games between team1 and team2

    public boolean isEliminated(String team) {
        if (!Teams.contains(team))
            throw new IllegalArgumentException("NU E BINE");
        if (trivEl[Teams.indexOf(team)] == 1)
            return true;
        if (isEl[Teams.indexOf(team)] == 1)
            return true;
        return false;
    }       // is given team eliminated?

    public Iterable<String> certificateOfElimination(String team) {
        if (!isEliminated(team))
            return null;
        if (!Teams.contains(team))
            throw new IllegalArgumentException("NU E BINE");
        ArrayList<String> R = new ArrayList<String>();
        for (int i = 0; i < noTeams; i++) {
            if (i != Teams.indexOf(team) && trivEl[i] != 1) {
                R.add(Teams.get(i));
            }
        }
        return R;
    }// subset R of teams that eliminates given team; null if not eliminated

    public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination(args[0]);
        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team)) {
                    StdOut.print(t + " ");
                }
                StdOut.println("}");
            }
            else {
                StdOut.println(team + " is not eliminated");
            }
        }
    }
}
