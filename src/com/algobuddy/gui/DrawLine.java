package com.algobuddy.gui;

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

    public DrawLine(Graphics2D gfx, Point p1, Point p2, Color col, Stroke LineStroke) {
        this.p1 = p1;
        this.p2 = p2;
        this.gfx = gfx;
        double n = Node.getRadius();
        double m = Math.sqrt(((p1.x - p2.x) * (p1.x - p2.x)) + ((p1.y - p2.y) * (p1.y - p2.y)));
        Point newP2 = getIntersectionPoint(m, n);
        m = Node.getRadius();
        n = Math.sqrt(((p1.x - p2.x) * (p1.x - p2.x)) + ((p1.y - p2.y) * (p1.y - p2.y)));
        Point newP1 = getIntersectionPoint(m, n);
        gfx.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        gfx.setColor(col);
        gfx.setStroke(LineStroke);
        gfx.drawLine(newP1.x, newP1.y, newP2.x, newP2.y);
    }

    private Point getIntersectionPoint(double m, double n) {
        Point res = new Point();
        res.x = (int) ((m * p2.x + n * p1.x) / (m + n));
        res.y = (int) ((m * p2.y + n * p1.y) / (m + n));
        return res;
    }
}
