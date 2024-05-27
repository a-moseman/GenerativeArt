package org.amoseman.generativeart.filter;

import org.amoseman.generativeart.ColorValue;
import org.amoseman.generativeart.image.ImageData;

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

            int dx = 0;
            int dy = 0;
            do {
                switch (random.nextInt(4)) {
                    case 0:
                        dx = 1;
                        dy = 0;
                        break;
                    case 1:
                        dx = -1;
                        dy = 0;
                        break;
                    case 2:
                        dx = 0;
                        dy = 1;
                        break;
                    case 3:
                        dx = 0;
                        dy = -1;
                        break;
                }
            } while (x + dx < 0 || x + dx >= data.getWidth() || y + dy < 0 || y + dy >= data.getHeight());
            ColorValue v = data.get(x, y);
            data.set(x + dx, y + dy, v);
            i++;
        }
    }
}
