package org.amoseman.generativeart.filter;

import org.amoseman.generativeart.image.ImageData;

import java.util.Random;

//Bresenham
//https://medium.com/geekculture/bresenhams-line-drawing-algorithm-2e0e953901b3
public class Line implements Filter {
    private final float x1;
    private final float y1;
    private final float x2;
    private final float y2;
    private final float dx;
    private final float dy;
    private final float slope;
    private final float[] value;

    public Line(float x1, float y1, float x2, float y2, float[] value) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.dx = x2 - x1;
        this.dy = y2 - y1;
        this.slope = dy / dx;
        this.value = value;
    }

    @Override
    public void apply(ImageData data, Random random) {
        float x, y;
        if (Math.abs(slope) < 1) {
            x = x1;
            y = y1;
            while (x != x2) {
                draw(data, x, y);
                x++;
                y += slope;
            }
        }
        else if (Math.abs(slope) > 1) {
            x = x1;
            y = y1;
            while (y != y2) {
                draw(data, x, y);
                y++;
                x += 1.0f / slope;
            }
        }
    }

    private void draw(ImageData data, float x, float y) {
        data.set(Math.round(x), Math.round(y), value);
    }
}
