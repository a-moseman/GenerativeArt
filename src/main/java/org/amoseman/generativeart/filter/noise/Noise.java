package org.amoseman.generativeart.filter.noise;

import org.amoseman.generativeart.ColorValue;
import org.amoseman.generativeart.filter.Filter;
import org.amoseman.generativeart.image.ImageData;

import java.util.Random;

public class Noise implements Filter {
    private final ColorValue[] palette;

    public Noise(ColorValue... palette) {
        this.palette = palette;
    }

    public Noise() {
        this.palette = null;
    }

    @Override
    public void apply(ImageData data, Random random) {
        for (int i = 0; i < data.getSize(); i++) {
            ColorValue a = data.get(i);
            ColorValue b = randomColor(random);
            ColorValue c = a.add(b);
            data.set(i, c);
        }
    }

    private ColorValue randomColor(Random random) {
        if (null == palette || 0 == palette.length) {
            return new ColorValue(
                    random.nextFloat(),
                    random.nextFloat(),
                    random.nextFloat(),
                    1
            );
        }
        return palette[random.nextInt(palette.length)];
    }
}
