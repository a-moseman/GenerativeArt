package org.amoseman.generativeart.filter;

import org.amoseman.generativeart.image.ColorValue;
import org.amoseman.generativeart.image.ImageData;

import java.util.Random;

public class Rescale implements Filter {
    private final int scale;

    public Rescale(int scale) {
        this.scale = scale;
    }

    @Override
    public void apply(ImageData data, Random random) {
        ColorValue[] scaled = new ColorValue[data.getSize()];
        for (int x = 0; x < data.getWidth(); x += scale) {
            for (int y = 0; y < data.getHeight(); y += scale) {
                ColorValue value = data.get(x, y);
                for (int dx = 0; dx < scale; dx++) {
                    for (int dy = 0; dy < scale; dy++) {
                        int px = x + dx;
                        if (px >= data.getWidth()) {
                            continue;
                        }
                        int py = y + dy;
                        if (py >= data.getHeight()) {
                            continue;
                        }
                        scaled[px + py * data.getWidth()] = value;
                    }
                }
            }
        }
        for (int i = 0; i < data.getSize(); i++) {
            data.set(i, scaled[i]);
        }

    }
}
