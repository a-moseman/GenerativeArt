package org.amoseman.generativeart.filter;

import org.amoseman.generativeart.image.ColorValue;
import org.amoseman.generativeart.image.ImageData;

import java.util.random.RandomGenerator;

public class Degranulate implements Filter {
    private int granularity;

    public Degranulate(int granularity) {
        this.granularity = granularity;
    }

    @Override
    public void apply(ImageData data, RandomGenerator random) {
        int g = granularity - 1;
        for (int i = 0; i < data.getSize(); i++) {
            ColorValue current = data.get(i);
            ColorValue value = new ColorValue(
                    (float) Math.round(current.getRed() * g) / g,
                    (float) Math.round(current.getGreen() * g) / g,
                    (float) Math.round(current.getBlue() * g) / g,
                    1
            );
            data.set(i, value);
        }
    }
}
