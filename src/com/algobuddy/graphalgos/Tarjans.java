package com.algobuddy.graphalgos;

import com.algobuddy.gui.AlgoWorker;
import com.algobuddy.gui.DrawArrow;
import com.algobuddy.gui.Edge;
import com.algobuddy.gui.GraphBoard;
import static com.algobuddy.gui.GraphBoard.isPlaying;
import com.algobuddy.gui.GraphPanel;
import com.algobuddy.gui.Node;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import javax.swing.ImageIcon;
import javax.swing.Timer;
import org.javatuples.Pair;

public class Tarjans extends GraphBoard {

    private Stack<Node> stack;                          // DFS stack
    private int[] disc, low;                            // Discovery and low-link arrays
    private boolean[] onStack;                          // Tracks nodes currently on stack
    private List<List<Node>> sccs;                      // Stores identified SCCs
    private int time;                                   // Discovery time counter
    private Color[] sccColors;                          // Colors for SCCs
    private Node poppedNode;                            // Node popped
    private List<Pair<Node, Node>> processedEdges;      // Edges that are being processed or have already been processed
    private boolean l1, l2, l3, l4, l5, l6, l7, l8, l9; // Flags to highlight algorithm steps
    private AlgoWorker<Void, Void> tarjansWorker;       // Worker thread for algorithm execution
    private boolean completed = false;                  // Tracks algorithm completion
    private int coefficient = 6;                        // Offset for visualization slots
    private Edge runningEdge;                           // Current edge being animated
    private Node runningNode;                           // Current node being animated
    private boolean isBacktracking;                     // Flag to show running node in different color when backtracking 
    private double progress;                            // Animation progress (0 to 1)
    private Timer animationTimer;                       // Timer for edge animation

