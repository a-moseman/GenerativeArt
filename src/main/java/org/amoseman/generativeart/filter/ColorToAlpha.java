package org.amoseman.generativeart.filter;

import org.amoseman.generativeart.ColorMath;
import org.amoseman.generativeart.ColorValue;
import org.amoseman.generativeart.image.ImageData;

import java.util.Random;

public class ColorToAlpha implements Filter {
    private final ColorValue color;
    private final double threshhold;

    public ColorToAlpha(ColorValue color, double threshold) {
        this.color = color;
        this.threshhold = threshold;
    }

    @Override
    public void apply(ImageData data, Random random) {
        for (int i = 0; i < data.getSize(); i++) {
            ColorValue a = data.get(i);
            if (a.distance(color) < threshhold) {
                data.set(i, ColorValue.TRANSPARENT);
            }
        }
    }
}
