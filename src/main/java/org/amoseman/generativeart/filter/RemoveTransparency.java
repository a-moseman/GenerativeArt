package org.amoseman.generativeart.filter;

import org.amoseman.generativeart.ColorValue;
import org.amoseman.generativeart.image.ImageData;

import java.util.Random;

public class RemoveTransparency implements Filter {
    @Override
    public void apply(ImageData data, Random random) {
        for (int i = 0; i < data.getSize(); i++) {
            ColorValue value = data.get(i);
            data.set(i, new ColorValue(value.getRed(), value.getGreen(), value.getBlue(), 1));
        }
    }
}