    @Override
    protected void paintComponent(Graphics gfx) {
        setPaintingBoundary(getSize());
        super.paintComponent(gfx);
        Graphics2D g2d = (Graphics2D) gfx;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw background gradient
        Color color1 = new Color(0, 22, 41);
        Color color2 = new Color(0, 23, 39);
        GradientPaint gp = new GradientPaint(0, 0, color1, getWidth(), getHeight(), color2);
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        // Draw graph boundary
        g2d.setStroke(new BasicStroke((float) 1.5));
        g2d.setColor(new Color(118, 127, 131));
        g2d.drawRect(24, 24, getWidth() - 350, getHeight() - 150);

        // Draw edges
        int i = 'A';
        for (Edge e : edges) {
            e.draw(g2d, true);
        }

        // Draw nodes
        for (Node n : nodes) {
            n.draw(g2d, i);
            i++;
        }

        // Draw selection area
        if (isSelecting()) {
            g2d.setColor(new Color(67, 102, 178, 60));
            Rectangle mouseRect = getMouseRect();
            g2d.fillRect(mouseRect.x, mouseRect.y,
                    mouseRect.width, mouseRect.height);
        }

        // Draw edges with animation for runningEdge
        if (isPlaying() && processedEdges != null) {
            for (Pair p : processedEdges) {
                Node n1 = (Node) p.getValue0();
                Node n2 = (Node) p.getValue1();

                if (runningEdge != null && n1.equals(runningEdge.getNode1()) && n2.equals(runningEdge.getNode2())) {
                    new DrawArrow(g2d, n1.getLocation(), n2.getLocation(), progress, new Color(218, 226, 237),
                            new BasicStroke((float) 4), new BasicStroke(), 25);
                } else {
                    new DrawArrow(g2d, n1.getLocation(), n2.getLocation(), 1, new Color(218, 226, 237),
                            new BasicStroke((float) 4), new BasicStroke(), 25);
                }

                g2d.setColor(new Color(245, 204, 158));
                g2d.fillOval(n1.getLocation().x - Node.getRadius(), n1.getLocation().y - Node.getRadius(),
                        2 * Node.getRadius(), 2 * Node.getRadius());
                if ((runningEdge == null || (!n1.equals(runningEdge.getNode1()) || !n2.equals(runningEdge.getNode2()))) || progress >= 1) {
                    g2d.fillOval(n2.getLocation().x - Node.getRadius(), n2.getLocation().y - Node.getRadius(),
                            2 * Node.getRadius(), 2 * Node.getRadius());
                }

                g2d.setColor(new Color(0, 22, 40));
                g2d.setFont(new Font("Casteller", Font.BOLD, 18));
                g2d.drawString(String.valueOf((char) (n1.getNodeNum() + 65)), n1.getLocation().x - 5,
                        n1.getLocation().y + 5);
                g2d.drawString(String.valueOf((char) (n2.getNodeNum() + 65)), n2.getLocation().x - 5,
                        n2.getLocation().y + 5);
            }
        }

        //  Draw SCCs
        if (isPlaying() && sccs != null) {
            for (Node n : nodes) {
                for (int j = 0; j < sccs.size(); j++) {
                    List<Node> scc = sccs.get(j);
                    if (scc.contains(n)) {
                        g2d.setColor(sccColors[j]);
                        g2d.fillOval(n.getLocation().x - Node.getRadius(), n.getLocation().y - Node.getRadius(),
                                2 * Node.getRadius(), 2 * Node.getRadius());

                        g2d.setColor(new Color(0, 22, 40));
                        g2d.setFont(new Font("Casteller", Font.BOLD, 18));
                        g2d.drawString(String.valueOf((char) (n.getNodeNum() + 65)), n.getLocation().x - 5,
                                n.getLocation().y + 5);
                        break;
                    }
                }
            }
        }

        // Draw nodes with disc/low values
        for (Node n : nodes) {
            if (isPlaying() && disc != null && low != null && disc[n.getNodeNum()] != 0) {
                String discLow = disc[n.getNodeNum()] + "/" + low[n.getNodeNum()];
                int N = discLow.length();
                g2d.setFont(new Font("Casteller", Font.BOLD, 18));
                g2d.setColor(new Color(161, 56, 64));
                g2d.drawString(discLow, n.getLocation().x - N * 5, n.getLocation().y - 27);
            }
        }

        // Draw DFS Stack visualization
        for (i = coefficient; i < coefficient + 26; i++) {
            g2d.setStroke(new BasicStroke(2));
            g2d.setColor((i & 1) > 0 ? new Color(154, 192, 230) : new Color(151, 188, 226));
            g2d.fillRect((i * ((getWidth() - 700) / 26) + 2), getHeight() - 79, (getWidth() - 760) / 26, 27);
        }
        g2d.setColor(new Color(161, 131, 199));
        g2d.setFont(new Font("Consolas", Font.ITALIC, 18));
        g2d.drawString("DFS Stack:", ((getWidth() - 700) / 26) + getWidth() / 150, getHeight() - 60);

        if (isPlaying() && stack != null) {
            g2d.setColor(Color.RED);
            g2d.setFont(new Font("Consolas", Font.BOLD, 18));
            i = coefficient;
            for (Node n : stack) {
                g2d.drawString(String.valueOf((char) (n.getNodeNum() + 65)), i * ((getWidth() - 700) / 26) + getWidth() / 150, getHeight() - 60);
                i++;
            }
            g2d.setColor(new Color(0, 22, 40));
            while (i < coefficient + 26) {
                g2d.drawString("-", i * ((getWidth() - 700) / 26) + getWidth() / 150, getHeight() - 79);
                i++;
            }
        } else {
            g2d.setColor(new Color(0, 22, 40));
            for (i = coefficient; i < coefficient + 26; i++) {
                g2d.drawString("-", i * ((getWidth() - 700) / 26) + getWidth() / 150, getHeight() - 79);
            }
        }

        // Draw popped nodes
        if (poppedNode != null) {
            g2d.setColor(new Color(234, 123, 114));
            g2d.setFont(new Font("Consolas", Font.BOLD, 18));
            i = coefficient;
            g2d.drawString(String.valueOf((char) (poppedNode.getNodeNum() + 65)), (i - 1) * ((getWidth() - 700) / 26) + getWidth() / 150, getHeight() - 60);
            i++;
        }

        // Draw running node
        if (isPlaying() && runningNode != null && !completed) {
            g2d.setStroke(new BasicStroke(3));
            if (!isBacktracking) {
                g2d.setColor(new Color(240, 10, 9));
            } else {
                g2d.setColor(new Color(113, 121, 126));
            }
            g2d.drawOval(runningNode.getLocation().x - Node.getRadius(), runningNode.getLocation().y - Node.getRadius(),
                    2 * Node.getRadius(), 2 * Node.getRadius());
        }

        // Draw algorithm steps
        g2d.setColor(new Color(177, 191, 222));
        g2d.setFont(new Font("Consolas", Font.BOLD, 30));
        g2d.drawString("Algorithm", getWidth() - 240, getHeight() - 1000);
        g2d.setColor(new Color(210, 52, 52));
        g2d.setFont(new Font("Consolas", Font.ITALIC, 15));

        if (l1) {
            g2d.drawString("1. Perform DFS from an unvisited node", getWidth() - 315, getHeight() - 950);
            g2d.drawString("   until all the nodes are visited.", getWidth() - 315, getHeight() - 930);
        }
        if (l2) {
            g2d.drawString("2. In DFS recursion:", getWidth() - 315, getHeight() - 900);
        }
        if (l3) {
            g2d.setColor(new Color(238, 247, 137));
            g2d.drawString("      i. Assign discovery and low-link", getWidth() - 315, getHeight() - 870);
            g2d.drawString("      values to the current node u.", getWidth() - 315, getHeight() - 850);
            g2d.setColor(new Color(210, 52, 52));
        }
        if (l4) {
            g2d.setColor(new Color(238, 247, 137));
            g2d.drawString("      ii. If neighbor v of u", getWidth() - 315, getHeight() - 820);
            g2d.drawString("      is not visited", getWidth() - 315, getHeight() - 800);
            g2d.drawString("         recursively call DFS on v", getWidth() - 315, getHeight() - 780);
            g2d.setColor(new Color(210, 52, 52));
        }
        if (l5) {
            g2d.setColor(new Color(238, 247, 137));
            g2d.drawString("      iii. If v is already visited", getWidth() - 315, getHeight() - 750);
            g2d.drawString("      and is in the stack at u", getWidth() - 315, getHeight() - 730);
            g2d.drawString("         low[u] ← min(low[u], disc[v])", getWidth() - 315, getHeight() - 710);
            g2d.setColor(new Color(210, 52, 52));
        }
        if (l6) {
            g2d.setColor(new Color(238, 247, 137));
            g2d.drawString("      iv. When backtracking to u from v", getWidth() - 315, getHeight() - 680);
            g2d.drawString("      low[u] ← min(low[u], low[v])", getWidth() - 315, getHeight() - 660);
            g2d.setColor(new Color(210, 52, 52));
        }
        if (l7) {
            g2d.setColor(new Color(238, 247, 137));
            g2d.drawString("      v. After processing all neighbors", getWidth() - 315, getHeight() - 630);
            g2d.drawString("      of u, if low[u] == disc[u]", getWidth() - 315, getHeight() - 610);
            g2d.setColor(new Color(210, 52, 52));
        }
        if (l8) {
            g2d.setColor(new Color(120, 255, 120));
            g2d.drawString("        a. Pop from the stack till u", getWidth() - 315, getHeight() - 580);
            g2d.setColor(new Color(210, 52, 52));
        }
        if (l9) {
            g2d.setColor(new Color(120, 255, 120));
            g2d.drawString("        b. Add the popped nodes in SCC", getWidth() - 315, getHeight() - 550);
            g2d.setColor(new Color(210, 52, 52));
        }

        g2d.setColor(new Color(161, 131, 199));
        if (!l1) {
            g2d.drawString("1. Perform DFS from an unvisited node", getWidth() - 315, getHeight() - 950);
            g2d.drawString("   until all the nodes are visited.", getWidth() - 315, getHeight() - 930);
        }
        if (!l2) {
            g2d.drawString("2. In DFS recursion:", getWidth() - 315, getHeight() - 900);
        }
        if (!l3) {
            g2d.drawString("      i. Assign discovery and low-link", getWidth() - 315, getHeight() - 870);
            g2d.drawString("      values to the current node u.", getWidth() - 315, getHeight() - 850);
        }
        if (!l4) {
            g2d.drawString("      ii. If neighbor v of u", getWidth() - 315, getHeight() - 820);
            g2d.drawString("      is not visited", getWidth() - 315, getHeight() - 800);
            g2d.drawString("         recursively call DFS on v", getWidth() - 315, getHeight() - 780);
        }
        if (!l5) {
            g2d.drawString("      iii. If v is already visited", getWidth() - 315, getHeight() - 750);
            g2d.drawString("      and is in the stack at u", getWidth() - 315, getHeight() - 730);
            g2d.drawString("         low[u] ← min(low[u], disc[v])", getWidth() - 315, getHeight() - 710);
        }
        if (!l6) {
            g2d.drawString("      iv. When backtracking to u from v", getWidth() - 315, getHeight() - 680);
            g2d.drawString("      low[u] ← min(low[u], low[v])", getWidth() - 315, getHeight() - 660);
        }
        if (!l7) {
            g2d.drawString("      v. After processing all neighbors", getWidth() - 315, getHeight() - 630);
            g2d.drawString("      of u, if low[u] == disc[u]", getWidth() - 315, getHeight() - 610);
        }
        if (!l8) {
            g2d.drawString("         a. Pop from the stack till u", getWidth() - 315, getHeight() - 580);
        }
        if (!l9) {
            g2d.drawString("         b. Add the popped nodes in SCC", getWidth() - 315, getHeight() - 550);
        }
    }

