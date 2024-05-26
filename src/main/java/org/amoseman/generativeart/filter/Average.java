package org.amoseman.generativeart.filter;

import org.amoseman.generativeart.ColorValue;
import org.amoseman.generativeart.image.ImageData;

import java.util.Random;

public class Average implements Filter {
    private ImageData secondary;
    private float ratio;

    public Average(ImageData secondary, float ratio) {
        this.secondary = secondary;
        this.ratio = ratio;
    }

    @Override
    public void apply(ImageData data, Random random) {
        if (data.getSize() != secondary.getSize()) {
            throw new RuntimeException("Images must be same size");
        }
        for (int i = 0; i < data.getSize(); i++) {
            ColorValue a = data.get(i);
            ColorValue b = secondary.get(i);
            ColorValue c = new ColorValue(
                    a.getRGB() * ratio + b.getRed() * (1 - ratio),
                    a.getGreen() * ratio + b.getGreen() * (1 - ratio),
                    a.getBlue() * ratio + b.getBlue() * (1 - ratio),
                    a.getAlpha() * ratio + b.getAlpha() * (1 - ratio)
            );
            data.set(i, c);
        }
    }
}
