package org.amoseman.generativeart.filter.draw;

import org.amoseman.generativeart.filter.Filter;
import org.amoseman.generativeart.image.ImageData;

import java.util.Random;

//Bresenham
//https://medium.com/geekculture/bresenhams-line-drawing-algorithm-2e0e953901b3
public class Line implements Filter {
    private final float x1;
    private final float y1;
    private final float x2;
    private final float y2;
    private final float[] value;
    private final float dx;
    private final float dy;
    private final int steps;
    private final float xInc;
    private final float yInc;

    public Line(float x1, float y1, float x2, float y2, float[] value) {
        if (x1 > x2) {
            this.x1 = x2;
            this.y1 = y2;
            this.x2 = x1;
            this.y2 = y1;
        }
        else {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
        }
        this.dx = this.x2 - this.x1;
        this.dy = this.y2 - this.y1;
        this.value = value;
        this.steps = (int) (Math.max(Math.abs(dx), Math.abs(dy)));
        this.xInc = dx / (float) steps;
        this.yInc = dy / (float) steps;
    }

    @Override
    public void apply(ImageData data, Random random) {
        float x = x1;
        float y = y1;
        for (int i = 0; i <= steps; i++) {
            draw(data, x, y);
            x += xInc;
            y += yInc;
        }
    }

    private boolean compareFloat(float a, float b) {
        return a < b;
    }

    private void draw(ImageData data, float x, float y) {
        data.draw(Math.round(x), Math.round(y), value);
    }
}
