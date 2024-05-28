package org.amoseman.generativeart.filter.color;

import org.amoseman.generativeart.filter.Filter;
import org.amoseman.generativeart.image.ColorValue;
import org.amoseman.generativeart.image.ImageData;

import java.util.random.RandomGenerator;

public class Brighten implements Filter {
    private final float strength;

    public Brighten(float strength) {
        this.strength = strength;
    }

    @Override
    public void apply(ImageData data, RandomGenerator random) {
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
