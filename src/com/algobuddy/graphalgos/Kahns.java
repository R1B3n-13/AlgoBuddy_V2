package com.algobuddy.graphalgos;

/**
 *
 * @author nebir, nazrul
 */
import com.algobuddy.gui.AlgoWorker;
import com.algobuddy.gui.DrawArrow;
import com.algobuddy.gui.Edge;
import com.algobuddy.gui.GraphBoard;
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
import java.util.Queue;
import java.util.LinkedList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.Timer;

import org.javatuples.Pair;

import com.algobuddy.gui.GraphPanel;

/**
 *
 * @author [Your Name]
 */
public class Kahns extends GraphBoard {

    private int[] inDegree;
    private Queue<Node> zeroInDegreeNodes;
    private List<Node> topologicalOrder;
    private boolean[] processed;
    private AlgoWorker<Void, Void> kahnsWorker;
    private List<Pair<Node, Node>> processedEdges;
    private Pair<Node, Node> runningEdge;
    private Node runningNode;
    private boolean completed = false;
    private boolean hasCycle = false;
    private int coefficient = 6;
    private Timer animationTimer;
    private double progress;
    private boolean l1, l2, l3, l4, l5, l6;

    @Override
    protected void paintComponent(Graphics g) {
        setPaintingBoundary(getSize());
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
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
            e.draw(g2d, true);
        }
        for (Node n : nodes) {
            n.draw(g2d, i);
            i++;
        }

        for (i = coefficient; i < coefficient + 26; i++) {
            g2d.setColor(new Color(161, 131, 199));
            g2d.setFont(new Font("Consolas", Font.BOLD, 18));
            g2d.drawString(String.valueOf((char) (i + 59)), i * ((getWidth() - 700) / 26) + getWidth() / 150, getHeight() - 96);

            g2d.setStroke(new BasicStroke(2));

            if ((i & 1) > 0) {
                g2d.setColor(new Color(154, 192, 230));
            } else {
                g2d.setColor(new Color(151, 188, 226));
            }
            g2d.fillRect((i * ((getWidth() - 700) / 26) + 2), getHeight() - 83, (getWidth() - 760) / 26, 27);
            g2d.fillRect((i * ((getWidth() - 700) / 26) + 2), getHeight() - 38, (getWidth() - 760) / 26, 27);

            if (!isPlaying()) {
                g2d.setColor(new Color(0, 22, 40));
                g2d.setFont(new Font("Consolas", Font.BOLD, 18));
                if (i - coefficient < nodes.size()) {
                    g2d.drawString("0", i * ((getWidth() - 700) / 26) + getWidth() / 150, getHeight() - 64);
                } else {
                    g2d.drawString("-", i * ((getWidth() - 700) / 26) + getWidth() / 150, getHeight() - 64);
                }
                g2d.drawString("-", i * ((getWidth() - 700) / 26) + getWidth() / 150, getHeight() - 19);
            }
        }

        g2d.setColor(new Color(161, 131, 199));
        g2d.setFont(new Font("Consolas", Font.ITALIC, 18));
        g2d.drawString("In-Degree:", ((getWidth() - 700) / 26) + getWidth() / 150, getHeight() - 64);
        g2d.drawString("Queue:", ((getWidth() - 700) / 26) + getWidth() / 150, getHeight() - 19);

        if (isSelecting()) {
            g2d.setColor(new Color(67, 102, 178, 60));
            Rectangle mouseRect = getMouseRect();
            g2d.fillRect(mouseRect.x, mouseRect.y,
                    mouseRect.width, mouseRect.height);
        }

