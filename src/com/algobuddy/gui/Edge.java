package com.algobuddy.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author nebir, nazrul
 */
public class Edge {

    private Node n1;
    private Node n2;
    private int w = 1;
    private static int X, Y;
    private Rectangle boundary = new Rectangle();

    public Edge(Node n1, Node n2) {
        this.n1 = n1;
        this.n2 = n2;
        X = (n1.getLocation().x + n2.getLocation().x) / 2;
        Y = (n1.getLocation().y + n2.getLocation().y) / 2;
        boundary.setBounds(X - 8, Y - 9, 16, 18);
    }

    /**
     * Draws edges
     *
     * @param g2d
     * @param directed
     */
    public void draw(Graphics2D g2d, boolean directed) {
        Point p1 = n1.getLocation();
        Point p2 = n2.getLocation();
        g2d.setColor(new Color(47, 182, 171));
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setStroke(new BasicStroke((float) 2.5));
        if (directed) {
            new DrawArrow(g2d, p1, p2, 1, new Color(47, 182, 171), new BasicStroke((float) 2.5), new BasicStroke(), 25);
        } else {
            g2d.drawLine(p1.x, p1.y, p2.x, p2.y);
        }
    }

    /**
     * @return the first node
     */
    public Node getNode1() {
        return n1;
    }

    /**
     * @return the second node
     */
    public Node getNode2() {
        return n2;
    }

    /**
     * @return the weight
     */
    public int getWeight() {
        return w;
    }

    /**
     * sets the weight of the edge
     */
    static void setWeight(List<Edge> list, Point p) {
        for (Edge e : list) {
            if (e.boundary.contains(p)) {
                int w = -1;
                try {
                    w = Integer.parseInt(JOptionPane.showInputDialog(null,
                            "Insert a positive integer between 1 to 80000000",
                            "Input Dialog",
                            JOptionPane.PLAIN_MESSAGE));
                    if (w < 0 || w > 80000000) {
                        throw new Exception();
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null,
                            "Invalid input.",
                            "Error!",
                            JOptionPane.ERROR_MESSAGE);
                }
                if (w >= 0 && w <= 80000000) {
                    e.w = w;
                    e.updateBounds();
                }
                break;
            }
        }
    }

    /**
     * updates the bounds of the weight box
     */
    private void updateBounds() {
        X = (n1.getLocation().x + n2.getLocation().x) / 2;
        Y = (n1.getLocation().y + n2.getLocation().y) / 2;
        String str = String.valueOf(w);
        int N = str.length();
        boundary.setBounds(X - N * 5 - 3, Y - 9, N * 10 + 6, 18);
    }

    /**
     * updates All the bounds of weight boxes related to node n
     *
     */
    static void updateAllBounds(List<Edge> list, Node n) {
        for (Edge e : list) {
            if (e.getNode1() == n || e.getNode2() == n) {
                e.updateBounds();
            }
        }
    }

}
