package com.algobuddy.graphalgos;

import com.algobuddy.gui.AlgoWorker;
import com.algobuddy.gui.DrawLine;
import com.algobuddy.gui.Edge;
import com.algobuddy.gui.GraphBoard;
import static com.algobuddy.gui.GraphBoard.isPlaying;
import static com.algobuddy.gui.GraphBoard.setPlayingState;
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
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import org.javatuples.Pair;

/**
 *
 * @author Nebir, Nazrul
 */
public class Prims extends GraphBoard {

    private Graph g;
    private PriorityQueue<Pair<Integer, Pair<Node, Node>>> pq; // (weight, (node1, node2))
    private HashSet<Node> inMST; // Tracks nodes included in MST
    private boolean l1, l2, l3, l4, l5;
    private AlgoWorker<Void, Void> primsWorker;
    private List<Pair<Node, Node>> mstEdges; // Edges in the MST
    private Pair<Node, Node> runningEdge; // Current edge being processed
    private Node runningNode; // Current node being processed
    private boolean completed = false;
    private int coefficient = 6;
    private Timer animationTimer;
    private double progress;

    @Override
    protected void paintComponent(Graphics gfx) {
        setPaintingBoundary(getSize());
        super.paintComponent(gfx);
        Graphics2D g2d = (Graphics2D) gfx;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Color color1 = new Color(0, 22, 41);
        Color color2 = new Color(0, 23, 39);
        GradientPaint gp = new GradientPaint(0, 0, color1, getWidth(), getHeight(), color2);
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        g2d.setStroke(new BasicStroke((float) 1.5));
        g2d.setColor(new Color(118, 127, 131));
        g2d.drawRect(24, 24, getWidth() - 350, getHeight() - 150);

        int i = 'A';
        for (Edge e : edges) {
            e.draw(g2d, false);
        }

        for (Node n : nodes) {
            n.draw(g2d, i);
            i++;
        }

        for (i = coefficient; i < coefficient + 26; i++) {
            g2d.setStroke(new BasicStroke(2));
            if ((i & 1) > 0) {
                g2d.setColor(new Color(154, 192, 230));
            } else {
                g2d.setColor(new Color(151, 188, 226));
            }
            g2d.fillRect((i * ((getWidth() - 700) / 26) + 2), getHeight() - 96, (getWidth() - 760) / 26, 27);
            g2d.fillRect((i * ((getWidth() - 700) / 26) + 2), getHeight() - 38, (getWidth() - 760) / 26, 27);

            if (!isPlaying()) {
                if (getSource() == null) {
                    g2d.setColor(new Color(0, 22, 40));
                    g2d.setFont(new Font("Consolas", Font.BOLD, 18));
                    g2d.drawString("-", i * ((getWidth() - 700) / 26) + getWidth() / 150, getHeight() - 77);
                    g2d.drawString("-", i * ((getWidth() - 700) / 26) + getWidth() / 150, getHeight() - 19);
                }
            }
        }

        g2d.setColor(new Color(161, 131, 199));
        g2d.setFont(new Font("Consolas", Font.ITALIC, 18));
        g2d.drawString("MST Nodes:", ((getWidth() - 700) / 26) + getWidth() / 150, getHeight() - 77);
        g2d.drawString("Min Heap:", ((getWidth() - 700) / 26) + getWidth() / 150, getHeight() - 19);

        if (getSource() != null) {
            l1 = false;
            g2d.setColor(new Color(231, 204, 158));
            g2d.fillOval(getSource().getLocation().x - Node.getRadius(), getSource().getLocation().y - Node.getRadius(),
                    2 * Node.getRadius(), 2 * Node.getRadius());
            g2d.setColor(new Color(0, 22, 40));
            g2d.setFont(new Font("Casteller", Font.BOLD, 18));
            g2d.drawString(String.valueOf((char) (getSource().getNodeNum() + 65)), getSource().getLocation().x - 5,
                    getSource().getLocation().y + 5);
            if (!isPlaying()) {
                l2 = true;
                g2d.setColor(Color.RED);
                g2d.setFont(new Font("Consolas", Font.BOLD, 18));

                g2d.drawString(String.valueOf((char) (getSource().getNodeNum() + 65)), coefficient * ((getWidth() - 700) / 26) + getWidth() / 150, getHeight() - 77);

                g2d.setColor(new Color(0, 22, 40));
                i = coefficient + 1;
                while (i < coefficient + 26) {
                    g2d.drawString("-", i * ((getWidth() - 700) / 26) + getWidth() / 150, getHeight() - 77);
                    i++;
                }

                PriorityQueue<Pair<Integer, Pair<Node, Node>>> pqTemp = new PriorityQueue<>(30, (a, b) -> a.getValue0() - b.getValue0());
                for (Edge e : edges) {
                    if (e.getNode1() == getSource() || e.getNode2() == getSource()) {
                        Node otherNode = e.getNode1() == getSource() ? e.getNode2() : e.getNode1();
                        pqTemp.add(Pair.with(e.getWeight(), new Pair<>(getSource(), otherNode)));

                    }
                }

                g2d.setColor(Color.RED);
                i = coefficient;
                for (Pair<Integer, Pair<Node, Node>> p : pqTemp) {
                    Node n1 = p.getValue1().getValue0();
                    Node n2 = p.getValue1().getValue1();
                    g2d.drawString(String.valueOf((char) (n1.getNodeNum() + 65)) + String.valueOf((char) (n2.getNodeNum() + 65)), i * ((getWidth() - 700) / 26) + getWidth() / 150, getHeight() - 19);
                    i++;
                }

                g2d.setColor(new Color(0, 22, 40));
                while (i < coefficient + 26) {
                    g2d.drawString("-", i * ((getWidth() - 700) / 26) + getWidth() / 150, getHeight() - 19);
                    i++;
                }
            }
        } else {
            l1 = true;
            l2 = false;
        }

        if (isSelecting()) {
            g2d.setColor(new Color(67, 102, 178, 60));
            Rectangle mouseRect = getMouseRect();
            g2d.fillRect(mouseRect.x, mouseRect.y, mouseRect.width, mouseRect.height);
        }

        if (isPlaying()) {
            g2d.setColor(Color.RED);
            g2d.setFont(new Font("Consolas", Font.BOLD, 18));
            i = coefficient;
            for (Node n : inMST) {
                g2d.drawString(String.valueOf((char) (n.getNodeNum() + 65)), i * ((getWidth() - 700) / 26) + getWidth() / 150, getHeight() - 77);
                i++;
            }
            g2d.setColor(new Color(0, 22, 40));
            while (i < coefficient + 26) {
                g2d.drawString("-", i * ((getWidth() - 700) / 26) + getWidth() / 150, getHeight() - 77);
                i++;
            }

            g2d.setColor(Color.RED);
            i = coefficient;
            for (Pair<Integer, Pair<Node, Node>> p : pq) {
                if (i == coefficient + 25) {
                    g2d.setColor(new Color(0, 22, 40));
                    g2d.drawString("…", i * ((getWidth() - 700) / 26) + getWidth() / 150, getHeight() - 19);
                    i++;
                    break;
                }
                Node n1 = p.getValue1().getValue0();
                Node n2 = p.getValue1().getValue1();
                g2d.drawString(String.valueOf((char) (n1.getNodeNum() + 65)) + String.valueOf((char) (n2.getNodeNum() + 65)), i * ((getWidth() - 700) / 26) + getWidth() / 150, getHeight() - 19);
                i++;
            }
            g2d.setColor(new Color(0, 22, 40));
            while (i < coefficient + 26) {
                g2d.drawString("-", i * ((getWidth() - 700) / 26) + getWidth() / 150, getHeight() - 19);
                i++;
            }

            for (Pair<Node, Node> p : mstEdges) {
                Node n1 = p.getValue0();
                Node n2 = p.getValue1();

                if (p.equals(runningEdge)) {
                    new DrawLine(g2d, n1.getLocation(), n2.getLocation(), progress, new Color(218, 226, 237), new BasicStroke(4));
                } else {
                    new DrawLine(g2d, n1.getLocation(), n2.getLocation(), 1, new Color(255, 68, 87), new BasicStroke(4));
                }

                g2d.setColor(new Color(245, 204, 158));
                g2d.fillOval(n1.getLocation().x - Node.getRadius(), n1.getLocation().y - Node.getRadius(),
                        2 * Node.getRadius(), 2 * Node.getRadius());
                if (!p.equals(runningEdge) || progress >= 1) {
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

            if (!completed) {
                g2d.setStroke(new BasicStroke(3));
                g2d.setColor(new Color(240, 10, 9));
                g2d.drawOval(runningNode.getLocation().x - Node.getRadius(), runningNode.getLocation().y - Node.getRadius(),
                        2 * Node.getRadius(), 2 * Node.getRadius());
                g2d.setFont(new Font("Consolas", Font.BOLD, 18));
                if (runningEdge != null) {
                    g2d.setColor(new Color(234, 123, 114));
                    g2d.drawString(String.valueOf((char) (runningEdge.getValue0().getNodeNum() + 65)) + String.valueOf((char) (runningEdge.getValue1().getNodeNum() + 65)),
                            (coefficient - 1) * ((getWidth() - 700) / 26) + getWidth() / 150, getHeight() - 19);
                }

            }
        }

        for (Edge e : edges) {
            int X = (e.getNode1().getLocation().x + e.getNode2().getLocation().x) / 2;
            int Y = (e.getNode1().getLocation().y + e.getNode2().getLocation().y) / 2;
            String str = String.valueOf(e.getWeight());
            int N = str.length();
            g2d.setColor(new Color(119, 142, 209));
            g2d.fillRect(X - N * 5 - 3, Y - 9, N * 10 + 6, 18);
            g2d.setColor(new Color(0, 22, 40));
            g2d.setFont(new Font("Casteller", Font.BOLD, 18));
            g2d.drawString(str, X - N * 5, Y + 6);
        }

        g2d.setColor(new Color(177, 191, 222));
        g2d.setFont(new Font("Consolas", Font.BOLD, 30));
        g2d.drawString("Algorithm", getWidth() - 240, getHeight() - 1000);
        g2d.setColor(new Color(210, 52, 52));
        g2d.setFont(new Font("Consolas", Font.ITALIC, 15));
        if (l1) {
            g2d.drawString("1. Create a min heap H for edges", getWidth() - 315, getHeight() - 950);
            g2d.drawString("and an unordered set S for MST nodes", getWidth() - 315, getHeight() - 930);
        }
        if (l2) {
            g2d.drawString("2. Insert a random starting node in S", getWidth() - 315, getHeight() - 900);
            g2d.drawString("and add its edges to H", getWidth() - 318, getHeight() - 880);
        }
        if (l3) {
            g2d.drawString("3. While H is non-empty", getWidth() - 315, getHeight() - 850);
        }
        if (l4) {
            g2d.setColor(new Color(238, 247, 137));
            g2d.drawString("     i.  Pick min edge (u, v) from H", getWidth() - 315, getHeight() - 820);
            g2d.setColor(new Color(210, 52, 52));
        }
        if (l5) {
            g2d.setColor(new Color(238, 247, 137));
            g2d.drawString("     ii. If v not in S,", getWidth() - 315, getHeight() - 790);
            g2d.drawString("         add (u, v) to MST", getWidth() - 315, getHeight() - 770);
            g2d.drawString("         Add v to S", getWidth() - 315, getHeight() - 750);
            g2d.drawString("         Add v's edges to H", getWidth() - 315, getHeight() - 730);
            g2d.setColor(new Color(210, 52, 52));
        }

        g2d.setColor(new Color(161, 131, 199));
        if (!l1) {
            g2d.drawString("1. Create a min heap H for edges", getWidth() - 315, getHeight() - 950);
            g2d.drawString("and a set S for MST nodes", getWidth() - 315, getHeight() - 930);
        }
        if (!l2) {
            g2d.drawString("2. Insert a random starting node in S", getWidth() - 315, getHeight() - 900);
            g2d.drawString("and add its edges to H", getWidth() - 318, getHeight() - 880);
        }
        if (!l3) {
            g2d.drawString("3. While H is non-empty", getWidth() - 315, getHeight() - 850);
        }
        if (!l4) {
            g2d.drawString("     i.  Pick min edge (u, v) from H", getWidth() - 315, getHeight() - 820);
        }
        if (!l5) {
            g2d.drawString("     ii. If v not in S,", getWidth() - 315, getHeight() - 790);
            g2d.drawString("            add (u, v) to MST", getWidth() - 315, getHeight() - 770);
            g2d.drawString("            Add v to S", getWidth() - 315, getHeight() - 750);
            g2d.drawString("            Add v's edges to H", getWidth() - 315, getHeight() - 730);
        }
    }

    private void startAnimationTimer() {
        int delay = getSpeed() / 165;
        animationTimer = new Timer(delay, e -> {
            progress += 0.01;
            progress = Math.min(progress, 1);
            repaint();
        });
        animationTimer.start();
    }

    private void stopAnimationTimer() {
        if (animationTimer != null && animationTimer.isRunning()) {
            animationTimer.stop();
            progress = 0;
            runningEdge = null;
        }
    }

    public void start() {
        if (getSource() == null) {
            JOptionPane.showMessageDialog(null,
                    "No starting node found!",
                    "ERROR!",
                    JOptionPane.ERROR_MESSAGE);
            setPlayingState(false);
            GraphPanel.getPlayLabel().setIcon(new ImageIcon("src" + File.separator + "com" + File.separator + "algobuddy" + File.separator + "gui" + File.separator + "img" + File.separator + "playEnabled.png"));
            GraphPanel.getResetLabel().setIcon(new ImageIcon("src" + File.separator + "com" + File.separator + "algobuddy" + File.separator + "gui" + File.separator + "img" + File.separator + "resetDisabled.png"));
            repaint();
            return;
        }
        completed = false;
        g = new Graph(nodes.size());
        pq = new PriorityQueue<>(30, (a, b) -> a.getValue0() - b.getValue0());
        inMST = new HashSet<>();
        mstEdges = new ArrayList<>();

        for (Edge e : edges) {
            g.addEdge(e.getNode1(), e.getNode2(), e.getWeight());
            g.addEdge(e.getNode2(), e.getNode1(), e.getWeight());
        }

        inMST.add(getSource());
        addEdgesToPQ(getSource());

        primsWorker = new AlgoWorker<>() {
            @Override
            public Void doInBackground() throws InterruptedException {
                while (!pq.isEmpty() && !isCancelled()) {
                    if (!isPaused()) {
                        Pair<Integer, Pair<Node, Node>> p = pq.poll();
                        Node u = p.getValue1().getValue0();
                        Node v = p.getValue1().getValue1();

                        runningNode = u;
                        runningEdge = new Pair<>(u, v);
                        l3 = l4 = true;
                        l1 = l2 = l5 = false;
                        repaint();
                        waitFor(getSpeed());

                        if (!inMST.contains(v)) {
                            mstEdges.add(new Pair<>(u, v));
                            inMST.add(v);
                            l5 = true;
                            l1 = l2 = l4 = false;
                            startAnimationTimer();
                            waitFor(getSpeed());
                            stopAnimationTimer();
                            addEdgesToPQ(v);
                            repaint();
                            waitFor(getSpeed());
                        }
                    } else {
                        waitFor(200);
                    }
                }
                return null;
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
        primsWorker.execute();
    }

    private void addEdgesToPQ(Node u) {
        var it = g.getWadj()[u.getNodeNum()].listIterator();
        while (it.hasNext()) {
            Pair<Node, Integer> q = it.next();
            Node v = q.getValue0();
            int w = q.getValue1();
            if (!inMST.contains(v)) {
                pq.add(Pair.with(w, new Pair<>(u, v)));
            }
        }
    }

    public AlgoWorker<Void, Void> getPrimsWorker() {
        return primsWorker;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void resetCode() {
        l1 = l2 = l3 = l4 = l5 = false;
    }
}
