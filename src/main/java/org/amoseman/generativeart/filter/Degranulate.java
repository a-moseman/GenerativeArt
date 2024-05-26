package org.amoseman.generativeart.filter;

import org.amoseman.generativeart.image.ImageData;

import java.util.Random;

public class Degranulate implements Filter {
    private int granularity;

    public Degranulate(int granularity) {
        this.granularity = granularity;
    }

    @Override
    public void apply(ImageData data, Random random) {
        int g = granularity - 1;
        for (int i = 0; i < data.getSize(); i++) {
            float[] current = data.get(i);
            float[] value = new float[4];
            for (int j = 0; j < 3; j++) {
                float v = (float) Math.round(current[j] * g) / g;
                value[j] = v;
            }
            value[3] = 1;
            data.set(i, value);
        }
    }
}
