package org.amoseman.generativeart.filter;

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
            float[] value = data.get(i);
            value[0] = Math.min(1, value[0] + d);
            value[1] = Math.min(1, value[1] + d);
            value[2] = Math.min(1, value[2] + d);
            data.set(i, value);
        }
    }
}
