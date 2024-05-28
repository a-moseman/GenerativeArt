package org.amoseman.generativeart.filter;

import org.amoseman.generativeart.image.ColorValue;
import org.amoseman.generativeart.image.ImageData;

import java.util.random.RandomGenerator;

public class RemoveTransparency implements Filter {
    @Override
    public void apply(ImageData data, RandomGenerator random) {
        for (int i = 0; i < data.getSize(); i++) {
            ColorValue value = data.get(i);
            data.set(i, new ColorValue(value.getRed(), value.getGreen(), value.getBlue(), 1));
        }
    }
}
