package org.amoseman.generativeart.filter;

import org.amoseman.generativeart.ColorValue;
import org.amoseman.generativeart.image.ImageData;

import java.util.Random;

public class Blur implements Filter {
    @Override
    public void apply(ImageData data, Random random) {
        for (int x = 0; x < data.getWidth(); x++) {
            for (int y = 0; y < data.getHeight(); y++) {
                float[] value = new float[4];
                int t = 0;
                for (int dx = -1; dx < 2; dx++) {
                    for (int dy = -1; dy < 2; dy++) {
                        int x2 = x + dx;
                        int y2 = y + dy;
                        if (x2 < 0 || x2 >= data.getWidth() || y2 < 0 || y2 >= data.getHeight()) {
                            continue;
                        }
                        t++;
                        ColorValue b = data.get(x2, y2);
                        value[0] += b.getRed();
                        value[1] += b.getGreen();
                        value[2] += b.getBlue();
                        value[3] += b.getAlpha();
                    }
                }
                data.set(x, y, new ColorValue(
                        value[0] / t,
                        value[1] / t,
                        value[2] / t,
                        value[3] / t
                ));
            }
        }
    }
}