        if (isPlaying()) {
            for (i = coefficient; i < coefficient + 27; i++) {
                if (i - coefficient < nodes.size()) {
                    g2d.setColor(new Color(0, 22, 40));
                    g2d.setFont(new Font("Consolas", Font.BOLD, 18));
                    g2d.drawString(String.valueOf(inDegree[i - coefficient]),
                            i * ((getWidth() - 700) / 26) + getWidth() / 150, getHeight() - 64);
                } else {
                    g2d.setColor(new Color(0, 22, 40));
                    g2d.setFont(new Font("Consolas", Font.BOLD, 18));
                    g2d.drawString("-", i * ((getWidth() - 700) / 26) + getWidth() / 150, getHeight() - 64);
                }
            }

            g2d.setColor(Color.RED);
            g2d.setFont(new Font("Consolas", Font.BOLD, 18));
            i = coefficient;
            for (Node n : zeroInDegreeNodes) {
                g2d.drawString(String.valueOf((char) (n.getNodeNum() + 65)),
                        i * ((getWidth() - 700) / 26) + getWidth() / 150, getHeight() - 19);
                i++;
            }
            g2d.setColor(new Color(0, 22, 40));
            while (i < coefficient + 26) {
                g2d.drawString("-", i * ((getWidth() - 700) / 26) + getWidth() / 150, getHeight() - 19);
                i++;
            }

            for (Pair p : processedEdges) {
                Node n1 = (Node) p.getValue0();
                Node n2 = (Node) p.getValue1();

                if (p.equals(runningEdge)) {
                    new DrawArrow(g2d, n1.getLocation(), n2.getLocation(), progress, new Color(218, 226, 237),
                            new BasicStroke((float) 4), new BasicStroke(), 25);
                } else {
                    new DrawArrow(g2d, n1.getLocation(), n2.getLocation(), 1, new Color(218, 226, 237),
                            new BasicStroke((float) 4), new BasicStroke(), 25);
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

            if (!completed && runningNode != null) {
                g2d.setStroke(new BasicStroke(3));
                g2d.setColor(new Color(240, 10, 9));
                g2d.drawOval(runningNode.getLocation().x - Node.getRadius(), runningNode.getLocation().y - Node.getRadius(),
                        2 * Node.getRadius(), 2 * Node.getRadius());
                g2d.setColor(new Color(234, 123, 114));
                g2d.setFont(new Font("Consolas", Font.BOLD, 18));
                g2d.drawString(String.valueOf((char) (runningNode.getNodeNum() + 65)),
                        (coefficient - 1) * ((getWidth() - 700) / 26) + getWidth() / 150, getHeight() - 19);
            }

            // Display Topological Order
            if (topologicalOrder != null && !topologicalOrder.isEmpty()) {
                g2d.setColor(new Color(177, 191, 222));
                g2d.setFont(new Font("Consolas", Font.BOLD, 20));
                g2d.drawString("Topological Order:", getWidth() - 315, getHeight() - 650);

                StringBuilder orderStr = new StringBuilder();
                for (Node n : topologicalOrder) {
                    orderStr.append((char) (n.getNodeNum() + 65)).append(" → ");
                }
                if (orderStr.length() > 3) {
                    orderStr.setLength(orderStr.length() - 3); // Remove last arrow
                }

                // Word wrap for long topological orders
                String order = orderStr.toString();
                int maxWidth = 200;
                List<String> lines = new ArrayList<>();
                String[] words = order.split(" → ");
                StringBuilder currentLine = new StringBuilder();

                for (String word : words) {
                    if (currentLine.length() + word.length() + 3 <= maxWidth / 8) {
                        if (currentLine.length() > 0) {
                            currentLine.append(" → ");
                        }
                        currentLine.append(word);
                    } else {
                        lines.add(currentLine.toString());
                        currentLine = new StringBuilder(word);
                    }
                }

                if (currentLine.length() > 0) {
                    lines.add(currentLine.toString());
                }

                g2d.setColor(new Color(238, 247, 137));
                for (int j = 0; j < lines.size(); j++) {
                    g2d.drawString(lines.get(j), getWidth() - 315, getHeight() - 620 + j * 25);
                }
            }
        }

        // Algorithm pseudo-code display
        g2d.setColor(new Color(177, 191, 222));
        g2d.setFont(new Font("Consolas", Font.BOLD, 30));
        g2d.drawString("Algorithm", getWidth() - 240, getHeight() - 1000);

        g2d.setColor(new Color(210, 52, 52));
        g2d.setFont(new Font("Consolas", Font.ITALIC, 15));
        if (l1) {
            g2d.drawString("1. Compute in-degree for each vertex", getWidth() - 315, getHeight() - 950);
            g2d.drawString("and Create a queue of vertices with", getWidth() - 315, getHeight() - 930);
            g2d.drawString("in-degree 0", getWidth() - 315, getHeight() - 910);
        }
        if (l2) {
            g2d.drawString("2. While queue is not empty", getWidth() - 315, getHeight() - 880);
        }
        if (l3) {
            g2d.setColor(new Color(238, 247, 137));
            g2d.drawString("    i. Dequeue a vertex u", getWidth() - 315, getHeight() - 850);
            g2d.drawString("    and Add u to topological order", getWidth() - 315, getHeight() - 830);
            g2d.setColor(new Color(210, 52, 52));
        }
        if (l4) {
            g2d.setColor(new Color(238, 247, 137));
            g2d.drawString("    ii. For each neighbor v of u", getWidth() - 315, getHeight() - 800);
            g2d.setColor(new Color(210, 52, 52));
        }
        if (l5) {
            g2d.setColor(new Color(120, 255, 120));
            g2d.drawString("         a. Reduce in-degree of v by 1", getWidth() - 315, getHeight() - 770);
            g2d.setColor(new Color(210, 52, 52));
        }
        if (l6) {
            g2d.setColor(new Color(120, 255, 120));
            g2d.drawString("         b. If in-degree of v becomes 0", getWidth() - 315, getHeight() - 740);
            g2d.drawString("              add v to queue", getWidth() - 315, getHeight() - 720);
            g2d.setColor(new Color(210, 52, 52));
        }

        g2d.setColor(new Color(161, 131, 199));
        if (!l1) {
            g2d.drawString("1. Compute in-degree for each vertex", getWidth() - 315, getHeight() - 950);
            g2d.drawString("and Create a queue of vertices with", getWidth() - 315, getHeight() - 930);
            g2d.drawString("in-degree 0", getWidth() - 315, getHeight() - 910);
        }
        if (!l2) {
            g2d.drawString("4. While queue is not empty", getWidth() - 315, getHeight() - 880);
        }
        if (!l3) {
            g2d.drawString("    i. Dequeue a vertex u", getWidth() - 315, getHeight() - 850);
            g2d.drawString("    and Add u to topological order", getWidth() - 315, getHeight() - 830);
        }
        if (!l4) {
            g2d.drawString("    ii. For each neighbor v of u", getWidth() - 315, getHeight() - 800);
        }
        if (!l5) {
            g2d.drawString("         a. Reduce in-degree of v by 1", getWidth() - 315, getHeight() - 770);
        }
        if (!l6) {
            g2d.drawString("         b. If in-degree of v becomes 0", getWidth() - 315, getHeight() - 740);
            g2d.drawString("              add v to queue", getWidth() - 315, getHeight() - 720);
        }

        // If we have a cycle, display a warning
        if (hasCycle && completed && isPlaying()) {
            g2d.setColor(new Color(255, 50, 50));
            g2d.setFont(new Font("Consolas", Font.BOLD, 18));
            g2d.drawString("Graph contains a cycle!", getWidth() - 315, getHeight() - 200);
            g2d.drawString("Topological sort not possible.", getWidth() - 315, getHeight() - 175);
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

        completed = false;
        hasCycle = false;

        inDegree = new int[nodes.size()];
        zeroInDegreeNodes = new LinkedList<>();
        topologicalOrder = new ArrayList<>();
        processed = new boolean[nodes.size()];
        processedEdges = new ArrayList<>();
        runningNode = null;
        runningEdge = null;

        for (Edge e : edges) {
            inDegree[e.getNode2().getNodeNum()]++;
        }

        kahnsWorker = new AlgoWorker<>() {
            @Override
            public Void doInBackground() throws InterruptedException {
                try {

                    for (int i = 0; i < nodes.size(); i++) {
                        if (inDegree[i] == 0) {
                            zeroInDegreeNodes.add(nodes.get(i));
                        }
                    }

                    l1 = true;
                    l2 = l3 = l4 = l5 = l6 = false;
                    repaint();
                    waitFor(getSpeed());

                    int visitedCount = 0;

                    while (!zeroInDegreeNodes.isEmpty() && !isCancelled()) {
                        if (!isPaused()) {
                            Node u = zeroInDegreeNodes.poll();
                            runningNode = u;

                            topologicalOrder.add(u);
                            visitedCount++;
                            processed[u.getNodeNum()] = true;

                            l2 = l3 = true;
                            l1 = l4 = l5 = l6 = false;
                            repaint();
                            waitFor(getSpeed());

                            for (Edge e : edges) {
                                if (e.getNode1().equals(u) && !isCancelled()) {
                                    if (!isPaused()) {
                                        Node v = e.getNode2();

                                        runningEdge = new Pair<>(u, v);
                                        processedEdges.add(runningEdge);

                                        l4 = l5 = true;
                                        l1 = l3 = l6 = false;
                                        startAnimationTimer();
                                        waitFor(getSpeed());
                                        stopAnimationTimer();

                                        inDegree[v.getNodeNum()]--;
                                        repaint();
                                        waitFor(getSpeed() / 2);

                                        if (inDegree[v.getNodeNum()] == 0) {
                                            zeroInDegreeNodes.add(v);

                                            l6 = true;
                                            l1 = l3 = l5 = false;
                                            repaint();
                                            waitFor(getSpeed());
                                        }
                                    } else {
                                        waitFor(200);
                                    }
                                }
                            }
                            l1 = l3 = l4 = l5 = l6 = false;
                        } else {
                            waitFor(200);
                        }
                    }

                    if (visitedCount != nodes.size()) {
                        if (isCancelled()) {
                            return null;
                        }
                        hasCycle = true;
                        JOptionPane.showMessageDialog(null,
                                "Graph contains a cycle!\nTopological sort not possible.",
                                "Cycle Detected",
                                JOptionPane.WARNING_MESSAGE);
                    }

                    return null;
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
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
        kahnsWorker.execute();
    }

    public AlgoWorker<Void, Void> getKahnsWorker() {
        return kahnsWorker;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void resetCode() {
        l1 = l2 = l3 = l4 = l5 = l6 = false;
    }
}
