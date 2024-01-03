package com.algobuddy.graphalgos;

import com.algobuddy.gui.AlgoWorker;
import com.algobuddy.gui.DrawArrow;
import com.algobuddy.gui.DrawLine;
import com.algobuddy.gui.Edge;
import com.algobuddy.gui.GraphBoard;
import static com.algobuddy.gui.GraphBoard.isDirected;
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
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import org.javatuples.Pair;

/**
 *
 * @author nebir, nazrul
 */
public class Dijkstra extends GraphBoard {

    private Graph g;
    private PriorityQueue<Pair<Integer, Node>> pq;
    private static int[] dis;
    private boolean l1, l2, l3, l4, l5;
    private AlgoWorker<Void, Void> dijkstraWorker;
    private List<Pair<Node, Node>> processingNodes;
    private Pair<Node, Node> runningEdge;
    private Node runningNode;
    private boolean completed = false;
    private int coefficient = 6;
    private Timer animationTimer;
    private double progress;

    @Override
    protected void paintComponent(Graphics g) {
        setPaintingBoundary(getSize());
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
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
            e.draw(g2d, isDirected());
        }
        for (Node n : nodes) {
            n.draw(g2d, i);
            i++;
        }

        for (i = coefficient; i < 32; i++) {
            g2d.setStroke(new BasicStroke(2));
            if ((i & 1) > 0) {
                g2d.setColor(new Color(154, 192, 230));
            } else {
                g2d.setColor(new Color(151, 188, 226));
            }

            g2d.fillRect((i * ((getWidth() - 700) / 26) + 2), getHeight() - 79, (getWidth() - 760) / 26, 27);

            if (!isPlaying()) {
                if (getSource() == null) {
                    g2d.setColor(new Color(0, 22, 40));
                    g2d.setFont(new Font("Consolas", Font.BOLD, 18));
                    g2d.drawString("-", i * ((getWidth() - 700) / 26) + getWidth() / 150, getHeight() - 60);
                }

                if (getSource() != null && i != coefficient) {
                    g2d.setColor(new Color(0, 22, 40));
                    g2d.setFont(new Font("Consolas", Font.BOLD, 18));
                    g2d.drawString("-", i * ((getWidth() - 700) / 26) + getWidth() / 150, getHeight() - 60);
                }
            }
        }

