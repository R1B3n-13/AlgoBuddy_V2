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
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import org.javatuples.Pair;

/**
 *
 * @author nebir, nazrul
 */
public class BFS extends GraphBoard {

    private Graph g;
    private Queue<Node> q;
    private boolean[] vis;
    private AlgoWorker<Void, Void> bfsWorker;
    private List<Pair<Node, Node>> processingNodes;
    private Node runningNode;
    private boolean completed = false;
    private int coefficient = 7;

    @Override
    protected void paintComponent(Graphics g) {
        setPaintingBoundary(getSize());
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        Color color1 = new Color(110, 110, 110);
        Color color2 = new Color(118, 118, 118);
        GradientPaint gp = new GradientPaint(0, 0, color1, getWidth(), getHeight(), color2);
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        g2d.setColor(Color.WHITE);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRect(24, 24, getWidth() - 300, getHeight() - 150);

        int i = 'A';

        for (Edge e : edges) {
            e.draw(g2d, isDirected());
        }
        for (Node n : nodes) {
            n.draw(g2d, i);
            i++;
        }

        for (i = coefficient; i < 33; i++) {
            g2d.setColor(new Color(230, 230, 230));
            g2d.setFont(new Font("Casteller", Font.BOLD, 18));
            g2d.drawString(String.valueOf((char) (i + 58)), i * ((getWidth() - 700) / 26) + getWidth() / 150 , getHeight() - 96);

            g2d.setColor(Color.DARK_GRAY);
            g2d.setStroke(new BasicStroke(2));
            g2d.drawRect(i * ((getWidth() - 700) / 26), getHeight() - 85, (getWidth() - 700) / 26, 30);

            g2d.drawRect(i * ((getWidth() - 700) / 26), getHeight() - 40, (getWidth() - 700) / 26, 30);

            if ((i & 1) > 0) {
                g2d.setColor(new Color(251, 234, 181));
            } else {
                g2d.setColor(new Color(249, 239, 194));
            }
            g2d.fillRect((i * ((getWidth() - 700) / 26) + 2), getHeight() - 83, (getWidth() - 760) / 26, 27);

            g2d.fillRect((i * ((getWidth() - 700) / 26) + 2), getHeight() - 38, (getWidth() - 760) / 26, 27);

            if (!isPlaying()) {
                if (getSource() != null && i != getSource().getNodeNum() + coefficient) {
                    g2d.setColor(Color.DARK_GRAY);
                    g2d.setFont(new Font("Modern No. 20", Font.BOLD, 18));
                    if (i - coefficient < nodes.size()) {
                        g2d.drawString("0", i * ((getWidth() - 700) / 26) + getWidth()/150, getHeight() - 64);
                    } else {
                        g2d.drawString("-", i * ((getWidth() - 700) / 26) + getWidth()/150, getHeight() - 64);
                    }
                } else if (getSource() == null) {
                    g2d.setColor(Color.DARK_GRAY);
                    g2d.setFont(new Font("Modern No. 20", Font.BOLD, 18));
                    if (i - coefficient < nodes.size()) {
                        g2d.drawString("0", i * ((getWidth() - 700) / 26) + getWidth()/150, getHeight() - 64);
                    } else {
                        g2d.drawString("-", i * ((getWidth() - 700) / 26) + getWidth()/150, getHeight() - 64);
                    }

                    g2d.drawString("-", i * ((getWidth() - 700) / 26) + getWidth()/150, getHeight() - 19);
                }

                if (getSource() != null && i != coefficient) {
                    g2d.setColor(Color.DARK_GRAY);
                    g2d.setFont(new Font("Modern No. 20", Font.BOLD, 18));
                    g2d.drawString("-", i * ((getWidth() - 700) / 26) + getWidth()/150, getHeight() - 19);
                }
            }
        }

        g2d.setColor(new Color(230, 230, 230));
        g2d.setFont(new Font("Casteller", Font.BOLD, 18));
        g2d.drawString("Visited:", 2 * ((getWidth() - 700) / 26) + getWidth()/150, getHeight() - 64);

        g2d.drawString("Queue:", 2 * ((getWidth() - 700) / 26) + getWidth()/150, getHeight() - 19);

        if (getSource() != null) {
            g2d.setColor(new Color(218, 226, 237));
            g2d.fillOval(getSource().getLocation().x - Node.getRadius(), getSource().getLocation().y - Node.getRadius(),
                    2 * Node.getRadius(), 2 * Node.getRadius());
            g2d.setColor(new Color(255, 0, 0));
            g2d.setFont(new Font("Casteller", Font.BOLD, 18));
            g2d.drawString(String.valueOf((char) (getSource().getNodeNum() + 65)), getSource().getLocation().x - 5,
                    getSource().getLocation().y + 5);
            if (!isPlaying()) {
                g2d.setColor(Color.RED);
                g2d.setFont(new Font("Modern No. 20", Font.BOLD, 18));
                g2d.drawString(String.valueOf((char) (getSource().getNodeNum() + 65)),
                        coefficient * ((getWidth() - 700) / 26) + getWidth()/150, getHeight() - 19);

                g2d.setColor(Color.RED);
                g2d.setFont(new Font("Modern No. 20", Font.BOLD, 18));
                g2d.drawString("1", (getSource().getNodeNum() + coefficient) * ((getWidth() - 700) / 26) + getWidth()/150, getHeight() - 64);
            }
        }

        if (isSelecting()) {
            g2d.setColor(new Color(39, 41, 93, 80));
            Rectangle mouseRect = getMouseRect();
            g2d.fillRect(mouseRect.x, mouseRect.y,
                    mouseRect.width, mouseRect.height);
        }

        if (isPlaying()) {
            for (i = coefficient; i < 33; i++) {
                if (i - coefficient < nodes.size()) {
                    if (vis[i - coefficient]) {
                        g2d.setColor(Color.RED);
                        g2d.setFont(new Font("Modern No. 20", Font.BOLD, 18));
                        g2d.drawString("1", i * ((getWidth() - 700) / 26) + getWidth()/150, getHeight() - 64);
                    } else {
                        g2d.setColor(Color.DARK_GRAY);
                        g2d.setFont(new Font("Modern No. 20", Font.BOLD, 18));
                        g2d.drawString("0", i * ((getWidth() - 700) / 26) + getWidth()/150, getHeight() - 64);
                    }
                } else {
                    g2d.setColor(Color.DARK_GRAY);
                    g2d.setFont(new Font("Modern No. 20", Font.BOLD, 18));
                    g2d.drawString("-", i * ((getWidth() - 700) / 26) + getWidth()/150, getHeight() - 64);
                }
            }

            g2d.setColor(Color.RED);
            g2d.setFont(new Font("Modern No. 20", Font.BOLD, 18));
            i = coefficient;
            for (Node n : q) {
                g2d.drawString(String.valueOf((char) (n.getNodeNum() + 65)), i * ((getWidth() - 700) / 26) + getWidth()/150, getHeight() - 19);
                i++;
            }
            g2d.setColor(Color.DARK_GRAY);
            while (i < 33) {
                g2d.drawString("-", i * ((getWidth() - 700) / 26) + getWidth()/150, getHeight() - 19);
                i++;
            }

            for (Pair p : processingNodes) {

                Node n1 = (Node) p.getValue0();
                Node n2 = (Node) p.getValue1();

                if (isDirected()) {
                    new DrawArrow(g2d, n1.getLocation(), n2.getLocation(), new Color(218, 226, 237),
                            new BasicStroke((float) 4), new BasicStroke(), 25);
                } else {
                    new DrawLine(g2d, n1.getLocation(), n2.getLocation(), new Color(218, 226, 237), new BasicStroke(4));
                }

                g2d.setColor(new Color(218, 226, 237));
                g2d.fillOval(n1.getLocation().x - Node.getRadius(), n1.getLocation().y - Node.getRadius(),
                        2 * Node.getRadius(), 2 * Node.getRadius());
                g2d.fillOval(n2.getLocation().x - Node.getRadius(), n2.getLocation().y - Node.getRadius(),
                        2 * Node.getRadius(), 2 * Node.getRadius());

                g2d.setColor(new Color(255, 0, 0));
                g2d.setFont(new Font("Casteller", Font.BOLD, 18));
                g2d.drawString(String.valueOf((char) (n1.getNodeNum() + 65)), n1.getLocation().x - 5,
                        n1.getLocation().y + 5);
                g2d.drawString(String.valueOf((char) (n2.getNodeNum() + 65)), n2.getLocation().x - 5,
                        n2.getLocation().y + 5);
            }
            if (!completed) {
                g2d.setStroke(new BasicStroke(3));
                g2d.setColor(new Color(150, 42, 68));
                g2d.drawOval(runningNode.getLocation().x - Node.getRadius(), runningNode.getLocation().y - Node.getRadius(),
                        2 * Node.getRadius(), 2 * Node.getRadius());
                g2d.setColor(new Color(251, 234, 181));
                g2d.setFont(new Font("Modern No. 20", Font.BOLD, 18));
                g2d.drawString(String.valueOf((char) (runningNode.getNodeNum() + 65)),
                        (coefficient - 1) * ((getWidth() - 700) / 26) + getWidth()/150, getHeight() - 19);
            }
        }
    }

