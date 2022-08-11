package com.algobuddy.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.*;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author Nebir, Nazrul
 */
public class recursionPanel extends javax.swing.JPanel {

    private int panel_width, panel_height;
    private ArrayList<ArrayList<Integer>> index;
    private int[] parent, value, level, x_, y_, is_visited;
    private int node_diameter, art_campus, max_level, dif_bet_two_cons;
    private boolean fl1 = false;
    private int string_flag = 0;
    private int[][] edges;
    ArrayList<String> strs;
    private int art_speed;
    private String show;
    private Queue<Integer> Q1, Q2;
    public static boolean isPlaying = false;
    public static AlgoWorker<Void, Void> recursionWorker;

    public recursionPanel() {
        initComponents();
    }

    private void dfs(int node, int par) throws InterruptedException {
        if (fl1 == false) {
            return;
        }
        repaint();
        Thread.sleep(800);
        if (par != -1) {
            edges[par][node] = 1;
            string_flag = 1;
            show = "fn( " + strs.get(par) + ") calls fn(" + strs.get(node) + ")";
        }
        is_visited[node] = 1;
        repaint();

        for (int i = 0; i < index.size(); i++) {
            if (parent[i] == node) {
                dfs(i, node);
            }
        }
        if (fl1 == false) {
            return;
        }
        Thread.sleep(800);
        string_flag = 2;
        show = "fn(" + strs.get(node) + ") returns " + Integer.toString(value[node]);
        if (par != -1) {
            edges[par][node] = 2;
        }
        repaint();
    }

    int ktk = 0;

