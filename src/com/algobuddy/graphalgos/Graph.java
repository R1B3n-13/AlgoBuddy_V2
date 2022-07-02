package com.algobuddy.graphalgos;

import com.algobuddy.gui.Node;
import java.util.LinkedList;

/**
 *
 * @author nebir, nazrul
 */
public class Graph {

    private final LinkedList<Node>[] adj;

    Graph(int N) {
        adj = new LinkedList[N];
        for (int i = 0; i < N; i++) {
            adj[i] = new LinkedList<>();
        }
    }

    void addEdge(Node u, Node v) {
        adj[u.getNodeNum()].add(v);
    }

    LinkedList<Node>[] getAdj() {
        return adj;
    }
}
