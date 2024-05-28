package org.amoseman.generativeart.filter;

import org.amoseman.generativeart.ColorValue;
import org.amoseman.generativeart.image.ImageData;
import org.amoseman.generativeart.vector.Vector;

import java.util.Random;

public class Grow implements Filter {
    private final int n;
    private final double ignoreThreshold;
    private final ColorValue[] ignore;

    public Grow(int n, double ignoreThreshold, ColorValue... ignore) {
        this.n = n;
        this.ignoreThreshold = ignoreThreshold;
        this.ignore = ignore;
    }

    public Grow(int n, ColorValue... ignore) {
        this.n = n;
        this.ignoreThreshold = 0;
        this.ignore = ignore;
    }

    @Override
    public void apply(ImageData data, Random random) {
        int i = 0;
        while (i < n) {
            int x = random.nextInt(data.getWidth());
            int y = random.nextInt(data.getHeight());
            boolean flag = false;
            for (ColorValue ign : ignore) {
                if (data.get(x, y).distance(ign) < ignoreThreshold) {
                    flag = true;
                    break;
                }
            }
            if (flag) {
                continue;
            }

            Vector growth = randomGrowthPosition(data, random, new Vector(x, y));
            ColorValue v = data.get(x, y);
            data.set((int) growth.x, (int) growth.y, v);
            i++;
        }
    }

    private Vector randomGrowthPosition(ImageData data, Random random, Vector origin) {
        Vector growth;
        do {
            growth = origin.add(randomShift(random));
        } while (growth.x < 0 || growth.x >= data.getWidth() || growth.y < 0 || growth.y >= data.getHeight());
        return growth;
    }

    private Vector randomShift(Random random) {
        return switch (random.nextInt(4)) {
            case 0 -> Vector.UP;
            case 1 -> Vector.DOWN;
            case 2 -> Vector.RIGHT;
            case 3 -> Vector.LEFT;
            default -> throw new IllegalStateException("Unexpected value: " + random.nextInt(4));
        };
    }
}