    void process_in_background() {
        recursionWorker = new AlgoWorker<>() {
            @Override
            public Void doInBackground() throws InterruptedException {
                while (Q1.size() > 0 && !isCancelled()) {
                    if (!isPaused()) {
                        int node1 = Q1.remove(), node2 = Q2.remove();
                        if (node1 > node2) {
                            if (node2 != -1) {
                                edges[node2][node1] = 1;
                                string_flag = 1;
                                show = "fn( " + strs.get(node2) + ") calls fn(" + strs.get(node1) + ")";
                            }
                            is_visited[node1] = 1;
                        } else {
                            if (node1 != -1) {
                                edges[node1][node2] = 2;
                            }
                            string_flag = 2;
                            show = "fn(" + strs.get(node2) + ") returns " + Integer.toString(value[node2]);

                        }
                        System.out.println(node1 + " " + node2);
                        repaint();
                        Thread.sleep(500);
                    } else {
                        Thread.sleep(200);
                    }
                }
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void done() {
            fiboRunLabel.setIcon(new ImageIcon("src\\com\\algobuddy\\gui\\img\\playDisabled.png"));
            fastPowerRunLabel.setIcon(new ImageIcon("src\\com\\algobuddy\\gui\\img\\playDisabled.png"));
            bcRunLabel.setIcon(new ImageIcon("src\\com\\algobuddy\\gui\\img\\playDisabled.png"));
            lcsRunLabel.setIcon(new ImageIcon("src\\com\\algobuddy\\gui\\img\\playDisabled.png"));
            }
        };
        recursionWorker.execute();

    }

    private void dfs1(int node, int par) {
        Q1.add(node);
        Q2.add(par);
        for (int i = 0; i < index.size(); i++) {
            if (parent[i] == node) {
                dfs1(i, node);
            }
        }
        Q1.add(par);
        Q2.add(node);
    }

    void create_cor() {
        for (int i = max_level; i >= 0; i--) {
            ArrayList<Integer> l1 = new ArrayList<>();
            for (int j = 0; j < index.size(); j++) {
                if (level[j] == i) {
                    l1.add(j);
                }
            }

            for (int n2 : l1) {
                ArrayList<Integer> l2 = new ArrayList<>();
                for (int j = 0; j < index.size(); j++) {
                    if (parent[j] == n2) {
                        l2.add(j);
                    }
                }

                if (l2.size() == 0) {
                    y_[n2] = 0;
                    continue;
                }

                int child = l2.get(0);

                int maxy[] = new int[max_level + 2], miny[] = new int[max_level + 3];
                for (int k4 = 0; k4 <= max_level; k4++) {
                    maxy[k4] = -100000;
                    miny[k4] = 100000;
                }
                Stack<Integer> st = new Stack<>();
                st.push(child);
                while (!st.empty()) {
                    int nod = st.pop();

                    int lv = level[nod];

                    maxy[lv] = Math.max(maxy[lv], y_[nod]);
                    miny[lv] = Math.min(miny[lv], y_[nod]);
                    for (int k = 0; k < index.size(); k++) {
                        if (nod == parent[k]) {
                            st.push(k);
                        }
                    }
                }

                for (int k1 = 1; k1 < l2.size(); k1++) {
                    int miny1[] = new int[max_level + 5], maxy1[] = new int[max_level + 5];
                    for (int k4 = 0; k4 <= max_level; k4++) {
                        maxy1[k4] = -100000;
                        miny1[k4] = 100000;
                    }

                    int nod1 = l2.get(k1);
                    Stack<Integer> s1 = new Stack<>();
                    s1.push(nod1);

                    ArrayList<Integer> l3 = new ArrayList<>();
                    while (!s1.empty()) {
                        int nod = s1.pop();
                        l3.add(nod);
                        maxy1[level[nod]] = Math.max(maxy1[level[nod]], y_[nod]);
                        miny1[level[nod]] = Math.min(miny1[level[nod]], y_[nod]);
                        for (int k = 0; k < index.size(); k++) {
                            if (nod == parent[k]) {
                                s1.push(k);
                            }
                        }
                    }

                    int l = 0, r = 4000, ans = 4000;
                    while (l <= r) {
                        int mid = (l + r) / 2;
                        boolean fl2 = true;
                        for (int k2 = i; k2 <= max_level; k2++) {
                            if (miny1[k2] >= 100000 || maxy[k2] <= -100000); //--------
                            int po = miny1[k2] + mid - maxy[k2];
                            if ((po < dif_bet_two_cons)) {
                                l = mid + 1;
                                fl2 = false;
                                break;
                            }
                        }
                        if (fl2) {
                            ans = Math.min(ans, mid);
                            r = mid - 1;
                        }
                    }

                    for (int nod : l3) {
                        y_[nod] += ans + node_diameter;
                    }
                    for (int k3 = i; k3 <= max_level; k3++) {
                        miny[k3] = maxy1[k3] + ans + node_diameter;
                    }
                }

                int sum = 0, cn = 0;
                for (int kk = 0; kk <= index.size(); kk++) {
                    if (parent[kk] == n2) {
                        sum += y_[kk];
                        cn++;
                    }
                }
                y_[n2] = sum / cn;
                Stack<Integer> s4 = new Stack<>();
                s4.push(n2);
                int addition = y_[n2];
                while (!s4.empty()) {
                    int nod = s4.pop();
                    y_[nod] -= addition;
                    for (int kk = 0; kk <= index.size(); kk++) {
                        if (nod == parent[kk]) {
                            s4.push(kk);
                        }
                    }
                }
            }
        }
    }

    void create() throws InterruptedException {
        max_level = 0;

        for (int i = 0; i < index.size(); i++) {
            max_level = Math.max(max_level, level[i]);
        }

        art_campus = panel_height - 100;

        fl1 = true;
        node_diameter = (art_campus) / ((int) (2 * (double) max_level) + 1);

        dif_bet_two_cons = node_diameter / 4;
        int index_size = index.size();
        x_ = new int[index_size + 10];
        y_ = new int[index_size + 10];
        is_visited = new int[index_size + 10];
        for (int i = 0; i < index_size + 9; i++) {
            x_[i] = 0;
            y_[i] = 0;
            is_visited[i] = 0;
        }

        int is_parent[] = new int[index_size + 10];
        for (int i = 0; i < index_size; i++) {
            is_parent[i] = 0;
        }
        for (int i = 0; i < index_size; i++) {
            if (parent[i] != -1) {
                is_parent[parent[i]] = 1;
            }
        }

        create_cor();
        int mn = 0, mx = 0;
        for (int i = 0; i < index_size; i++) {
            mn = Math.min(mn, y_[i]);
            mx = Math.max(mx, y_[i]);
        }

        int x1 = 50, x2 = panel_width - 100; // artpanel width(minus 50 by 50)
        int add_value = (x1 - mn + x2 - mx) / 2 - node_diameter / 2;
        for (int i = 0; i < index_size; i++) {
            y_[i] += add_value;
        }

        int yy = 50;
        for (int j = 0; j <= max_level; j++) {
            for (int i = 0; i < index_size; i++) {
                if (j == level[i]) {
                    x_[i] = yy;
                }
            }
            yy += 2 * node_diameter;
        }

        edges = new int[index.size() + 5][index.size() + 5];
        for (int i = 0; i <= index.size(); i++) {
            for (int j = 0; j <= index.size(); j++) {
                edges[i][j] = 0;
            }
        }
        strs = new ArrayList<>();
        for (int i = 0; i < index.size(); i++) {
            String ss;
            ss = Integer.toString(index.get(i).get(0));
            for (int j = 1; j < index.get(i).size(); j++) {
                String s1 = Integer.toString(index.get(i).get(j));
                ss = ss + "," + s1;
            }
            strs.add(ss);
        }

        Q1 = new LinkedList<>();
        Q2 = new LinkedList<>();

        dfs1(0, -1);

        process_in_background();

        repaint();

    }

    public void init() throws InterruptedException {
        index = MainFrame.getIndex();
        parent = MainFrame.getPar();
        level = MainFrame.getLev();
        value = MainFrame.getVal();
        panel_width = this.getSize().width;
        panel_height = this.getSize().height;
        isPlaying = true;

        create();
    }
    
    public void reset() {
        fl1 = false;
        for (int i = 0; i < index.size(); i++) {
            is_visited[i] = 0;
        }
        repaint();
        isPlaying = false;
    }

    ArrayList<JLabel> arr = new ArrayList<>();
    JLabel label = new JLabel();

    @Override
    public void paint(Graphics g) {
        Graphics2D g1 = (Graphics2D) g;
        super.paint(g1);

        g1.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // ------------for test---------------
//      g1.drawRect(50, 50, 1477, 929);
        if (fl1) {
//         g1.fillOval(200, 200, node_diameter, node_diameter);
//         int y = 50;
//         for (int i = 0; i < max_level; i++) {
//            g1.fillOval(50, y, node_diameter, node_diameter);
//            y += 2*node_diameter;
//         }
//         g1.fillOval(50, y, node_diameter, node_diameter);
            int font_size = 0;
            int lineW = 2;
            if (node_diameter <= 30) {
                font_size = 10;
                lineW = 2;
            } else if (node_diameter > 30 && node_diameter <= 50) {
                lineW = 3;
                font_size = 16;
            } else {
                font_size = 18;
                lineW = 4;
            }
            for (int i = 0; i < index.size(); i++) {
                for (int j = 0; j < index.size(); j++) {
                    g1.setStroke(new BasicStroke(lineW));
                    int ex1 = y_[i] + node_diameter / 2, ey1 = x_[i] + node_diameter / 2;
                    int ex2 = y_[j] + node_diameter / 2, ey2 = x_[j] + node_diameter / 2;
                    if (edges[i][j] == 1) {
                        g1.setColor(new Color(108, 180, 64)); // front track edge color
                        g1.drawLine(ex1, ey1, ex2, ey2);
                    }
                    if (edges[i][j] == 2) {
                        g1.setColor(new Color(38, 112, 139)); // back track edge color
                        g1.drawLine(ex1, ey1, ex2, ey2);
                        g1.setColor(new Color(1, 22, 39)); // small circles in between nodes
                        String s1 = Integer.toString(value[j]);
                        int x = (ex1 + ex2) / 2 - font_size / 2, y = (ey1 + ey2) / 2 - font_size / 2;
                        g1.fillOval(x, y, font_size + 4, font_size + 4);
                        g1.setColor(new Color(38, 112, 139)); // edge value font color
                        g1.setFont(new Font("Casteller", Font.BOLD, font_size));
                        g1.drawString(s1, x + font_size / 2 - 4 * s1.length(), y + font_size / 2 + 7);
                    }
                }
            }

            if (string_flag >= 1) {
                g1.setColor(new Color(38, 112, 139)); // info rectangle color
                g1.fillRect(19, 19, 400, 50);
                g1.setColor(new Color(182, 207, 216)); // info rectangle font color
                g1.setFont(new Font("Consolus", Font.BOLD, 25));
                g1.drawString(show, 215 - 6 * show.length(), 50);
            }

            for (int i = 0; i < index.size(); i++) {

                if (is_visited[i] > 0) {
                    g1.setColor(new Color(108, 180, 64)); // node color
                    g1.fillOval(y_[i], x_[i], node_diameter, node_diameter);
                    g1.setColor(Color.black); // node font color
                    g1.setFont(new Font("Casteller", Font.BOLD, font_size));

                    String st1 = strs.get(i);
                    g1.drawString(st1, y_[i] + node_diameter / 2 - 5 * st1.length(), x_[i] + node_diameter / 2 + 5);
                }
            }

        }
        //------------for test------------------

    }

    public static AlgoWorker<Void, Void> getRecursionWorker() {
        return recursionWorker;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(new java.awt.Color(255, 255, 255));

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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
