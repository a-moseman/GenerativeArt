package org.amoseman.generativeart.filter.blur;

import org.amoseman.generativeart.filter.Filter;
import org.amoseman.generativeart.image.ColorValue;
import org.amoseman.generativeart.image.ImageData;

import java.util.random.RandomGenerator;

public class GaussianBlur implements Filter {
    private final int radius;
    private final int kernelWidth;
    private final double standardDeviation;
    private final double[][] kernel;

    public GaussianBlur(int radius) {
        this.radius = radius;
        this.kernelWidth = 2 * radius + 1;
        this.standardDeviation = Math.max((double) radius / 2, 1);
        this.kernel = new double[kernelWidth][kernelWidth];
        double sum = 0;
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                double en = -(x * x + y * y);
                double ed = 2 * standardDeviation * standardDeviation;
                double ee = Math.pow(Math.E, en / ed);
                double kv = ee / (2 * Math.PI * standardDeviation * standardDeviation);
                kernel[x + radius][y + radius] = kv;
                sum += kv;
            }
        }
        for (int x = 0; x < kernelWidth; x++) {
            for (int y = 0; y < kernelWidth; y++) {
                kernel[x][y] /= sum;
            }
        }
    }

    @Override
    public void apply(ImageData data, RandomGenerator random) {
        ColorValue[] blurred = new ColorValue[data.getSize()];
        // avoid null values
        for (int i = 0; i < data.getSize(); i++) {
            blurred[i] = ColorValue.RED;
        }

        for (int x = radius; x < data.getWidth() - radius; x++) {
            for (int y = radius; y < data.getHeight() - radius; y++) {
                ColorValue value = ColorValue.TRANSPARENT;
                for (int dx = -radius; dx <= radius; dx++) {
                    for (int dy = -radius; dy <= radius; dy++) {
                        double kernelValue = kernel[dx + radius][dy + radius];
                        value = value.sum(data.get(x - dx, y - dy).scale(kernelValue));
                    }
                }
                blurred[x + y * data.getWidth()] = value;
            }
        }

        for (int i = 0; i < data.getSize(); i++) {
            data.set(i, blurred[i]);
        }
    }
}
