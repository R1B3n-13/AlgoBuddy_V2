package com.algobuddy.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;

/**
 *
 * @author nebir, nazrul
 */
public class MainFrame extends javax.swing.JFrame {

    private int prevX, prevY;
    private Action enterAction;
    private static MainFrame mainFrame;

    /**
     * Creates new form MainFrame
     */
    public MainFrame() {
        initComponents();
        enterAction = new EnterAction();
        introPanel.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "enter");
        introPanel.getActionMap().put("enter", enterAction);
        indexPanel.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "enter");
        indexPanel.getActionMap().put("enter", enterAction);
        graphIndexPanel.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "enter");
        graphIndexPanel.getActionMap().put("enter", enterAction);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        titleBar = new javax.swing.JPanel();
        closeLabel = new javax.swing.JLabel();
        minimizeLabel = new javax.swing.JLabel();
        backLabel = new javax.swing.JLabel();
        mainPanel = new com.algobuddy.gui.JSlidingPanel();
        introPanel = new keeptoo.KGradientPanel();
        introLogoLabel = new javax.swing.JLabel();
        introTextLabel = new javax.swing.JLabel();
        introGifLabel = new javax.swing.JLabel();
        indexPanel = new keeptoo.KGradientPanel();
        indexLabel = new javax.swing.JLabel();
        goToGraphButton = new javax.swing.JButton();
        goToSortButton = new javax.swing.JButton();
        goToRecursionButton = new javax.swing.JButton();
        graphIndexPanel = new keeptoo.KGradientPanel();
        graphIndexLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setAlwaysOnTop(true);
        setUndecorated(true);
        setResizable(false);

        titleBar.setBackground(new java.awt.Color(13, 8, 18));
        titleBar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(13, 8, 18)));
        titleBar.setPreferredSize(new java.awt.Dimension(800, 25));
        titleBar.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                titleBarMouseDragged(evt);
            }
        });
        titleBar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                titleBarMousePressed(evt);
            }
        });

        closeLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/algobuddy/gui/img/closeLabel.png"))); // NOI18N
        closeLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closeLabelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                closeLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                closeLabelMouseExited(evt);
            }
        });

        minimizeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        minimizeLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/algobuddy/gui/img/minimizeLabel.png"))); // NOI18N
        minimizeLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                minimizeLabelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                minimizeLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                minimizeLabelMouseExited(evt);
            }
        });

        backLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        backLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/algobuddy/gui/img/backLabel.png"))); // NOI18N
        backLabel.setVisible(false);
        backLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                backLabelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                backLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                backLabelMouseExited(evt);
            }
        });

        javax.swing.GroupLayout titleBarLayout = new javax.swing.GroupLayout(titleBar);
        titleBar.setLayout(titleBarLayout);
        titleBarLayout.setHorizontalGroup(
            titleBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, titleBarLayout.createSequentialGroup()
                .addComponent(backLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 738, Short.MAX_VALUE)
                .addComponent(minimizeLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(closeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        titleBarLayout.setVerticalGroup(
            titleBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(closeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
            .addComponent(minimizeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(backLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        getContentPane().add(titleBar, java.awt.BorderLayout.PAGE_START);

        introPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(13, 8, 18)));
        introPanel.setkEndColor(new java.awt.Color(18, 5, 32));
        introPanel.setkStartColor(new java.awt.Color(10, 10, 15));
        introPanel.setName("introPanel"); // NOI18N
        introPanel.setPreferredSize(new java.awt.Dimension(800, 600));

        introLogoLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        introLogoLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/algobuddy/gui/img/introLogo.png"))); // NOI18N

        introTextLabel.setBackground(new java.awt.Color(205, 214, 224));
        introTextLabel.setFont(new java.awt.Font("Baskerville Old Face", 1, 72)); // NOI18N
        introTextLabel.setForeground(new java.awt.Color(172, 179, 186));
        introTextLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        introTextLabel.setText("AlgoBuddy");
        introTextLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        introTextLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                introTextLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                introTextLabelMouseExited(evt);
            }
        });

        introGifLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        introGifLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/algobuddy/gui/img/introGifLabel.gif"))); // NOI18N
        introGifLabel.setName(""); // NOI18N

        javax.swing.GroupLayout introPanelLayout = new javax.swing.GroupLayout(introPanel);
        introPanel.setLayout(introPanelLayout);
        introPanelLayout.setHorizontalGroup(
            introPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(introPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(introPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(introLogoLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(introGifLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 786, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, introPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(introTextLabel)
                .addGap(224, 224, 224))
        );
        introPanelLayout.setVerticalGroup(
            introPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(introPanelLayout.createSequentialGroup()
                .addGap(148, 148, 148)
                .addComponent(introLogoLabel)
                .addGap(24, 24, 24)
                .addComponent(introTextLabel)
                .addGap(18, 18, 18)
                .addComponent(introGifLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(232, Short.MAX_VALUE))
        );

        mainPanel.add(introPanel, "introPanel");

        indexPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(18, 8, 13)));
        indexPanel.setkEndColor(new java.awt.Color(32, 5, 18));
        indexPanel.setkStartColor(new java.awt.Color(15, 10, 10));
        indexPanel.setName("indexPanel"); // NOI18N
        indexPanel.setPreferredSize(new java.awt.Dimension(800, 600));

        indexLabel.setFont(new java.awt.Font("Georgia", 0, 48)); // NOI18N
        indexLabel.setForeground(new java.awt.Color(157, 162, 173));
        indexLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        indexLabel.setText("INDEX");

        goToGraphButton.setBackground(new java.awt.Color(18, 8, 13));
        goToGraphButton.setFont(new java.awt.Font("Cambria", 1, 20)); // NOI18N
        goToGraphButton.setForeground(new java.awt.Color(172, 179, 186));
        goToGraphButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/algobuddy/gui/img/graphLogo.png"))); // NOI18N
        goToGraphButton.setText("Graph Algorithms");
        goToGraphButton.setBorder(null);
        goToGraphButton.setBorderPainted(false);
        goToGraphButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        goToGraphButton.setIconTextGap(10);
        goToGraphButton.setMaximumSize(new java.awt.Dimension(222, 72));
        goToGraphButton.setMinimumSize(new java.awt.Dimension(222, 72));
        goToGraphButton.setPreferredSize(new java.awt.Dimension(222, 72));
        goToGraphButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                goToGraphButtonMouseClicked(evt);
            }
        });

        goToSortButton.setBackground(new java.awt.Color(18, 8, 13));
        goToSortButton.setFont(new java.awt.Font("Cambria", 1, 17)); // NOI18N
        goToSortButton.setForeground(new java.awt.Color(172, 179, 186));
        goToSortButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/algobuddy/gui/img/recursionTreeLogo.png"))); // NOI18N
        goToSortButton.setText("Recursion Tree Builder");
        goToSortButton.setBorder(null);
        goToSortButton.setBorderPainted(false);
        goToSortButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        goToSortButton.setIconTextGap(8);
        goToSortButton.setMaximumSize(new java.awt.Dimension(222, 72));
        goToSortButton.setMinimumSize(new java.awt.Dimension(222, 72));
        goToSortButton.setPreferredSize(new java.awt.Dimension(222, 72));

        goToRecursionButton.setBackground(new java.awt.Color(18, 8, 13));
        goToRecursionButton.setFont(new java.awt.Font("Cambria", 1, 20)); // NOI18N
        goToRecursionButton.setForeground(new java.awt.Color(172, 179, 186));
        goToRecursionButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/algobuddy/gui/img/sortLogo.png"))); // NOI18N
        goToRecursionButton.setText("Sorting Algorithms");
        goToRecursionButton.setBorder(null);
        goToRecursionButton.setBorderPainted(false);
        goToRecursionButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        goToRecursionButton.setIconTextGap(10);
        goToRecursionButton.setMaximumSize(new java.awt.Dimension(222, 72));
        goToRecursionButton.setMinimumSize(new java.awt.Dimension(222, 72));
        goToRecursionButton.setPreferredSize(new java.awt.Dimension(222, 72));

        javax.swing.GroupLayout indexPanelLayout = new javax.swing.GroupLayout(indexPanel);
        indexPanel.setLayout(indexPanelLayout);
        indexPanelLayout.setHorizontalGroup(
            indexPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(indexPanelLayout.createSequentialGroup()
                .addContainerGap(277, Short.MAX_VALUE)
                .addGroup(indexPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(goToGraphButton, javax.swing.GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)
                    .addComponent(goToRecursionButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(goToSortButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(indexLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(277, Short.MAX_VALUE))
        );
        indexPanelLayout.setVerticalGroup(
            indexPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(indexPanelLayout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(indexLabel)
                .addGap(49, 49, 49)
                .addComponent(goToGraphButton, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(goToRecursionButton, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(goToSortButton, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(184, Short.MAX_VALUE))
        );

        mainPanel.add(indexPanel, "indexPanel");

        graphIndexPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(8, 13, 18)));
        graphIndexPanel.setkEndColor(new java.awt.Color(5, 18, 32));
        graphIndexPanel.setkStartColor(new java.awt.Color(10, 10, 15));
        graphIndexPanel.setName("graphIndexPanel"); // NOI18N

        graphIndexLabel.setFont(new java.awt.Font("Georgia", 0, 36)); // NOI18N
        graphIndexLabel.setForeground(new java.awt.Color(157, 162, 173));
        graphIndexLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        graphIndexLabel.setText("GRAPH ALGORITHMS");

        javax.swing.GroupLayout graphIndexPanelLayout = new javax.swing.GroupLayout(graphIndexPanel);
        graphIndexPanel.setLayout(graphIndexPanelLayout);
        graphIndexPanelLayout.setHorizontalGroup(
            graphIndexPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(graphIndexPanelLayout.createSequentialGroup()
                .addGap(210, 210, 210)
                .addComponent(graphIndexLabel)
                .addContainerGap(211, Short.MAX_VALUE))
        );
        graphIndexPanelLayout.setVerticalGroup(
            graphIndexPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(graphIndexPanelLayout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(graphIndexLabel)
                .addContainerGap(515, Short.MAX_VALUE))
        );

        mainPanel.add(graphIndexPanel, "graphIndexPanel");

        getContentPane().add(mainPanel, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void titleBarMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_titleBarMousePressed
        if (evt.getButton() == MouseEvent.BUTTON1) {
            prevX = evt.getX();
            prevY = evt.getY();
        }
    }//GEN-LAST:event_titleBarMousePressed

    private void titleBarMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_titleBarMouseDragged
        int b1 = MouseEvent.BUTTON1_DOWN_MASK;
        int b2 = MouseEvent.BUTTON2_DOWN_MASK;
        if ((evt.getModifiersEx() & (b1 | b2)) == b1) { // this checks that BUTTON1 is down but BUTTON2 is not
            int curX = evt.getXOnScreen();
            int curY = evt.getYOnScreen();
            if (getExtendedState() == NORMAL) {
                this.setLocation(curX - prevX, curY - prevY);
            }
        }
    }//GEN-LAST:event_titleBarMouseDragged

    private void minimizeLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_minimizeLabelMouseExited
        minimizeLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/algobuddy/gui/img/minimizeLabel.png")));
    }//GEN-LAST:event_minimizeLabelMouseExited

    private void minimizeLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_minimizeLabelMouseEntered
        minimizeLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/algobuddy/gui/img/minimizeLabelHover.png")));
    }//GEN-LAST:event_minimizeLabelMouseEntered

    private void minimizeLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_minimizeLabelMouseClicked
        if (evt.getButton() == MouseEvent.BUTTON1) {
            setExtendedState(ICONIFIED);
        }
    }//GEN-LAST:event_minimizeLabelMouseClicked

    private void closeLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeLabelMouseExited
        closeLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/algobuddy/gui/img/closeLabel.png")));
    }//GEN-LAST:event_closeLabelMouseExited

    private void closeLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeLabelMouseEntered
        closeLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/algobuddy/gui/img/closeLabelHover.png")));
    }//GEN-LAST:event_closeLabelMouseEntered

    private void closeLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeLabelMouseClicked
        if (evt.getButton() == MouseEvent.BUTTON1)
            System.exit(0);
    }//GEN-LAST:event_closeLabelMouseClicked

    private void introTextLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_introTextLabelMouseExited
        introTextLabel.setForeground(new Color(172, 179, 186));
    }//GEN-LAST:event_introTextLabelMouseExited

    private void introTextLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_introTextLabelMouseEntered
        introTextLabel.setForeground(new Color(158, 164, 173));
    }//GEN-LAST:event_introTextLabelMouseEntered

    private void backLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_backLabelMouseEntered
        backLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/algobuddy/gui/img/backHoverLabel.png")));
    }//GEN-LAST:event_backLabelMouseEntered

    private void backLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_backLabelMouseExited
        backLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/algobuddy/gui/img/backLabel.png")));
    }//GEN-LAST:event_backLabelMouseExited

    private void backLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_backLabelMouseClicked
        if (evt.getButton() == MouseEvent.BUTTON1) {
            if (getCurrentComponentName(mainPanel).equals("indexPanel")) {
                mainPanel.nextSlidingPanel(10, introPanel, JSlidingPanel.Direction.Right);
                titleBar.setBackground(new Color(13, 8, 18));
                titleBar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(13, 8, 18)));
                backLabel.setVisible(false);
            }
            if (getCurrentComponentName(mainPanel).equals("graphIndexPanel")) {
                mainPanel.nextSlidingPanel(10, indexPanel, JSlidingPanel.Direction.Right);
                titleBar.setBackground(new Color(18, 8, 13));
                titleBar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(18, 8, 13)));
            }
        }
    }//GEN-LAST:event_backLabelMouseClicked

    private void goToGraphButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_goToGraphButtonMouseClicked
        if (evt.getButton() == MouseEvent.BUTTON1) {
            mainPanel.nextSlidingPanel(10, graphIndexPanel, JSlidingPanel.Direction.Left);
            titleBar.setBackground(new Color(8, 13, 18));
            titleBar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(8, 13, 18)));
        }
    }//GEN-LAST:event_goToGraphButtonMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                mainFrame = new MainFrame();
                mainFrame.setVisible(true);
            }
        });
    }

    class EnterAction extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (getCurrentComponentName(mainPanel).equals("introPanel")) {
                mainPanel.nextSlidingPanel(10, indexPanel, JSlidingPanel.Direction.Down);
                titleBar.setBackground(new Color(18, 8, 13));
                titleBar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(18, 8, 13)));
                backLabel.setVisible(true);
            }
        }
    }

    String getCurrentComponentName(Container parent) {
        String compName = null;
        Component comp = null;
        int n = parent.getComponentCount();
        for (int i = 0; i < n; i++) {
            comp = parent.getComponent(i);
            if (comp.isVisible()) {
                compName = comp.getName();
            }
        }
        return compName;
    }

    static MainFrame getMainFrame() {
        return mainFrame;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel backLabel;
    private javax.swing.JLabel closeLabel;
    private javax.swing.JButton goToGraphButton;
    private javax.swing.JButton goToRecursionButton;
    private javax.swing.JButton goToSortButton;
    private javax.swing.JLabel graphIndexLabel;
    private keeptoo.KGradientPanel graphIndexPanel;
    private javax.swing.JLabel indexLabel;
    private keeptoo.KGradientPanel indexPanel;
    private javax.swing.JLabel introGifLabel;
    private javax.swing.JLabel introLogoLabel;
    private keeptoo.KGradientPanel introPanel;
    private javax.swing.JLabel introTextLabel;
    private com.algobuddy.gui.JSlidingPanel mainPanel;
    private javax.swing.JLabel minimizeLabel;
    private javax.swing.JPanel titleBar;
    // End of variables declaration//GEN-END:variables
}