        g2d.setColor(new Color(161, 131, 199));
        g2d.setFont(new Font("Consolas", Font.ITALIC, 18));
        g2d.drawString("Min Heap:", ((getWidth() - 700) / 26) + getWidth() / 150, getHeight() - 60);

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
                g2d.drawString(String.valueOf((char) (getSource().getNodeNum() + 65)),
                        coefficient * ((getWidth() - 700) / 26) + getWidth() / 150, getHeight() - 60);
            }
        } else {
            l1 = true;
            l2 = false;
        }

        if (isSelecting()) {
            g2d.setColor(new Color(67, 102, 178, 60));
            Rectangle mouseRect = getMouseRect();
            g2d.fillRect(mouseRect.x, mouseRect.y,
                    mouseRect.width, mouseRect.height);
        }

        if (isPlaying()) {
            g2d.setColor(Color.RED);
            g2d.setFont(new Font("Consolas", Font.BOLD, 18));
            i = coefficient;
            for (Pair p : pq) {
                Node n = (Node) p.getValue1();
                g2d.drawString(String.valueOf((char) (n.getNodeNum() + 65)), i * ((getWidth() - 700) / 26) + getWidth() / 150, getHeight() - 60);
                i++;
            }
            g2d.setColor(new Color(0, 22, 40));
            while (i < 32) {
                g2d.drawString("-", i * ((getWidth() - 700) / 26) + getWidth() / 150, getHeight() - 60);
                i++;
            }

            for (Pair p : processingNodes) {

                Node n1 = (Node) p.getValue0();
                Node n2 = (Node) p.getValue1();

                if (isDirected()) {
                    if (p.equals(runningEdge)) {
                        new DrawArrow(g2d, n1.getLocation(), n2.getLocation(), progress, new Color(218, 226, 237),
                                new BasicStroke((float) 4), new BasicStroke(), 25);
                    } else {
                        new DrawArrow(g2d, n1.getLocation(), n2.getLocation(), 1, new Color(218, 226, 237),
                                new BasicStroke((float) 4), new BasicStroke(), 25);
                    }
                } else {
                    if (p.equals(runningEdge)) {
                        new DrawLine(g2d, n1.getLocation(), n2.getLocation(), progress, new Color(218, 226, 237), new BasicStroke(4));
                    } else {
                        new DrawLine(g2d, n1.getLocation(), n2.getLocation(), 1, new Color(218, 226, 237), new BasicStroke(4));
                    }
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
                g2d.setColor(new Color(234, 123, 114));
                g2d.setFont(new Font("Consolas", Font.BOLD, 18));
                g2d.drawString(String.valueOf((char) (runningNode.getNodeNum() + 65)),
                        (coefficient - 1) * ((getWidth() - 700) / 26) + getWidth() / 150, getHeight() - 60);
            }
        }

        for (Node n : nodes) {
            g2d.setFont(new Font("Casteller", Font.BOLD, 18));
            g2d.setColor(new Color(161, 56, 64));
            if (!isPlaying()) {
                if (getSource() == n) {
                    g2d.drawString("0", n.getLocation().x - 5, n.getLocation().y - 27);
                } else {
                    g2d.drawString("∞", n.getLocation().x - 5, n.getLocation().y - 27);
                }
            } else {
                String str = getDistance(n);
                int N = str.length();
                g2d.drawString(str, n.getLocation().x - N * 5, n.getLocation().y - 27);
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
            g2d.drawString("1. Create a min heap H", getWidth() - 315, getHeight() - 950);
            g2d.drawString("and assign ∞ to all the node distance", getWidth() - 315, getHeight() - 930);
        }
        if (l2) {
            g2d.drawString("2. Assign zero to the source distance", getWidth() - 315, getHeight() - 900);
            g2d.drawString("and put it into H", getWidth() - 318, getHeight() - 880);
        }
        if (l3) {
            g2d.drawString("3. While H is non- empty", getWidth() - 315, getHeight() - 850);
        }
        if (l4) {
            g2d.setColor(new Color(238, 247, 137));
            g2d.drawString("     i.  Remove the head u of H and", getWidth() - 315, getHeight() - 820);
            g2d.setColor(new Color(210, 52, 52));
        }
        if (l5) {
            g2d.setColor(new Color(238, 247, 137));
            g2d.drawString("     ii. For all the neighbors v of u", getWidth() - 315, getHeight() - 790);
            g2d.drawString("     if dist[v] > dist[u] + w(u, v)", getWidth() - 315, getHeight() - 770);
            g2d.drawString("        dist[v] ← dist[u] + w(u, v)", getWidth() - 315, getHeight() - 740);
            g2d.drawString("        and put v into H", getWidth() - 318, getHeight() - 720);
            g2d.setColor(new Color(210, 52, 52));
        }

        g2d.setColor(new Color(161, 131, 199));
        if (!l1) {
            g2d.drawString("1. Create a min heap H", getWidth() - 315, getHeight() - 950);
            g2d.drawString("and assign ∞ to all the node distance", getWidth() - 315, getHeight() - 930);
        }
        if (!l2) {
            g2d.drawString("2. Assign zero to the source distance", getWidth() - 315, getHeight() - 900);
            g2d.drawString("and put it into H", getWidth() - 318, getHeight() - 880);
        }
        if (!l3) {
            g2d.drawString("3. While H is non- empty", getWidth() - 315, getHeight() - 850);
        }
        if (!l4) {
            g2d.drawString("     i.  Remove the head u of H and", getWidth() - 315, getHeight() - 820);
        }
        if (!l5) {
            g2d.drawString("     ii. For all the neighbors v of u", getWidth() - 315, getHeight() - 790);
            g2d.drawString("     if dist[v] > dist[u] + w(u, v)", getWidth() - 315, getHeight() - 770);
            g2d.drawString("        dist[v] ← dist[u] + w(u, v)", getWidth() - 315, getHeight() - 740);
            g2d.drawString("        and put v into H", getWidth() - 318, getHeight() - 720);
        }
    }

    private void startAnimationTimer() {
        int delay = getSpeed() / 165;  // Set your desired delay (in milliseconds)
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
                    "No source node found!",
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
        dis = new int[nodes.size()];
        Arrays.fill(dis, Integer.MAX_VALUE);
        pq = new PriorityQueue<>(30, (a, b) -> a.getValue0() - b.getValue0());
        processingNodes = new ArrayList<>();
        for (Edge e : edges) {
            g.addEdge(e.getNode1(), e.getNode2(), e.getWeight());
            if (!isDirected()) {
                g.addEdge(e.getNode2(), e.getNode1(), e.getWeight());
            }
        }

        dis[getSource().getNodeNum()] = 0;
        pq.add(Pair.with(0, getSource()));

        dijkstraWorker = new AlgoWorker<>() {
            @Override
            public Void doInBackground() throws InterruptedException {
                while (!pq.isEmpty() && !isCancelled()) {
                    if (!isPaused()) {
                        Pair p = pq.poll();
                        Node u = (Node) p.getValue1();
                        runningNode = u;
                        l4 = l3 = true;
                        l1 = l2 = l5 = false;
                        repaint();
                        waitFor(getSpeed());
                        var it = g.getWadj()[u.getNodeNum()].listIterator();
                        while (it.hasNext()) {
                            if (!isPaused()) {
                                Pair q = it.next();
                                Node v = (Node) q.getValue0();
                                int w = (int) q.getValue1();
                                if (dis[v.getNodeNum()] > dis[u.getNodeNum()] + w) {
                                    runningEdge = new Pair<>(u, v);
                                    processingNodes.add(Pair.with(u, v));
                                    l5 = true;
                                    l1 = l2 = l4 = false;
                                    startAnimationTimer();
                                    waitFor(getSpeed());
                                    stopAnimationTimer();
                                    dis[v.getNodeNum()] = dis[u.getNodeNum()] + w;
                                    pq.add(Pair.with(dis[v.getNodeNum()], v));
                                    repaint();
                                    waitFor(getSpeed());
                                }
                            } else {
                                waitFor(200);
                            }
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
                repaint();
                GraphPanel.getPlayLabel().setIcon(new ImageIcon("src" + File.separator + "com" + File.separator + "algobuddy" + File.separator + "gui" + File.separator + "img" + File.separator + "playDisabled.png"));
            }
        };
        dijkstraWorker.execute();
    }

    public AlgoWorker<Void, Void> getDijkstraWorker() {
        return dijkstraWorker;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void resetCode() {
        l1 = l2 = l3 = l4 = l5 = false;
    }

    public static String getDistance(Node n) {
        if (dis[n.getNodeNum()] != Integer.MAX_VALUE) {
            return String.valueOf(dis[n.getNodeNum()]);
        }
        return "∞";
    }
}
