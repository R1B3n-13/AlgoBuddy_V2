package com.algobuddy.gui;

import com.algobuddy.graphalgos.BFS;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.ListIterator;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author nebir, nazrul
 */
public class GraphPanel extends javax.swing.JPanel {

    /**
     * Creates new form GraphPanel
     */
    public GraphPanel() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        graphWestPanel = new javax.swing.JPanel();
        addEdgesRadioButton = new javax.swing.JRadioButton();
        disconnectNodesButton = new javax.swing.JButton();
        deleteNodesButton = new javax.swing.JButton();
        clearAllButton = new javax.swing.JButton();
        playLabel = new javax.swing.JLabel();
        resetLabel = new javax.swing.JLabel();
        addNodesRadioButton = new javax.swing.JRadioButton();
        graphHidePanel2 = new javax.swing.JPanel();
        graphHidePanel1 = new javax.swing.JPanel();
        graphHidePanel3 = new javax.swing.JPanel();
        speedSlider = new javax.swing.JSlider();
        directedStateRadioButton = new javax.swing.JRadioButton();

        setBackground(new java.awt.Color(0, 204, 204));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setPreferredSize(new java.awt.Dimension(800, 600));
        setLayout(new java.awt.BorderLayout());

        graphWestPanel.setBackground(new java.awt.Color(210, 210, 210));
        graphWestPanel.setPreferredSize(new java.awt.Dimension(180, 612));
        graphWestPanel.setLayout(null);

