package maze;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 *
 * @author davide
 */
public class AdiacencyMatrix {

    public boolean[][] edge;

    public AdiacencyMatrix(int n) {
        edge = new boolean[n][n];
    }

    public void addEdge(int v, int w) {
        edge[v][w] = true;
        edge[w][v] = true;
    }

    public boolean hasEdge(int v, int w) {
        return edge[v][w] && edge[w][v];
    }

    public void removeEdge(int s, int d) {
        edge[s][d] = false;
        edge[d][s] = false;
    }

    public List<Integer> adiacentVertices(int v) {
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < edge.length; i++) {
            if (hasEdge(v,i)) {
                list.add(i);
            }
        }
        return list;
    }

    public int[] BFSspanningTree(int v) {
        Queue<Integer> q = new LinkedList();
        int[] p = new int[edge.length];
        for (int i = 0; i < p.length; i++) {
            p[i] = -1;
        }
        p[v] = v;//alla posizione v metti il vertice di partenza
        q.add(v);
        while (!q.isEmpty()) {
            Integer r = q.remove();
            for (Integer w : adiacentVertices(r)) {
                if (p[w] < 0) {
                    p[w] = r;
                    q.add(w);
                }
           }
        }
        return p;
    }
}
