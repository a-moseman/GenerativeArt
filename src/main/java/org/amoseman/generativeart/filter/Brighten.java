package org.amoseman.generativeart.filter;

import org.amoseman.generativeart.image.ColorValue;
import org.amoseman.generativeart.image.ImageData;

import java.util.Random;

public class Brighten implements Filter {
    private final float strength;

    public Brighten(float strength) {
        this.strength = strength;
    }

    @Override
    public void apply(ImageData data, Random random) {
        float d = strength / 3;
        for (int i = 0; i < data.getSize(); i++) {
            ColorValue value = data.get(i);
            data.set(i, new ColorValue(
                    Math.min(1, value.getRed() + d),
                    Math.min(1, value.getGreen() + d),
                    Math.min(1, value.getBlue() + d),
                    value.getAlpha()
            ));
            data.set(i, value);
        }
    }
}
