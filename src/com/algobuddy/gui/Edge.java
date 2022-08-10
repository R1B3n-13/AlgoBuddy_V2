package com.algobuddy.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;

/**
 *
 * @author nebir, nazrul
 */
public class Edge {

    private Node n1;
    private Node n2;
    private int w = 1;

    public Edge(Node n1, Node n2) {
        this.n1 = n1;
        this.n2 = n2;
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
            new DrawArrow(g2d, p1, p2, new Color(47, 182, 171), new BasicStroke((float) 2.5), new BasicStroke(), 25);
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
     * @param w 
     */
    void setWeight(int w) {
        this.w = w;
    }
}
