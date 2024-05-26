package org.amoseman.generativeart.filter.draw;

import org.amoseman.generativeart.ColorValue;
import org.amoseman.generativeart.filter.Filter;
import org.amoseman.generativeart.filter.Grow;
import org.amoseman.generativeart.image.ImageData;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Text implements Filter {
    private final float x;
    private final float y;
    private final String text;
    private final ColorValue color;
    private final Font font;

    public Text(float x, float y, String text, ColorValue color, Font font) {
        this.x = x;
        this.y = y;
        this.text = text;
        this.color = color;
        this.font = font;
    }

    @Override
    public void apply(ImageData data, Random random) {
        BufferedImage image = data.asImage();
        Graphics graphics = image.getGraphics();
        graphics.setFont(font);
        int px = Math.round(x);
        int py = Math.round(y);
        graphics.setColor(color.toColor());
        graphics.drawString(text, px, py);
        for (int x = 0; x < data.getWidth(); x++) {
            for (int y = 0; y < data.getHeight(); y++) {
                Color c = new Color(image.getRGB(x, y));
                float[] v = new float[4];
                v = c.getColorComponents(v);
                data.set(x, y, new ColorValue(v[0], v[1], v[2], v[3]));
            }
        }
    }
}
