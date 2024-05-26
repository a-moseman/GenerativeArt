package org.amoseman.generativeart.filter;

import org.amoseman.generativeart.image.ImageData;

import java.util.Random;

public class Blur implements Filter {
    @Override
    public void apply(ImageData data, Random random) {
        for (int x = 0; x < data.getWidth(); x++) {
            for (int y = 0; y < data.getHeight(); y++) {
                float[] value = new float[4];
                for (int dx = -1; dx < 2; dx++) {
                    for (int dy = -1; dy < 2; dy++) {
                        int x2 = x + dx;
                        int y2 = y + dy;
                        if (x2 < 0 || x2 >= data.getWidth() || y2 < 0 || y2 >= data.getHeight()) {
                            continue;
                        }
                        float[] b = data.get(x2, y2);
                        value[0] += b[0] / 9;
                        value[1] += b[1] / 9;
                        value[2] += b[2] / 9;
                        value[3] += b[3] / 9;
                    }
                }
                data.set(x, y, value);
            }
        }
    }
}