        addEdgesRadioButton.setBackground(new java.awt.Color(204, 204, 255));
        addEdgesRadioButton.setFont(new java.awt.Font("Baskerville Old Face", 0, 14)); // NOI18N
        addEdgesRadioButton.setForeground(new java.awt.Color(0, 102, 102));
        addEdgesRadioButton.setText("ADD EDGES");
        addEdgesRadioButton.setBorder(null);
        addEdgesRadioButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        addEdgesRadioButton.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        addEdgesRadioButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/algobuddy/gui/img/switchOff.png"))); // NOI18N
        addEdgesRadioButton.setIconTextGap(8);
        addEdgesRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addEdgesRadioButtonActionPerformed(evt);
            }
        });
        graphWestPanel.add(addEdgesRadioButton);
        addEdgesRadioButton.setBounds(40, 30, 115, 24);

        disconnectNodesButton.setBackground(new java.awt.Color(0, 102, 102));
        disconnectNodesButton.setFont(new java.awt.Font("Baskerville Old Face", 0, 14)); // NOI18N
        disconnectNodesButton.setForeground(new java.awt.Color(255, 255, 153));
        disconnectNodesButton.setText("DISCONNECT");
        disconnectNodesButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                disconnectNodesButtonMouseClicked(evt);
            }
        });
        graphWestPanel.add(disconnectNodesButton);
        disconnectNodesButton.setBounds(40, 130, 123, 21);

        deleteNodesButton.setBackground(new java.awt.Color(0, 102, 102));
        deleteNodesButton.setFont(new java.awt.Font("Baskerville Old Face", 0, 14)); // NOI18N
        deleteNodesButton.setForeground(new java.awt.Color(255, 255, 153));
        deleteNodesButton.setText("DELETE");
        deleteNodesButton.setPreferredSize(new java.awt.Dimension(123, 21));
        deleteNodesButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                deleteNodesButtonMouseClicked(evt);
            }
        });
        graphWestPanel.add(deleteNodesButton);
        deleteNodesButton.setBounds(40, 170, 123, 21);

        clearAllButton.setBackground(new java.awt.Color(0, 102, 102));
        clearAllButton.setFont(new java.awt.Font("Baskerville Old Face", 0, 14)); // NOI18N
        clearAllButton.setForeground(new java.awt.Color(255, 255, 153));
        clearAllButton.setText("CLEAR");
        clearAllButton.setPreferredSize(new java.awt.Dimension(123, 21));
        clearAllButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                clearAllButtonMouseClicked(evt);
            }
        });
        graphWestPanel.add(clearAllButton);
        clearAllButton.setBounds(40, 210, 123, 21);

        playLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/algobuddy/gui/img/playEnabled.png"))); // NOI18N
        playLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        playLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                playLabelMouseClicked(evt);
            }
        });
        graphWestPanel.add(playLabel);
        playLabel.setBounds(70, 250, 24, 24);

        resetLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/algobuddy/gui/img/resetDisabled.png"))); // NOI18N
        resetLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        resetLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                resetLabelMouseClicked(evt);
            }
        });
        graphWestPanel.add(resetLabel);
        resetLabel.setBounds(110, 255, 16, 16);

        addNodesRadioButton.setBackground(new java.awt.Color(204, 204, 255));
        addNodesRadioButton.setFont(new java.awt.Font("Baskerville Old Face", 0, 14)); // NOI18N
        addNodesRadioButton.setForeground(new java.awt.Color(0, 102, 102));
        addNodesRadioButton.setText("ADD NODES");
        addNodesRadioButton.setBorder(null);
        addNodesRadioButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        addNodesRadioButton.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        addNodesRadioButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/algobuddy/gui/img/switchOn.png"))); // NOI18N
        addNodesRadioButton.setIconTextGap(6);
        addNodesRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addNodesRadioButtonActionPerformed(evt);
            }
        });
        graphWestPanel.add(addNodesRadioButton);
        addNodesRadioButton.setBounds(40, 60, 115, 24);

        graphHidePanel2.setBackground(new java.awt.Color(111, 111, 111));
        graphHidePanel2.setPreferredSize(new java.awt.Dimension(0, 800));

        javax.swing.GroupLayout graphHidePanel2Layout = new javax.swing.GroupLayout(graphHidePanel2);
        graphHidePanel2.setLayout(graphHidePanel2Layout);
        graphHidePanel2Layout.setHorizontalGroup(
            graphHidePanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        graphHidePanel2Layout.setVerticalGroup(
            graphHidePanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 720, Short.MAX_VALUE)
        );

        graphWestPanel.add(graphHidePanel2);
        graphHidePanel2.setBounds(0, 322, 180, 720);

        graphHidePanel1.setBackground(new java.awt.Color(110, 110, 110));
        graphHidePanel1.setPreferredSize(new java.awt.Dimension(0, 24));

        javax.swing.GroupLayout graphHidePanel1Layout = new javax.swing.GroupLayout(graphHidePanel1);
        graphHidePanel1.setLayout(graphHidePanel1Layout);
        graphHidePanel1Layout.setHorizontalGroup(
            graphHidePanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        graphHidePanel1Layout.setVerticalGroup(
            graphHidePanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 24, Short.MAX_VALUE)
        );

        graphWestPanel.add(graphHidePanel1);
        graphHidePanel1.setBounds(0, 0, 180, 24);

        graphHidePanel3.setBackground(new java.awt.Color(110, 110, 110));

        javax.swing.GroupLayout graphHidePanel3Layout = new javax.swing.GroupLayout(graphHidePanel3);
        graphHidePanel3.setLayout(graphHidePanel3Layout);
        graphHidePanel3Layout.setHorizontalGroup(
            graphHidePanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 24, Short.MAX_VALUE)
        );
        graphHidePanel3Layout.setVerticalGroup(
            graphHidePanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 330, Short.MAX_VALUE)
        );

        graphWestPanel.add(graphHidePanel3);
        graphHidePanel3.setBounds(0, 16, 24, 330);

        speedSlider.setMaximum(3000);
        speedSlider.setMinimum(300);
        speedSlider.setValue(1650);
        speedSlider.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        speedSlider.setInverted(true);
        speedSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                speedSliderStateChanged(evt);
            }
        });
        graphWestPanel.add(speedSlider);
        speedSlider.setBounds(40, 290, 120, 20);

        directedStateRadioButton.setBackground(new java.awt.Color(204, 204, 255));
        directedStateRadioButton.setFont(new java.awt.Font("Baskerville Old Face", 0, 14)); // NOI18N
        directedStateRadioButton.setForeground(new java.awt.Color(0, 102, 102));
        directedStateRadioButton.setText("DIRECTED");
        directedStateRadioButton.setBorder(null);
        directedStateRadioButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        directedStateRadioButton.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        directedStateRadioButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/algobuddy/gui/img/switchOff.png"))); // NOI18N
        directedStateRadioButton.setIconTextGap(18);
        directedStateRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                directedStateRadioButtonActionPerformed(evt);
            }
        });
        graphWestPanel.add(directedStateRadioButton);
        directedStateRadioButton.setBounds(40, 90, 115, 24);

        add(graphWestPanel, java.awt.BorderLayout.LINE_START);
    }// </editor-fold>//GEN-END:initComponents

    private void clearAllButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clearAllButtonMouseClicked
        if (evt.getButton() == MouseEvent.BUTTON1)
            if (!GraphBoard.isPlaying()) {
                clearAllAction();
            }
    }//GEN-LAST:event_clearAllButtonMouseClicked

    private void deleteNodesButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deleteNodesButtonMouseClicked
        if (evt.getButton() == MouseEvent.BUTTON1)
            if (!GraphBoard.isPlaying()) {
                deleteAction();
            }
    }//GEN-LAST:event_deleteNodesButtonMouseClicked

    private void disconnectNodesButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_disconnectNodesButtonMouseClicked
        if (evt.getButton() == MouseEvent.BUTTON1)
            if (!GraphBoard.isPlaying()) {
                disconnectNodesAction();
            }
    }//GEN-LAST:event_disconnectNodesButtonMouseClicked

    private void playLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_playLabelMouseClicked
        if (evt.getButton() == MouseEvent.BUTTON1) {
            playLabelAction();
        }
    }//GEN-LAST:event_playLabelMouseClicked

    private void addEdgesRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addEdgesRadioButtonActionPerformed
        if (!GraphBoard.isPlaying()) {
            if (addEdgesRadioButton.isSelected()) {
                GraphBoard.setAddingEdges(true);
                GraphBoard.setAddingNodes(false);
                addEdgesRadioButton.setIcon(new ImageIcon("src\\com\\algobuddy\\gui\\img\\switchOn.png"));
                addNodesRadioButton.setIcon(new ImageIcon("src\\com\\algobuddy\\gui\\img\\switchOff.png"));
            } else {
                GraphBoard.setAddingEdges(false);
                addEdgesRadioButton.setIcon(new ImageIcon("src\\com\\algobuddy\\gui\\img\\switchOff.png"));
            }
        }
    }//GEN-LAST:event_addEdgesRadioButtonActionPerformed

    private void addNodesRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addNodesRadioButtonActionPerformed
        if (!GraphBoard.isPlaying()) {
            if (addNodesRadioButton.isSelected()) {
                GraphBoard.setAddingNodes(true);
                GraphBoard.setAddingEdges(false);
                addNodesRadioButton.setIcon(new ImageIcon("src\\com\\algobuddy\\gui\\img\\switchOn.png"));
                addEdgesRadioButton.setIcon(new ImageIcon("src\\com\\algobuddy\\gui\\img\\switchOff.png"));
            } else {
                GraphBoard.setAddingNodes(false);
                addNodesRadioButton.setIcon(new ImageIcon("src\\com\\algobuddy\\gui\\img\\switchOff.png"));
            }
        }
    }//GEN-LAST:event_addNodesRadioButtonActionPerformed

    private void directedStateRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_directedStateRadioButtonActionPerformed
        if (!GraphBoard.isPlaying()) {
            if (directedStateRadioButton.isSelected()) {
                GraphBoard.setDirected(true);
                directedStateRadioButton.setIcon(new ImageIcon("src\\com\\algobuddy\\gui\\img\\switchOn.png"));
                clearEdgesAction();
            } else {
                GraphBoard.setDirected(false);
                directedStateRadioButton.setIcon(new ImageIcon("src\\com\\algobuddy\\gui\\img\\switchOff.png"));
                clearEdgesAction();
            }
        }
    }//GEN-LAST:event_directedStateRadioButtonActionPerformed

    private void resetLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_resetLabelMouseClicked
        resetLabelAction();
    }//GEN-LAST:event_resetLabelMouseClicked

    private void speedSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_speedSliderStateChanged
        int speed = speedSlider.getValue();
        GraphBoard.setSpeed(speed);
    }//GEN-LAST:event_speedSliderStateChanged

    /**
     * Resets the graph panel to its initial state
     */
    void reset() {
        GraphBoard.setPlayingState(false);
        playLabel.setIcon(new ImageIcon("src\\com\\algobuddy\\gui\\img\\playEnabled.png"));
        resetLabel.setIcon(new ImageIcon("src\\com\\algobuddy\\gui\\img\\resetDisabled.png"));
        clearAllAction();
        GraphBoard.setAddingNodes(true);
        GraphBoard.setAddingEdges(false);
        addNodesRadioButton.setIcon(new ImageIcon("src\\com\\algobuddy\\gui\\img\\switchOn.png"));
        addEdgesRadioButton.setIcon(new ImageIcon("src\\com\\algobuddy\\gui\\img\\switchOff.png"));
        GraphBoard.setDirected(false);
        directedStateRadioButton.setIcon(new ImageIcon("src\\com\\algobuddy\\gui\\img\\switchOff.png"));
        speedSlider.setValue(1650);
        clearEdgesAction();
        GraphBoard.setCurrentAlgo("");
        Node.selectNone(GraphBoard.nodes);
    }

    /**
     * Plays and pauses the algorithm simulator
     */
    private void playLabelAction() {
        BorderLayout layout = (BorderLayout) this.getLayout();
        Component comp = layout.getLayoutComponent(BorderLayout.CENTER);
        if (comp instanceof BFS) {
            BFS bfs = (BFS) comp;
            if (GraphBoard.isPlaying()) {
                if (bfs.getBfsWorker().isPaused()) {
                    bfs.getBfsWorker().resume();
                    playLabel.setIcon(new ImageIcon("src\\com\\algobuddy\\gui\\img\\pauseButton.png"));
                } else {
                    bfs.getBfsWorker().pause();
                    if (!bfs.isCompleted()) {
                        playLabel.setIcon(new ImageIcon("src\\com\\algobuddy\\gui\\img\\playEnabled.png"));
                    }
                }
            } else if (!GraphBoard.nodes.isEmpty()) {
                GraphBoard.setPlayingState(true);
                playLabel.setIcon(new ImageIcon("src\\com\\algobuddy\\gui\\img\\pauseButton.png"));
                resetLabel.setIcon(new ImageIcon("src\\com\\algobuddy\\gui\\img\\resetEnabled.png"));
                bfs.start();
            }
        }
    }

    /**
     * Resets the algorithm simulator
     */
    private void resetLabelAction() {
        BorderLayout layout = (BorderLayout) this.getLayout();
        Component comp = layout.getLayoutComponent(BorderLayout.CENTER);
        if (comp instanceof BFS) {
            BFS bfs = (BFS) comp;
            bfs.getBfsWorker().cancel(true);
            GraphBoard.setPlayingState(false);
            GraphBoard.setSource(null);
            Node.selectNone(GraphBoard.nodes);
            resetLabel.setIcon(new ImageIcon("src\\com\\algobuddy\\gui\\img\\resetDisabled.png"));
            playLabel.setIcon(new ImageIcon("src\\com\\algobuddy\\gui\\img\\playEnabled.png"));
            repaint();
        }
    }

    /**
     * Clears all the painted nodes and edges
     */
    private void clearAllAction() {
        if (!GraphBoard.isPlaying()) {
            GraphBoard.nodes.clear();
            GraphBoard.edges.clear();
            Node.selectedNodes.clear();
            GraphBoard.setSource(null);
            repaint();
        }
    }

    /**
     * Clears all the painted edges
     */
    private void clearEdgesAction() {
        GraphBoard.edges.clear();
        repaint();
    }

    /**
     * Deletes the selected nodes
     */
    private void deleteAction() {
        if (!GraphBoard.isPlaying()) {
            ListIterator<Node> it = GraphBoard.nodes.listIterator();
            while (it.hasNext()) {
                Node n = it.next();
                if (n.isSelected()) {
                    deleteEdges(n);
                    Node.selectedNodes.remove(n);
                    it.remove();
                }
            }
            GraphBoard.setSource(null);
            repaint();
        }
    }

    /**
     * Deletes the selected edges
     */
    private void deleteEdges(Node n) {
        ListIterator<Edge> it = GraphBoard.edges.listIterator();
        while (it.hasNext()) {
            Edge e = it.next();
            if (e.getNode1() == n || e.getNode2() == n) {
                it.remove();
            }
        }
    }

    /**
     * Disconnects two nodes
     */
    private void disconnectNodesAction() {
        if (!GraphBoard.isPlaying()) {
            if (Node.selectedNodes.size() != 2) {
                JOptionPane.showMessageDialog(null,
                        "Please select exactly two nodes. ",
                        "WARNING!",
                        JOptionPane.WARNING_MESSAGE);
            } else {
                Iterator<Node> it = Node.selectedNodes.iterator();
                Node n1 = it.next();
                Node n2 = null;
                while (it.hasNext()) {
                    n2 = it.next();
                }
                ListIterator<Edge> it2 = GraphBoard.edges.listIterator();
                while (it2.hasNext()) {
                    Edge e = it2.next();
                    if (GraphBoard.isDirected()) {
                        if (e.getNode1() == n1 && e.getNode2() == n2) {
                            it2.remove();
                        }
                    } else {
                        if ((e.getNode1() == n1 && e.getNode2() == n2) || (e.getNode1() == n2 && e.getNode2() == n1)) {
                            it2.remove();
                        }
                    }
                    repaint();
                }
            }
        }
    }

    /**
     * Returns the reference of play label
     */
    public static javax.swing.JLabel getPlayLabel() {
        return playLabel;
    }

    /**
     * Returns the reference of reset label
     */
    public static javax.swing.JLabel getResetLabel() {
        return resetLabel;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton addEdgesRadioButton;
    private javax.swing.JRadioButton addNodesRadioButton;
    private javax.swing.JButton clearAllButton;
    private javax.swing.JButton deleteNodesButton;
    private javax.swing.JRadioButton directedStateRadioButton;
    private javax.swing.JButton disconnectNodesButton;
    private javax.swing.JPanel graphHidePanel1;
    private javax.swing.JPanel graphHidePanel2;
    private javax.swing.JPanel graphHidePanel3;
    private javax.swing.JPanel graphWestPanel;
    private static javax.swing.JLabel playLabel;
    private static javax.swing.JLabel resetLabel;
    private static javax.swing.JSlider speedSlider;
    // End of variables declaration//GEN-END:variables
}