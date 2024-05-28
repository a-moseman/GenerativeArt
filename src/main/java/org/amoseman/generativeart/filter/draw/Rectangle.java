package org.amoseman.generativeart.filter.draw;

import org.amoseman.generativeart.image.ColorValue;
import org.amoseman.generativeart.filter.Filter;
import org.amoseman.generativeart.image.ImageData;
import org.amoseman.generativeart.vector.Vector;

import java.util.random.RandomGenerator;

public class Rectangle implements Filter {
    private final float x;
    private final float y;
    private final float w;
    private final float h;
    private final Vector center;
    private final float rotation;
    private final ColorValue color;
    private final boolean fill;
    private final int thickness;

    public Rectangle(float x, float y, float w, float h, float rotation, ColorValue color, boolean fill, int thickness) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.center = new Vector(x + w / 2, y + h / 2);
        this.rotation = rotation;
        this.color = color;
        this.fill = fill;
        this.thickness = thickness;
    }

    public Rectangle(float x, float y, float w, float h, float rotation, ColorValue color, boolean fill) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.center = new Vector(x + w / 2, y + h / 2);
        this.rotation = rotation;
        this.color = color;
        this.fill = fill;
        this.thickness = 1;
    }

    @Override
    public void apply(ImageData data, RandomGenerator random) {
        if (fill) {
            for (float dx = x; dx < w + x; dx++) {
                for (float dy = y; dy < h + y; dy++) {
                    Vector point = new Vector(dx, dy).rotate(center, rotation);

                    // dumb solution to avoiding holes when rotated
                    for (float i = 0; i < 0.8f; i += 0.2f) {
                        for (float j = 0; j < 0.8f; j += 0.2f) {
                            int px = (int) Math.round(point.x + i);
                            int py = (int) Math.round(point.y + j);
                            data.set(px, py, color);
                        }
                    }
                    //int px = (int) point.x;
                    //int py = (int) point.y;
                    //data.set(px, py, color);
                }
            }
            return;
        }

        Vector a = new Vector(x, y).rotate(center, rotation);
        Vector b = new Vector(x + w, y).rotate(center, rotation);
        Vector c = new Vector(x + w, y + h).rotate(center, rotation);
        Vector d = new Vector(x, y + h).rotate(center, rotation);

        new Line((float) a.x, (float) a.y, (float) b.x, (float) b.y, color, thickness).apply(data, random);
        new Line((float) b.x, (float) b.y, (float) c.x, (float) c.y, color, thickness).apply(data, random);
        new Line((float) c.x, (float) c.y, (float) d.x, (float) d.y, color, thickness).apply(data, random);
        new Line((float) d.x, (float) d.y, (float) a.x, (float) a.y, color, thickness).apply(data, random);
    }
}
