package com.algobuddy.graphalgos;

import com.algobuddy.gui.AlgoWorker;
import com.algobuddy.gui.DrawArrow;
import com.algobuddy.gui.DrawLine;
import com.algobuddy.gui.Edge;
import com.algobuddy.gui.GraphBoard;
import com.algobuddy.gui.GraphPanel;
import com.algobuddy.gui.Node;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Queue;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import org.javatuples.Pair;

/**
 *
 * @author nebir, nazrul
 */
public class BFS extends GraphBoard {

    private Graph g;
    private Queue<Node> q;
    private boolean[] vis;
    private static int[] dis;
    private boolean l1, l2, l3, l4, l5;
    private AlgoWorker<Void, Void> bfsWorker;
    private List<Pair<Node, Node>> processedEdges;
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
            e.draw(g2d, isDirected());
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
                if (getSource() != null && i != getSource().getNodeNum() + coefficient) {
                    g2d.setColor(new Color(0, 22, 40));
                    g2d.setFont(new Font("Consolas", Font.BOLD, 18));
                    if (i - coefficient < nodes.size()) {
                        g2d.drawString("0", i * ((getWidth() - 700) / 26) + getWidth() / 150, getHeight() - 64);
                    } else {
                        g2d.drawString("-", i * ((getWidth() - 700) / 26) + getWidth() / 150, getHeight() - 64);
                    }
                } else if (getSource() == null) {
                    g2d.setColor(new Color(0, 22, 40));
                    g2d.setFont(new Font("Consolas", Font.BOLD, 18));
                    if (i - coefficient < nodes.size()) {
                        g2d.drawString("0", i * ((getWidth() - 700) / 26) + getWidth() / 150, getHeight() - 64);
                    } else {
                        g2d.drawString("-", i * ((getWidth() - 700) / 26) + getWidth() / 150, getHeight() - 64);
                    }

                    g2d.drawString("-", i * ((getWidth() - 700) / 26) + getWidth() / 150, getHeight() - 19);
                }

