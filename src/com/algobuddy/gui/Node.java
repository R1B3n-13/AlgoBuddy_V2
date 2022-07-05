package com.algobuddy.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.util.LinkedHashSet;
import java.util.List;

/**
 *
 * @author nebir, nazrul
 */
public class Node {

    private Point p;
    private static final int radius = 23;
    private final Color nodeColor = new Color(108, 180, 64);
    private final Color fontColor = new Color(0, 22, 40);
    private boolean selected = false;
    private Rectangle boundary = new Rectangle();
    protected static LinkedHashSet<Node> selectedNodes = new LinkedHashSet<Node>();
    private int nodeNumber;

    public Node(Point p) {
        if (p.x > GraphBoard.getPaintingBoundary().width - 300) {
            p.x = GraphBoard.getPaintingBoundary().width - 300;
        }
        if (p.x < 26 + radius) {
            p.x = 26 + radius;
        }
        if (p.y > GraphBoard.getPaintingBoundary().height - 150) {
            p.y = GraphBoard.getPaintingBoundary().height - 150;
        }
        if (p.y < 26 + radius) {
            p.y = 26 + radius;
        }
        this.p = p;
        boundary.setBounds(p.x - radius, p.y - radius, 2 * radius, 2 * radius);
    }

    /**
     * Draws the node
     */
    public void draw(Graphics2D g2d, int ch) {
        nodeNumber = ch - 65;
        g2d.setColor(this.nodeColor);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.fillOval(boundary.x, boundary.y, boundary.width, boundary.height);
        g2d.setColor(this.fontColor);
        g2d.setFont(new Font("Casteller", Font.BOLD, 18));
        g2d.drawString(String.valueOf((char) ch), boundary.x + radius - 5, boundary.y + radius + 5);
        if (selected && !GraphBoard.isPlaying()) {
            g2d.setColor(new Color(145, 170, 199));
            g2d.setStroke(new BasicStroke());
            g2d.drawRect(boundary.x - 1, boundary.y - 1, boundary.width + 1, boundary.height + 1);
        }
    }

    /**
     * Returns the node number
     */
    public int getNodeNum() {
        return nodeNumber;
    }

    /**
     * Returns the location of the node
     */
    public Point getLocation() {
        return p;
    }

    /**
     * Returns the radius of the node
     */
    public static int getRadius() {
        return radius;
    }

    /**
     * Returns true if the node is selected.
     */
    boolean isSelected() {
        return selected;
    }

    /**
     * Marks this node as selected.
     */
    void setSelected(boolean selected) {
        this.selected = selected;
        if (this.selected) {
            selectedNodes.add(this);
        } else {
            selectedNodes.remove(this);
        }
    }

    /**
     * Unselects all the nodes.
     */
    static void selectNone(List<Node> list) {
        for (Node n : list) {
            n.setSelected(false);
        }
    }

    /**
     * Selects a single node; Returns true if there is a click on a node.
     */
    static boolean selectOne(List<Node> list, Point p) {
        for (Node n : list) {
            if (n.boundary.contains(p)) {
                if (!n.isSelected()) {
                    Node.selectNone(list);
                    n.setSelected(true);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Selects each node inside selection box.
     */
    static void selectBox(List<Node> list, Rectangle r) {
        for (Node n : list) {
            n.setSelected(r.contains(n.p)); // sets true if r contains n's point 'p'
        }
    }

    /**
     * Toggle selected state of each node containing p.
     */
    static void selectToggle(List<Node> list, Point p) {
        for (Node n : list) {
            if (n.boundary.contains(p)) {
                n.setSelected(!n.isSelected());
            }
        }
    }

    /**
     * Update each node's position by d (delta).
     */
    static void updatePosition(List<Node> list, Point d) {
        for (Node n : list) {
            if (n.isSelected()) {
                n.p.x += d.x;
                n.p.y += d.y;
                if (n.p.x > GraphBoard.getPaintingBoundary().width - 300) {
                    n.p.x = GraphBoard.getPaintingBoundary().width - 300;
                }
                if (n.p.x < 26 + radius) {
                    n.p.x = 26 + radius;
                }
                if (n.p.y > GraphBoard.getPaintingBoundary().height - 150) {
                    n.p.y = GraphBoard.getPaintingBoundary().height - 150;
                }
                if (n.p.y < 26 + radius) {
                    n.p.y = 26 + radius;
                }
                Point p = n.getLocation();
                int r = Node.getRadius();
                n.boundary.setBounds(p.x - r, p.y - r, 2 * r, 2 * r);
            }
        }
    }

    /**
     * Toggle selects the source node.
     */
    static void toggleSource(List<Node> list, Point p) {
        boolean flg = true;
        for (Node n : list) {
            if (n.boundary.contains(p)) {
                GraphBoard.setSource(n);
                flg = false;
            }
        }
        if (flg) {
            GraphBoard.setSource(null);
        }
    }
}
