package org.amoseman.generativeart.filter;

import org.amoseman.generativeart.image.ColorValue;
import org.amoseman.generativeart.image.ImageData;

import java.util.random.RandomGenerator;

public class Scrambulation implements Filter {
    private final int sections;
    private final double strength;
    private final Direction direction;

    public Scrambulation(int sections, double strength, Direction direction) {
        this.sections = sections;
        this.strength = strength;
        this.direction = direction;
    }


    @Override
    public void apply(ImageData data, RandomGenerator random) {
        ColorValue[] out = new ColorValue[data.getSize()];
        switch (direction) {
            case HORIZONTAL:
                for (int y = 0; y < data.getHeight(); y += sections) {
                    double r = random.nextDouble() - 0.5;
                    r *= strength;
                    int offset = (int) (data.getWidth() * r);
                    for (int dy = 0; dy < sections && y + dy < data.getHeight(); dy++) {
                        for (int x = 0; x < data.getWidth(); x++) {
                            int x2 = wrap(x + offset, data.getWidth());
                            out[x2 + (y + dy) * data.getWidth()] = data.get(x, y + dy);
                        }
                    }
                }
                break;
            case VERTICAL:
                for (int x = 0; x < data.getHeight(); x += sections) {
                    double r = random.nextDouble() - 0.5;
                    r *= strength;
                    int offset = (int) (data.getHeight() * r);
                    for (int dx = 0; dx < sections && x + dx < data.getWidth(); dx++) {
                        for (int y = 0; y < data.getHeight(); y++) {
                            int y2 = wrap(y + offset, data.getHeight());
                            out[(x + dx) + y2 * data.getWidth()] = data.get(x + dx, y);
                        }
                    }
                }
        }

        for (int i = 0; i < data.getSize(); i++) {
            data.set(i, out[i]);
        }
    }

    private int wrap(int x, int max) {
        if (x >= max) {
            return x - max;
        }
        if (x < 0) {
            return max + x;
        }
        return x;
    }
}
