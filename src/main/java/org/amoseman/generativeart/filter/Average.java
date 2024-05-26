package org.amoseman.generativeart.filter;

import org.amoseman.generativeart.image.ImageData;

import java.util.Random;

public class Average implements Filter {
    private ImageData secondary;
    private float ratio;

    public Average(ImageData secondary, float ratio) {
        this.secondary = secondary;
        this.ratio = ratio;
    }

    @Override
    public void apply(ImageData data, Random random) {
        if (data.getSize() != secondary.getSize()) {
            throw new RuntimeException("Images must be same size");
        }
        for (int i = 0; i < data.getSize(); i++) {
            float[] a = data.get(i);
            float[] b = secondary.get(i);
            float[] c = new float[]{
                    a[0] * ratio + b[0] * (1 - ratio),
                    a[1] * ratio + b[1] * (1 - ratio),
                    a[2] * ratio + b[2] * (1 - ratio),
                    a[3] * ratio + b[3] * (1 - ratio)
            };
            data.set(i, c);
        }
    }
}
