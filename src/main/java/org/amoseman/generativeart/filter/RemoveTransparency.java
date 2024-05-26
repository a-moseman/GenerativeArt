package org.amoseman.generativeart.filter;

import org.amoseman.generativeart.image.ImageData;

import java.util.Random;

public class RemoveTransparency implements Filter {
    @Override
    public void apply(ImageData data, Random random) {
        for (int i = 0; i < data.getSize(); i++) {
            float[] value = data.get(i);
            data.set(i, new float[]{value[0], value[1], value[2], 1});
        }
    }
}
