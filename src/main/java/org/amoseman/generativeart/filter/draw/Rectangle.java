package org.amoseman.generativeart.filter.draw;

import org.amoseman.generativeart.ColorValue;
import org.amoseman.generativeart.filter.Filter;
import org.amoseman.generativeart.image.ImageData;
import org.amoseman.generativeart.vector.Vector;
import org.amoseman.generativeart.vector.VectorMath;

import java.util.Random;

public class Rectangle implements Filter {
    private final float x;
    private final float y;
    private final float w;
    private final float h;
    private final Vector center;
    private final float rotation;
    private final ColorValue color;
    private final boolean fill;

    public Rectangle(float x, float y, float w, float h, float rotation, ColorValue color, boolean fill) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.center = new Vector(x + w / 2, y + h / 2);
        this.rotation = rotation;
        this.color = color;
        this.fill = fill;
    }

    @Override
    public void apply(ImageData data, Random random) {
        if (fill) {
            for (float dx = x; dx < w + x; dx++) {
                for (float dy = y; dy < h + y; dy++) {
                    Vector point = VectorMath.rotate(new Vector(dx, dy), center, rotation);
                    int px = (int) point.x;
                    int py = (int) point.y;
                    data.set(px, py, color);
                }
            }
            return;
        }

        Vector a = VectorMath.rotate(new Vector(x, y), center, rotation);
        Vector b = VectorMath.rotate(new Vector(x + w, y), center, rotation);
        Vector c = VectorMath.rotate(new Vector(x + w, y + h), center, rotation);
        Vector d = VectorMath.rotate(new Vector(x, y + h), center, rotation);

        new Line((float) a.x, (float) a.y, (float) b.x, (float) b.y, color).apply(data, random);
        new Line((float) b.x, (float) b.y, (float) c.x, (float) c.y, color).apply(data, random);
        new Line((float) c.x, (float) c.y, (float) d.x, (float) d.y, color).apply(data, random);
        new Line((float) d.x, (float) d.y, (float) a.x, (float) a.y, color).apply(data, random);
    }
}
