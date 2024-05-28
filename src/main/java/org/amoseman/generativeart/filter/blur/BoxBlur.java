package org.amoseman.generativeart.filter.blur;

import org.amoseman.generativeart.image.ColorValue;
import org.amoseman.generativeart.filter.Filter;
import org.amoseman.generativeart.image.ImageData;

import java.util.random.RandomGenerator;

public class BoxBlur implements Filter {
    private final int kernel;

    public BoxBlur(int kernel) {
        if (kernel <= 1) {
            throw new RuntimeException("Box blur kernel must be greater than 0");
        }
        if (kernel % 2 == 0) {
            throw new RuntimeException("Box blur kernel must be an odd value");
        }
        this.kernel = kernel;
    }

    @Override
    public void apply(ImageData data, RandomGenerator random) {
        int min = -kernel / 2;
        int max = kernel / 2;

        for (int x = 0; x < data.getWidth(); x++) {
            for (int y = 0; y < data.getHeight(); y++) {
                float[] value = new float[4];
                int t = 0;
                for (int dx = min; dx <= max; dx++) {
                    for (int dy = min; dy <= max; dy++) {
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
