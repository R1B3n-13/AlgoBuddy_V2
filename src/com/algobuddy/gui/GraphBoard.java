package com.algobuddy.gui;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JOptionPane;
import org.javatuples.Pair;

/**
 *
 * @author nebir, nazrul
 */
public class GraphBoard extends javax.swing.JPanel {

    protected static List<Node> nodes = new ArrayList<>();
    protected static List<Edge> edges = new ArrayList<>();

    private static Point mousePt = new Point();
    private static final int MAX = 26;
    private static boolean addingEdges = false;
    private static boolean addingNodes = true;
    private static boolean directed = false;
    private static boolean playing = false;;
    private static int speed = 1650;
    private static Dimension paintingBoundary;
    private static String currentAlgo = "";
    private static Node source = null;

    private Rectangle mouseRect = new Rectangle();
    private boolean selecting = false;

    /**
     * Creates new form GraphBoard
     */
    public GraphBoard() {
        initComponents();
        this.addMouseListener(new MouseHandler());
        this.addMouseMotionListener(new MouseMotionHandler()); // MouseMotionListener handles the events when the mouse is in motion
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private class MouseHandler extends MouseAdapter { // MouseAdapter implements both the MouseListener and MouseMotionListener

        @Override
        public void mouseReleased(MouseEvent e) {
            if (!playing) {
                selecting = false;
                mouseRect.setBounds(0, 0, 0, 0);
                e.getComponent().repaint(); // e is originating by clicking on the canvas so the getComponent().repaint() will repaint the canvas
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            mousePt = e.getPoint();
            if (!playing && mousePt.x > 24 && mousePt.x < paintingBoundary.width - 326
                    && mousePt.y > 24 && mousePt.y < paintingBoundary.height - 126) {
                if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
                    Node.toggleSource(nodes, mousePt);
                } else if (e.getButton() == MouseEvent.BUTTON3) {
                    if (addingEdges) {
                        if (Node.selectedNodes.size() >= 2) {
                            Node.selectNone(nodes);
                            Node.selectOne(nodes, mousePt);
                        } else {
                            Node.selectToggle(nodes, mousePt);
                            connectAction();
                        }
                    } else {
                        newNodeAction();
                    }
                } else {
                    if (e.isControlDown()) {
                        Node.selectToggle(nodes, mousePt);
                    } else if (Node.selectOne(nodes, mousePt)) {
                        selecting = false;
                    } else {
                        Node.selectNone(nodes);
                        selecting = true;
                    }
                }
                e.getComponent().repaint();
            }
        }
    }

    private class MouseMotionHandler extends MouseMotionAdapter { // MouseMotionAdapter implements only the MouseMotionListener; Could've used MouseAdapter instead

        Point delta = new Point();

        @Override
        public void mouseDragged(MouseEvent e) {
            if (!playing) {
                if (selecting) {
                    mouseRect.setBounds(
                            Math.min(mousePt.x, Math.max(e.getX(), 25)),
                            Math.min(mousePt.y, Math.max(e.getY(), 25)),
                            Math.min(mousePt.x, Math.max(e.getX(), 25))
                            + Math.abs(mousePt.x - Math.max(e.getX(), 25)) > paintingBoundary.width - 326
                            ? Math.abs(mousePt.x - paintingBoundary.width + 326)
                            : Math.abs(mousePt.x - Math.max(e.getX(), 25)),
                            Math.min(mousePt.y, Math.max(e.getY(), 25))
                            + Math.abs(mousePt.y - Math.max(e.getY(), 25)) > paintingBoundary.height - 126
                            ? Math.abs(mousePt.y - paintingBoundary.height + 126)
                            : Math.abs(mousePt.y - Math.max(e.getY(), 25)));
                    Node.selectBox(nodes, mouseRect);
                } else {
                    delta.setLocation(
                            e.getX() - mousePt.x,
                            e.getY() - mousePt.y);
                    Node.updatePosition(nodes, delta);
                    mousePt = e.getPoint();
                }
                e.getComponent().repaint();
            }
        }
    }

    /**
     * Connects the selected nodes
     */
    private void connectAction() {
        if (Node.selectedNodes.size() == 2 && !playing) {
            Iterator<Node> it = Node.selectedNodes.iterator();
            Node n1 = it.next();
            Node n2 = null;
            while (it.hasNext()) {
                n2 = it.next();
            }
            boolean flg = true;
            for (int i = 0; i < edges.size(); ++i) {
                Node node1 = edges.get(i).getNode1();
                Node node2 = edges.get(i).getNode2();
                if (directed) {
                    if (node1 == n1 && node2 == n2) {
                        flg = false;
                    }
                } else {
                    if ((node1 == n1 && node2 == n2) || (node1 == n2 && node2 == n1)) {
                        flg = false;
                    }
                }
            }
            if (flg) {
                edges.add(new Edge(n1, n2));
            }
            repaint();
        }
    }

    /**
     * Creates a new node
     */
    private void newNodeAction() {
        if (isAddingNodes() && !playing) {
            if (nodes.size() < getMaxSize()) {
                Node.selectNone(GraphBoard.nodes); // unselects every node when the 'New' button is pressed
                Point p = mousePt.getLocation();
                Node n = new Node(p);
                n.setSelected(true); // selects the newly created node
                nodes.add(n);
                repaint();
            } else {
                JOptionPane.showMessageDialog(null,
                        "Node limit reached!",
                        "WARNING!",
                        JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    /**
     * Sets the state of addingEdgesRadioButton
     */
    static void setAddingEdges(boolean b) {
        addingEdges = b;
    }

    /**
     * @return the state of addingEdgesRadioButton
     */
    static boolean isAddingEdges() {
        return addingEdges;
    }

    /**
     * Sets the state of addingNodesRadioButton
     */
    static void setAddingNodes(boolean b) {
        addingNodes = b;
    }

    /**
     * @return the state of addingNodesRadioButton
     */
    static boolean isAddingNodes() {
        return addingNodes;
    }

    /**
     * Sets the state of weightedStateRadioButton
     */
    static void setDirected(boolean b) {
        directed = b;
    }

    /**
     * @return the state of weightedStateRadioButton
     */
    public static boolean isDirected() {
        return directed;
    }

    /**
     * Sets the playing state of the algo
     *
     * @param b
     */
    public static void setPlayingState(boolean b) {
        playing = b;
    }

    /**
     * @return true if the play label has been set to play
     */
    public static boolean isPlaying() {
        return playing;
    }

    /**
     * Sets the current algorithm name
     */
    static void setCurrentAlgo(String s) {
        currentAlgo = s;
    }

    /**
     * @return the current algorithm name
     */
    static String getCurrentAlgo() {
        return currentAlgo;
    }

    /**
     * @return the highest numbers of node the panel can add
     */
    static int getMaxSize() {
        return MAX;
    }

    /**
     * @return the mouse clicked point
     */
    static Point getMousePt() {
        return mousePt;
    }

    /**
     * @return the selection area
     */
    protected Rectangle getMouseRect() {
        return mouseRect;
    }

    /**
     * @return true if selecting mode is on
     */
    protected boolean isSelecting() {
        return selecting;
    }

    /**
     * Sets the algo simulator speed
     */
    static void setSpeed(int v) {
        speed = v;
    }

    /**
     * @return the algo simulator speed
     */
    protected int getSpeed() {
        return speed;
    }

    /**
     * Pauses the simulation
     *
     * @param iMillis
     */
    protected void waitFor(int iMillis) {
        try {
            Thread.sleep(iMillis);
        } catch (InterruptedException ex) {
            System.err.println(ex);
        }
    }

    /**
     * Sets the painting boundary
     *
     * @param dim
     */
    protected void setPaintingBoundary(Dimension dim) {
        paintingBoundary = dim;
    }

    /**
     * @return the painting boundary
     */
    static Dimension getPaintingBoundary() {
        return paintingBoundary;
    }

    /**
     * Sets the source node
     */
    static void setSource(Node n) {
        source = n;
    }

    /**
     * @return the source node
     */
    protected static Node getSource() {
        return source;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
