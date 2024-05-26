package org.amoseman.generativeart.filter;

import org.amoseman.generativeart.ColorMath;
import org.amoseman.generativeart.image.ImageData;

import java.util.Random;

public class ColorToAlpha implements Filter {
    private final float[] color;
    private final double threshhold;

    public ColorToAlpha(float[] color, double threshold) {
        this.color = color;
        this.threshhold = threshold;
    }

    @Override
    public void apply(ImageData data, Random random) {
        for (int i = 0; i < data.getSize(); i++) {
            float[] a = data.get(i);
            if (ColorMath.distance(a, color) < threshhold) {
                data.set(i, new float[]{0, 0, 0, 0});
            }
        }
    }
}
