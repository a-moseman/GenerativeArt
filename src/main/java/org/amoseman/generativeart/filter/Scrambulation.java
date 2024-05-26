package org.amoseman.generativeart.filter;

import org.amoseman.generativeart.image.ImageData;

import java.util.Random;

public class Scambulation implements Filter {
    private final Random random;
    private final int sections;

    public Scambulation(Random random, int sections) {
        this.random = random;
        this.sections = sections;
    }


    @Override
    public void apply(ImageData data) {
        float[][] out = new float[data.getSize()][3];
        for (int y = 0; y < data.getHeight(); y += sections) {
            double r = random.nextDouble();
            r *= 0.5;
            int offset = (int) (data.getWidth() * r);
            for (int dy = 0; dy < sections && y + dy < data.getHeight(); dy++) {
                for (int x = 0; x < data.getWidth(); x++) {
                    int x2 = (x + offset) % data.getWidth();
                    out[x2 + (y + dy) * data.getWidth()] = data.get(x, y + dy);
                }
            }

        }
        for (int i = 0; i < data.getSize(); i++) {
            data.set(i, out[i]);
        }
    }

    private int wrap(int x, int max) {
        return x % max;
    }
}