    /**
     * Generate distinct colors for up to 26 SCCs
     */
    private Color[] generateColors() {

        ArrayList<Color> colors = new ArrayList<>();

        colors.add(new Color(224, 176, 255)); // Mauve 1
        colors.add(new Color(229, 43, 80));   // Amaranth 2
        colors.add(new Color(254, 178, 76));  // Orange 3
        colors.add(new Color(125, 249, 255)); // Electric Blue 4
        colors.add(new Color(208, 240, 192)); // Tea Green 5
        colors.add(new Color(244, 194, 194)); // Tea Rose 6
        colors.add(new Color(34, 181, 246));  // Cyan 7
        colors.add(new Color(245, 130, 51));  // Amber 8
        colors.add(new Color(204, 204, 255)); // Periwinkle 9
        colors.add(new Color(255, 36, 0));    // Scarlet 10
        colors.add(new Color(185, 217, 235)); // Columbia Blue 11
        colors.add(new Color(232, 193, 112)); // Gold 12
        colors.add(new Color(247, 92, 128));  // Salmon 13
        colors.add(new Color(227, 218, 201)); // Bone 14
        colors.add(new Color(181, 122, 167)); // Lavender 15
        colors.add(new Color(120, 133, 205)); // Periwinkle 16
        colors.add(new Color(233, 132, 209)); // Pink 17
        colors.add(new Color(237, 167, 155)); // Coral 18
        colors.add(new Color(0, 255, 191));   // Aquamarine 19
        colors.add(new Color(252, 138, 10));  // Tangerine 20
        colors.add(new Color(206, 61, 100));  // Cerise 21
        colors.add(new Color(255, 153, 102)); // Atomic Tangerine 22
        colors.add(new Color(196, 147, 190)); // Orchid 23
        colors.add(new Color(170, 170, 170)); // Silver 24
        colors.add(new Color(233, 116, 81));  // Burnt Sienna 25
        colors.add(new Color(178, 132, 190)); // African Violet 26

        return colors.toArray(Color[]::new);
    }

