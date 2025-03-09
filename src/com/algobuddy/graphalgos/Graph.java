package com.algobuddy.graphalgos;

import com.algobuddy.gui.Node;
import java.util.LinkedList;
import org.javatuples.Pair;

/**
 *
 * @author Nebir, Nazrul
 */
public class Graph {

    private final LinkedList<Node>[] adj;
    private final LinkedList<Pair<Node, Integer>>[] wAdj;

    Graph(int N) {
        adj = new LinkedList[N];
        wAdj = new LinkedList[N];
        for (int i = 0; i < N; i++) {
            adj[i] = new LinkedList<>();
            wAdj[i] = new LinkedList<>();
        }
    }

    void addEdge(Node u, Node v) {
        adj[u.getNodeNum()].add(v);
    }
    void addEdge(Node u, Node v, Integer w) {
        wAdj[u.getNodeNum()].add(Pair.with(v, w));
    }

    LinkedList<Node>[] getAdj() {
        return adj;
    }
    
    LinkedList<Pair<Node, Integer>>[] getWadj() {
        return wAdj;
    }
}
