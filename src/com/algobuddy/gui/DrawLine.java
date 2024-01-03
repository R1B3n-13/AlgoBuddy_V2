package com.algobuddy.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;

/**
 *
 * @author nebir, nazrul
 */
public class DrawLine {

    private Point p1;
    private Point p2;
    Graphics2D gfx;

    public DrawLine(Graphics2D gfx, Point p1, Point p2, double progress, Color col, Stroke LineStroke) {
        this.p1 = p1;
        this.p2 = p2;
        this.gfx = gfx;
        double n = Node.getRadius();
        double m = Math.sqrt(((p1.x - p2.x) * (p1.x - p2.x)) + ((p1.y - p2.y) * (p1.y - p2.y)));
        Point newP2 = getIntersectionPoint(m, n);
        m = Node.getRadius();
        n = Math.sqrt(((p1.x - p2.x) * (p1.x - p2.x)) + ((p1.y - p2.y) * (p1.y - p2.y)));
        Point newP1 = getIntersectionPoint(m, n);

        int x2 = (int) (newP1.x + (newP2.x - newP1.x) * progress);
        int y2 = (int) (newP1.y + (newP2.y - newP1.y) * progress);
        int x1 = newP1.x;
        int y1 = newP1.y;

        gfx.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        gfx.setColor(col);
        gfx.setStroke(LineStroke);
        if (x2 == newP2.x && y2 == newP2.y) {
            x2 = p2.x;
            y2 = p2.y;
        }
        gfx.drawLine(x1, y1, x2, y2);

        gfx.setColor(new Color(240, 10, 9));
        gfx.fillOval(x2 - 6, y2 - 6, 12, 12);
    }

    private Point getIntersectionPoint(double m, double n) {
        Point res = new Point();
        res.x = (int) ((m * p2.x + n * p1.x) / (m + n));
        res.y = (int) ((m * p2.y + n * p1.y) / (m + n));
        return res;
    }
}