    /**
     * Start the animation timer for edge traversal
     */
    private void startAnimationTimer() {
        int delay = getSpeed() / 165; // Adjust based on speed
        animationTimer = new Timer(delay, e -> {
            progress += 0.01;
            progress = Math.min(progress, 1);
            repaint();
        });
        animationTimer.start();
    }

    /**
     * Stop the animation timer
     */
    private void stopAnimationTimer() {
        if (animationTimer != null && animationTimer.isRunning()) {
            animationTimer.stop();
            progress = 0;
            runningEdge = null;
        }
    }

    public void start() {
        completed = false;
        stack = new Stack<>();
        disc = new int[nodes.size()];
        low = new int[nodes.size()];
        onStack = new boolean[nodes.size()];
        sccs = new ArrayList<>();
        processedEdges = new ArrayList<>();
        time = 0;
        sccColors = generateColors();
        poppedNode = null;
        progress = 0;
        runningEdge = null;

        tarjansWorker = new AlgoWorker<>() {
            @Override
            public Void doInBackground() throws InterruptedException {
                for (int i = 0; i < nodes.size(); i++) {
                    if (isCancelled()) {
                        return null;
                    }
                    if (!isPaused()) {
                        if (disc[i] == 0) {
                            runningNode = nodes.get(i);
                            l1 = true;
                            repaint();
                            waitFor(getSpeed() / 2);

                            l1 = false;
                            l2 = true;
                            dfs(i);
                            l2 = false;
                        }
                    } else {
                        waitFor(200);
                    }
                }
                return null;
            }

            private void dfs(int u) throws InterruptedException {

                if (isCancelled()) {
                    return;
                }

                disc[u] = low[u] = ++time;
                stack.push(nodes.get(u));
                onStack[u] = true;
                l3 = true;
                l4 = false;
                repaint();
                waitFor(getSpeed() / 2);
                l3 = false;

                runningNode = nodes.get(u);
                repaint();
                waitFor(getSpeed() / 2);

                for (Edge e : edges) {

                    if (isCancelled()) {
                        return;
                    }

                    if (!isPaused()) {
                        if (e.getNode1().getNodeNum() == u) {
                            int v = e.getNode2().getNodeNum();
                            if (disc[v] == 0) {
                                runningEdge = e;
                                Pair p = Pair.with(e.getNode1(), e.getNode2());
                                processedEdges.add(p);
                                l3 = l5 = l6 = false;
                                l4 = true;
                                startAnimationTimer();
                                waitFor(getSpeed());
                                stopAnimationTimer();
                                dfs(v);

                                isBacktracking = true;
                                l4 = l5 = l3 = false;
                                l6 = true;
                                runningNode = e.getNode1();
                                repaint();
                                waitFor(getSpeed() / 2);
                                low[u] = Math.min(low[u], low[v]);
                                repaint();
                                waitFor(getSpeed());
                                isBacktracking = false;
                            } else if (onStack[v]) {
                                l4 = l3 = l6 = false;
                                l5 = true;
                                low[u] = Math.min(low[u], disc[v]);
                                repaint();
                                waitFor(getSpeed());
                            }
                        }
                    } else {
                        waitFor(200);
                    }
                }

                isBacktracking = true;
                repaint();
                waitFor(getSpeed() / 2);
                if (low[u] == disc[u]) {
                    l3 = l4 = l5 = l6 = false;
                    l7 = true;
                    List<Node> scc = new ArrayList<>();
                    while (!stack.isEmpty() && !isCancelled()) {
                        if (!isPaused()) {
                            Node node = stack.pop();
                            poppedNode = node;
                            onStack[node.getNodeNum()] = false;
                            l8 = true;
                            repaint();
                            waitFor(getSpeed());
                            scc.add(node);
                            poppedNode = null;
                            repaint();
                            waitFor(getSpeed() / 4);
                            if (node.getNodeNum() == u) {
                                break;
                            }
                        } else {
                            waitFor(200);
                        }
                    }
                    sccs.add(scc);
                    l8 = false;
                    l9 = true;
                    repaint();
                    waitFor(getSpeed());
                    l9 = false;
                }
                l7 = false;
                isBacktracking = false;
            }

            @Override
            public void done() {
                super.done();
                completed = true;
                resetCode();
                if (!isCancelled()) {
                    GraphPanel.getPlayLabel().setIcon(new ImageIcon("src" + File.separator + "com" + File.separator + "algobuddy" + File.separator + "gui" + File.separator + "img" + File.separator + "playDisabled.png"));
                }
                repaint();
            }
        };
        tarjansWorker.execute();
    }

    public AlgoWorker<Void, Void> getTarjansWorker() {
        return tarjansWorker;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void resetCode() {
        l1 = l2 = l3 = l4 = l5 = l6 = l7 = l8 = l9 = false;
    }
}
