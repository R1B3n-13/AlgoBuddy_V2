package com.algobuddy.gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.GeneralPath;

/**
 *
 * @author nebir, nazrul
 */
public class DrawArrow {

    public DrawArrow(final Graphics2D gfx, final Point start, final Point end, double progress, final Color col, final Stroke lineStroke,
            final Stroke arrowStroke, final float arrowSize) {

        gfx.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        final Point newStart = new Point(getIntersectionPoint(end, start));
        final Point newEnd = new Point(getIntersectionPoint(start, end));

        int endx = (int) (newStart.x + (newEnd.x - newStart.x) * progress);
        int endy = (int) (newStart.y + (newEnd.y - newStart.y) * progress);
        int startx = newStart.x;
        int starty = newStart.y;

        gfx.setStroke(arrowStroke);
        gfx.setColor(col);
        final double deltax = startx - endx;
        final double result;
        if (deltax == 0.0d) {
            if (endy > start.y) {
                result = -(Math.PI / 2);
            } else {
                result = Math.PI / 2;
            }
        } else {
            result = Math.atan((starty - endy) / deltax) + (startx < endx ? Math.PI : 0);
        }

        final double angle = result;

        final double arrowAngle = Math.PI / 12.0d;

        final double x1 = arrowSize * Math.cos(angle - arrowAngle);
        final double y1 = arrowSize * Math.sin(angle - arrowAngle);
        final double x2 = arrowSize * Math.cos(angle + arrowAngle);
        final double y2 = arrowSize * Math.sin(angle + arrowAngle);

        final double cx = (arrowSize / 2.0f) * Math.cos(angle);
        final double cy = (arrowSize / 2.0f) * Math.sin(angle);

        final GeneralPath polygon = new GeneralPath();
        polygon.moveTo(endx, endy);
        polygon.lineTo(endx + x1, endy + y1);
        polygon.lineTo(endx + x2, endy + y2);
        polygon.closePath();
        gfx.fill(polygon);

        gfx.setStroke(lineStroke);
        gfx.drawLine(startx, starty, (int) (endx + cx), (int) (endy + cy));
    }

    private Point getIntersectionPoint(Point p1, Point p2) {
        double m = Math.sqrt(((p1.x - p2.x) * (p1.x - p2.x)) + ((p1.y - p2.y) * (p1.y - p2.y)));
        double n = Node.getRadius();
        Point res = new Point();
        res.x = (int) ((m * p2.x + n * p1.x) / (m + n));
        res.y = (int) ((m * p2.y + n * p1.y) / (m + n));
        return res;
    }
}