                if (getSource() != null && i != coefficient) {
                    g2d.setColor(new Color(0, 22, 40));
                    g2d.setFont(new Font("Consolas", Font.BOLD, 18));
                    g2d.drawString("-", i * ((getWidth() - 700) / 26) + getWidth() / 150, getHeight() - 19);
                }
            }
        }

        g2d.setColor(new Color(161, 131, 199));
        g2d.setFont(new Font("Consolas", Font.ITALIC, 18));
        g2d.drawString("Visited:", ((getWidth() - 700) / 26) + getWidth() / 150, getHeight() - 64);
        g2d.drawString("Queue:", ((getWidth() - 700) / 26) + getWidth() / 150, getHeight() - 19);

        if (getSource() != null) {
            l1 = false;
            g2d.setColor(new Color(245, 204, 158));
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
                        coefficient * ((getWidth() - 700) / 26) + getWidth() / 150, getHeight() - 19);

                g2d.setColor(Color.RED);
                g2d.setFont(new Font("Consolas", Font.BOLD, 18));
                g2d.drawString("1", (getSource().getNodeNum() + coefficient) * ((getWidth() - 700) / 26) + getWidth() / 150, getHeight() - 64);
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
            for (i = coefficient; i < coefficient + 27; i++) {
                if (i - coefficient < nodes.size()) {
                    if (vis[i - coefficient]) {
                        g2d.setColor(Color.RED);
                        g2d.setFont(new Font("Consolas", Font.BOLD, 18));
                        g2d.drawString("1", i * ((getWidth() - 700) / 26) + getWidth() / 150, getHeight() - 64);
                    } else {
                        g2d.setColor(new Color(0, 22, 40));
                        g2d.setFont(new Font("Consolas", Font.BOLD, 18));
                        g2d.drawString("0", i * ((getWidth() - 700) / 26) + getWidth() / 150, getHeight() - 64);
                    }
                } else {
                    g2d.setColor(new Color(0, 22, 40));
                    g2d.setFont(new Font("Consolas", Font.BOLD, 18));
                    g2d.drawString("-", i * ((getWidth() - 700) / 26) + getWidth() / 150, getHeight() - 64);
                }
            }

            g2d.setColor(Color.RED);
            g2d.setFont(new Font("Consolas", Font.BOLD, 18));
            i = coefficient;
            for (Node n : q) {
                g2d.drawString(String.valueOf((char) (n.getNodeNum() + 65)), i * ((getWidth() - 700) / 26) + getWidth() / 150, getHeight() - 19);
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
                        (coefficient - 1) * ((getWidth() - 700) / 26) + getWidth() / 150, getHeight() - 19);
            }

            // Display BFS Distances Level-wise
            if (dis != null && dis.length > 0) {
                g2d.setColor(new Color(177, 191, 222));
                g2d.setFont(new Font("Consolas", Font.BOLD, 20));
                g2d.drawString("BFS Levels:", getWidth() - 315, getHeight() - 700);

                // Group nodes by level
                Map<Integer, List<Character>> levelMap = new HashMap<>();
                int maxLevel = 0;

                for (i = 0; i < dis.length; i++) {
                    if (dis[i] != Integer.MAX_VALUE) {
                        char nodeName = (char) (i + 65);
                        int level = dis[i];
                        maxLevel = Math.max(maxLevel, level);

                        if (!levelMap.containsKey(level)) {
                            levelMap.put(level, new ArrayList<>());
                        }
                        levelMap.get(level).add(nodeName);
                    }
                }

                // Calculate appropriate font size based on number of levels
                int availableHeight = 650; // Approximate available height
                int lineHeight = availableHeight / Math.min(26, maxLevel + 5); // Add buffer
                int fontSize = Math.min(20, Math.max(10, lineHeight - 5)); // Adjust font size

                g2d.setFont(new Font("Consolas", Font.BOLD, fontSize));
                g2d.setColor(new Color(238, 247, 137));

                int verticalPosition = getHeight() - 670;
                int maxWidth = 300; // Available width

                // Display all levels from 0 to maxLevel
                for (int level = 0; level <= Math.min(25, maxLevel); level++) {
                    StringBuilder levelStr = new StringBuilder("Level " + level + ": ");
                    List<Character> nodesAtLevel = levelMap.getOrDefault(level, new ArrayList<>());

                    if (nodesAtLevel.isEmpty()) {
                        levelStr.append("(none)");
                    } else {
                        for (Character node : nodesAtLevel) {
                            levelStr.append(node);
                            if (nodesAtLevel.indexOf(node) < nodesAtLevel.size() - 1) {
                                levelStr.append(", ");
                            }
                        }
                    }

                    // Word wrap for long levels
                    String levelText = levelStr.toString();
                    FontMetrics metrics = g2d.getFontMetrics();

                    if (metrics.stringWidth(levelText) > maxWidth) {
                        // Split into multiple lines if too long
                        List<String> wrappedLines = new ArrayList<>();
                        String prefix = "Level " + level + ": ";
                        StringBuilder currentLine = new StringBuilder(prefix);

                        for (Character node : nodesAtLevel) {
                            String nodeStr = node.toString();
                            if (nodesAtLevel.indexOf(node) < nodesAtLevel.size() - 1) {
                                nodeStr += ", ";
                            }

                            if (metrics.stringWidth(currentLine.toString() + nodeStr) <= maxWidth) {
                                currentLine.append(nodeStr);
                            } else {
                                wrappedLines.add(currentLine.toString());
                                currentLine = new StringBuilder("  " + nodeStr); // Indent continuation lines
                            }
                        }

                        if (currentLine.length() > 0) {
                            wrappedLines.add(currentLine.toString());
                        }

                        for (String line : wrappedLines) {
                            g2d.drawString(line, getWidth() - 315, verticalPosition);
                            verticalPosition += fontSize + 2;
                        }
                    } else {
                        g2d.drawString(levelText, getWidth() - 315, verticalPosition);
                        verticalPosition += fontSize + 2;
                    }
                }

                // If there are unreachable nodes, list them               
                List<Character> unreachableNodes = new ArrayList<>();

                if (completed) {
                    for (i = 0; i < dis.length; i++) {
                        if (dis[i] == Integer.MAX_VALUE) {
                            unreachableNodes.add((char) (i + 65));
                        }
                    }
                }

                if (!unreachableNodes.isEmpty()) {
                    StringBuilder unreachableStr = new StringBuilder("Unreachable: ");
                    for (i = 0; i < unreachableNodes.size(); i++) {
                        unreachableStr.append(unreachableNodes.get(i));
                        if (i < unreachableNodes.size() - 1) {
                            unreachableStr.append(", ");
                        }
                    }

                    // Check if we need to word wrap
                    String unreachableText = unreachableStr.toString();
                    FontMetrics metrics = g2d.getFontMetrics();

                    if (metrics.stringWidth(unreachableText) > maxWidth) {
                        String prefix = "Unreachable: ";
                        g2d.drawString(prefix, getWidth() - 315, verticalPosition);
                        verticalPosition += fontSize + 2;

                        StringBuilder currentLine = new StringBuilder("  ");
                        for (Character node : unreachableNodes) {
                            String nodeStr = node.toString();
                            if (unreachableNodes.indexOf(node) < unreachableNodes.size() - 1) {
                                nodeStr += ", ";
                            }

                            if (metrics.stringWidth(currentLine.toString() + nodeStr) <= maxWidth) {
                                currentLine.append(nodeStr);
                            } else {
                                g2d.drawString(currentLine.toString(), getWidth() - 315, verticalPosition);
                                verticalPosition += fontSize + 2;
                                currentLine = new StringBuilder("  " + nodeStr);
                            }
                        }

                        if (currentLine.length() > 0) {
                            g2d.drawString(currentLine.toString(), getWidth() - 315, verticalPosition);
                        }
                    } else {
                        g2d.drawString(unreachableText, getWidth() - 315, verticalPosition);
                    }
                }
            }
        }

        for (Node n : nodes) {
            g2d.setFont(new Font("Casteller", Font.BOLD, 18));
            g2d.setColor(new Color(161, 56, 64));

            if (getSource() == n) {
                g2d.drawString("0", n.getLocation().x - 5, n.getLocation().y - 27);
            } else if (isPlaying() && !"∞".equals(getDistance(n))) {
                String str = getDistance(n);
                int N = str.length();
                g2d.drawString(str, n.getLocation().x - N * 5, n.getLocation().y - 27);
            }
        }

        g2d.setColor(new Color(177, 191, 222));
        g2d.setFont(new Font("Consolas", Font.BOLD, 30));
        g2d.drawString("Algorithm", getWidth() - 240, getHeight() - 1000);
        g2d.setColor(new Color(210, 52, 52));
        g2d.setFont(new Font("Consolas", Font.ITALIC, 15));
        if (l1) {
            g2d.drawString("1. Create a queue Q", getWidth() - 315, getHeight() - 950);
        }
        if (l2) {
            g2d.drawString("2. Mark the source as visited,", getWidth() - 315, getHeight() - 920);
            g2d.drawString("put it into Q and set dist[source] ← 0", getWidth() - 318, getHeight() - 900);
        }
        if (l3) {
            g2d.drawString("3. While Q is non- empty", getWidth() - 315, getHeight() - 870);
        }
        if (l4) {
            g2d.setColor(new Color(238, 247, 137));
            g2d.drawString("      i.  Remove the head u of Q", getWidth() - 315, getHeight() - 840);
            g2d.setColor(new Color(210, 52, 52));
        }
        if (l5) {
            g2d.setColor(new Color(238, 247, 137));
            g2d.drawString("      ii. Mark and enqueue", getWidth() - 315, getHeight() - 810);
            g2d.drawString("      all (unvisited) neighbors v of u", getWidth() - 318, getHeight() - 790);
            g2d.drawString("      and set dist[v] ← dist[u] + 1", getWidth() - 318, getHeight() - 770);
            g2d.setColor(new Color(210, 52, 52));
        }

        g2d.setColor(new Color(161, 131, 199));
        if (!l1) {
            g2d.drawString("1. Create a queue Q", getWidth() - 315, getHeight() - 950);
        }
        if (!l2) {
            g2d.drawString("2. Mark the source as visited,", getWidth() - 315, getHeight() - 920);
            g2d.drawString("put it into Q and set dist[source] ← 0", getWidth() - 318, getHeight() - 900);
        }
        if (!l3) {
            g2d.drawString("3. While Q is non- empty", getWidth() - 315, getHeight() - 870);
        }
        if (!l4) {
            g2d.drawString("      i.  Remove the head u of Q", getWidth() - 315, getHeight() - 840);
        }
        if (!l5) {
            g2d.drawString("      ii. Mark and enqueue", getWidth() - 315, getHeight() - 810);
            g2d.drawString("      all (unvisited) neighbors v of u", getWidth() - 315, getHeight() - 790);
            g2d.drawString("      and set dist[v] ← dist[u] + 1", getWidth() - 318, getHeight() - 770);
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
        vis = new boolean[nodes.size()];
        dis = new int[nodes.size()];
        Arrays.fill(dis, Integer.MAX_VALUE);
        q = new LinkedList<>();
        processedEdges = new ArrayList<>();
        for (Edge e : edges) {
            g.addEdge(e.getNode1(), e.getNode2());
            if (!isDirected()) {
                g.addEdge(e.getNode2(), e.getNode1());
            }
        }

        vis[getSource().getNodeNum()] = true;
        dis[getSource().getNodeNum()] = 0;
        q.add(getSource());

        bfsWorker = new AlgoWorker<>() {
            @Override
            public Void doInBackground() throws InterruptedException {
                while (!q.isEmpty() && !isCancelled()) {
                    if (!isPaused()) {
                        Node u = q.poll();
                        runningNode = u;
                        l4 = l3 = true;
                        l1 = l2 = l5 = false;
                        repaint();
                        waitFor(getSpeed());
                        var it = g.getAdj()[u.getNodeNum()].listIterator();
                        while (it.hasNext() && !isCancelled()) {
                            if (!isPaused()) {
                                Node v = it.next();
                                if (!vis[v.getNodeNum()]) {
                                    runningEdge = new Pair<>(u, v);
                                    processedEdges.add(runningEdge);
                                    l5 = true;
                                    l1 = l2 = l4 = false;
                                    startAnimationTimer();
                                    waitFor(getSpeed());
                                    stopAnimationTimer();
                                    vis[v.getNodeNum()] = true;
                                    dis[v.getNodeNum()] = dis[u.getNodeNum()] + 1;
                                    q.add(v);
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
                if (!isCancelled()) {
                    GraphPanel.getPlayLabel().setIcon(new ImageIcon("src" + File.separator + "com" + File.separator + "algobuddy" + File.separator + "gui" + File.separator + "img" + File.separator + "playDisabled.png"));
                }
                repaint();
            }
        };
        bfsWorker.execute();
    }

    public AlgoWorker<Void, Void> getBFSWorker() {
        return bfsWorker;
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
