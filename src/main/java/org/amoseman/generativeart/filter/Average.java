package org.amoseman.filter;

import org.amoseman.ImageData;

public class Average implements Filter {
    private ImageData secondary;

    public Average(ImageData secondary) {
        this.secondary = secondary;
    }

    @Override
    public void apply(ImageData data) {
        if (data.getSize() != secondary.getSize()) {
            throw new RuntimeException("Images must be same size");
        }
        for (int i = 0; i < data.getSize(); i++) {
            float[] a = data.get(i);
            float[] b = secondary.get(i);
            float[] c = new float[]{
                    (a[0] + b[0]) / 2,
                    (a[1] + b[1]) / 2,
                    (a[2] + b[2]) / 2
            };
            data.set(i, c);
        }
    }
}
