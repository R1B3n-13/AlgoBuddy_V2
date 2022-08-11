package com.algobuddy.videorecorder;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JComponent;

/**
 *
 * @author nebir, nazrul
 */
public class ComponentImage {

    private static List<String> types = Arrays.asList(ImageIO.getWriterFileSuffixes());

    public static BufferedImage capture(JComponent component) {
        Dimension d = component.getSize();
        if (d.width % 2 != 0) {
            d.width++;
        }
        if (d.height % 2 != 0) {
            d.height++;
        }
        Rectangle region = new Rectangle(0, 0, d.width, d.height);

        return ComponentImage.capture(component, region);
    }

    public static BufferedImage capture(JComponent component, Rectangle region) {
        BufferedImage image = new BufferedImage(region.width, region.height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();

        if (!component.isOpaque()) {
            g2d.setColor(component.getBackground());
            g2d.fillRect(region.x, region.y, region.width, region.height);
        }

        g2d.translate(-region.x, -region.y);
        component.print(g2d);
        g2d.dispose();
        return image;
    }

    public static void writeImage(BufferedImage image, String fileName) throws IOException {
        if (fileName == null) {
            return;
        }

        int offset = fileName.lastIndexOf(".");

        if (offset == -1) {
            String message = "file suffix was not specified";
            throw new IOException(message);
        }

        String type = fileName.substring(offset + 1);

        if (types.contains(type)) {
            ImageIO.write(image, type, new File(fileName));
        } else {
            String message = "unknown writer file suffix (" + type + ")";
            throw new IOException(message);
        }
    }
}
