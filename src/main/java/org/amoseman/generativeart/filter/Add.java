package org.amoseman.generativeart.filter;

import org.amoseman.generativeart.image.ImageData;

import java.util.random.RandomGenerator;

public class Add implements Filter {
    private final ImageData foreground;

    public Add(ImageData foreground) {
        this.foreground = foreground;
    }

    @Override
    public void apply(ImageData data, RandomGenerator random) {
        if (data.getSize() != foreground.getSize() || data.getWidth() != foreground.getWidth() || data.getHeight() != foreground.getHeight()) {
            throw new RuntimeException("Data must be of the same size and shape");
        }
        for (int i = 0; i < data.getSize(); i++) {
            data.draw(i, foreground.get(i));
        }
    }
}