    public void start() {
        if (getSource() == null) {
            JOptionPane.showMessageDialog(null,
                    "Please select a source",
                    "WARNING!",
                    JOptionPane.WARNING_MESSAGE);
            setPlayingState(false);
            GraphPanel.getPlayLabel().setIcon(new ImageIcon("src\\com\\algobuddy\\gui\\img\\playEnabled.png"));
            GraphPanel.getResetLabel().setIcon(new ImageIcon("src\\com\\algobuddy\\gui\\img\\resetDisabled.png"));
            repaint();
            return;
        }
        completed = false;
        g = new Graph(nodes.size());
        vis = new boolean[nodes.size()];
        q = new LinkedList<Node>();
        processingNodes = new ArrayList<>();
        for (Edge x : edges) {
            g.addEdge(x.getNode1(), x.getNode2());
            if (!isDirected()) {
                g.addEdge(x.getNode2(), x.getNode1());
            }
        }

        vis[getSource().getNodeNum()] = true;
        q.add(getSource());

        bfsWorker = new AlgoWorker<>() {
            @Override
            public Void doInBackground() throws InterruptedException {
                while (!q.isEmpty() && !isCancelled()) {
                    if (!isPaused()) {
                        Node u = q.poll();
                        runningNode = u;
                        repaint();
                        waitFor(getSpeed());
                        Iterator<Node> it = g.getAdj()[u.getNodeNum()].listIterator();
                        while (it.hasNext()) {
                            if (!isPaused()) {
                                Node v = it.next();
                                if (!vis[v.getNodeNum()]) {
                                    vis[v.getNodeNum()] = true;
                                    q.add(v);
                                    processingNodes.add(Pair.with(u, v));
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
                repaint();
                GraphPanel.getPlayLabel().setIcon(new ImageIcon("src\\com\\algobuddy\\gui\\img\\playDisabled.png"));
            }
        };
        bfsWorker.execute();
    }

    public AlgoWorker<Void, Void> getBfsWorker() {
        return bfsWorker;
    }

    public boolean isCompleted() {
        return completed;
    }
}