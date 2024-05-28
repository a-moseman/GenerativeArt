package org.amoseman.generativeart.filter.draw;

import org.amoseman.generativeart.image.ColorValue;
import org.amoseman.generativeart.filter.Filter;
import org.amoseman.generativeart.image.ImageData;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.random.RandomGenerator;

public class Text implements Filter {
    private final float x;
    private final float y;
    private final String text;
    private final ColorValue color;
    private final Font font;
    private final boolean centered;

    public Text(float x, float y, String text, ColorValue color, Font font) {
        this.x = x;
        this.y = y;
        this.text = text;
        this.color = color;
        this.font = font;
        this.centered = false;
    }

    public Text(float x, float y, String text, ColorValue color, Font font, boolean centered) {
        this.x = x;
        this.y = y;
        this.text = text;
        this.color = color;
        this.font = font;
        this.centered = centered;
    }

    @Override
    public void apply(ImageData data, RandomGenerator random) {
        BufferedImage image = data.asImage();
        Graphics graphics = image.getGraphics();
        graphics.setFont(font);
        int px = Math.round(x);
        int py = Math.round(y);
        if (centered) {
            FontMetrics metrics = graphics.getFontMetrics(font);
            px -= metrics.stringWidth(text) / 2;
            py -= metrics.getHeight() / 2 - metrics.getAscent();
        }
        graphics.setColor(color.toColor());
        graphics.drawString(text, px, py);
        for (int x = 0; x < data.getWidth(); x++) {
            for (int y = 0; y < data.getHeight(); y++) {
                Color c = new Color(image.getRGB(x, y));
                float[] v = new float[4];
                v = c.getRGBComponents(v);
                data.set(x, y, new ColorValue(v[0], v[1], v[2], v[3]));
            }
        }
    }
}
