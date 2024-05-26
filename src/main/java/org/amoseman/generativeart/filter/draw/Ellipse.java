package org.amoseman.generativeart.filter.draw;

import org.amoseman.generativeart.filter.Filter;
import org.amoseman.generativeart.image.ImageData;

import java.util.Random;

public class Ellipse implements Filter {
    private final float x;
    private final float y;
    private final float a;
    private final float b;
    private final float[] value;
    private final int precision;
    private final boolean fill;

    public Ellipse(float x, float y, float a, float b, float[] value, int precision, boolean fill) {
        this.x = x;
        this.y = y;
        this.a = a;
        this.b = b;
        this.value = value;
        this.precision = precision;
        this.fill = fill;
    }

    @Override
    public void apply(ImageData data, Random random) {
        if (fill) {
            for (int x = 0; x < data.getWidth(); x++) {
                for (int y = 0; y < data.getHeight(); y++) {
                    float theta = (float) Math.atan2(b * (y - this.y), a * (x - this.x));
                    double distance = Math.pow((x - this.x) * Math.cos(theta) + (y - this.y) * Math.sin(theta), 2) / Math.pow(a, 2) + Math.pow((x - this.x) * Math.sin(theta) - (y - this.y) * Math.cos(theta), 2) / Math.pow(b, 2);
                    if (distance <= 1) {
                        data.draw(x, y, value);
                    }
                }
            }
        }
        else {
            for (float step = 0; step < precision; step++) {
                float theta1 = (float) ((Math.PI * 2) * (step / precision));
                float x1 = (float) (a * Math.cos(theta1)) + x;
                float y1 = (float) (b * Math.sin(theta1)) + y;
                float nextStep = ((int) step + 1) % precision;
                float theta2 = (float) ((Math.PI * 2) * (nextStep / precision));
                float x2 = (float) (a * Math.cos(theta2)) + x;
                float y2 = (float) (b * Math.sin(theta2)) + y;
                new Line(x1, y1, x2, y2 , value).apply(data, random);
            }
        }
    }
}
