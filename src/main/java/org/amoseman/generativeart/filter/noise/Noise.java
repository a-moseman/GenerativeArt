package org.amoseman.generativeart.filter;

import org.amoseman.generativeart.ColorMath;
import org.amoseman.generativeart.image.ImageData;

import java.util.Random;

public class Noise implements Filter {
    private final float[][] palette;

    public Noise(float[]... palette) {
        this.palette = palette;
    }

    public Noise() {
        this.palette = null;
    }

    @Override
    public void apply(ImageData data, Random random) {
        for (int i = 0; i < data.getSize(); i++) {
            float[] a = data.get(i);
            float[] b = randomColor(random);
            float[] c = ColorMath.add(a, b);
            data.set(i, c);
        }
    }

    private float[] randomColor(Random random) {
        if (null == palette || 0 == palette.length) {
            return new float[]{
                    random.nextFloat(),
                    random.nextFloat(),
                    random.nextFloat(),
                    1
            };
        }
        return palette[random.nextInt(palette.length)];
    }
}
