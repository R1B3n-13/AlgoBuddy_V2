package com.algobuddy.gui;

import com.algobuddy.graphalgos.Dijkstra;
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
    private static final int RADIUS = 23;
    private final Color nodeColor = new Color(108, 180, 64);
    private final Color fontColor = new Color(0, 22, 40);
    private boolean selected = false;
    private Rectangle boundary = new Rectangle();
    protected static LinkedHashSet<Node> selectedNodes = new LinkedHashSet<>();
    private int nodeNumber;

    public Node(Point p) {
        if (p.x > GraphBoard.getPaintingBoundary().width - 350) {
            p.x = GraphBoard.getPaintingBoundary().width - 350;
        }
        if (p.x < 26 + RADIUS) {
            p.x = 26 + RADIUS;
        }
        if (p.y > GraphBoard.getPaintingBoundary().height - 150) {
            p.y = GraphBoard.getPaintingBoundary().height - 150;
        }
        if (p.y < 26 + RADIUS) {
            p.y = 26 + RADIUS;
        }
        this.p = p;
        boundary.setBounds(p.x - RADIUS, p.y - RADIUS, 2 * RADIUS, 2 * RADIUS);
    }

    /**
     * Draws the node
     *
     * @param g2d
     * @param ch
     */
    public void draw(Graphics2D g2d, int ch) {
        nodeNumber = ch - 65;
        g2d.setColor(this.nodeColor);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.fillOval(boundary.x, boundary.y, boundary.width, boundary.height);
        g2d.setColor(this.fontColor);
        g2d.setFont(new Font("Casteller", Font.BOLD, 18));
        g2d.drawString(String.valueOf((char) ch), boundary.x + RADIUS - 5, boundary.y + RADIUS + 5);
        if (selected && !GraphBoard.isPlaying()) {
            g2d.setColor(new Color(145, 170, 199));
            g2d.setStroke(new BasicStroke());
            g2d.drawRect(boundary.x - 1, boundary.y - 1, boundary.width + 1, boundary.height + 1);
        }
    }

    /**
     * @return the node number
     */
    public int getNodeNum() {
        return nodeNumber;
    }

    /**
     * @return the location of the node
     */
    public Point getLocation() {
        return p;
    }

    /**
     * @return the RADIUS of the node
     */
    public static int getRadius() {
        return RADIUS;
    }

    /**
     * @return true if the node is selected.
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
                if (n.p.x > GraphBoard.getPaintingBoundary().width - 350) {
                    n.p.x = GraphBoard.getPaintingBoundary().width - 350;
                }
                if (n.p.x < 26 + RADIUS) {
                    n.p.x = 26 + RADIUS;
                }
                if (n.p.y > GraphBoard.getPaintingBoundary().height - 150) {
                    n.p.y = GraphBoard.getPaintingBoundary().height - 150;
                }
                if (n.p.y < 26 + RADIUS) {
                    n.p.y = 26 + RADIUS;
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
