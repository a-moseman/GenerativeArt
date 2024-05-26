package org.amoseman;

import org.amoseman.filter.Filter;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageData {
    private final int width;
    private final int height;
    private final int size;
    private final float[][] pixels;

    public ImageData(int width, int height) {
        this.width = width;
        this.height = height;
        this.size = width * height;
        this.pixels = new float[size][3];
    }

    public ImageData(int width, int height, float[][] pixels) {
        this.width = width;
        this.height = height;
        this.size = width * height;
        if (size != pixels.length) {
            throw new RuntimeException("The number of pixels must equal the product of the provided width and height");
        }
        this.pixels = pixels;
    }

    public ImageData filter(Filter filter) {
        filter.apply(this);
        return this;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getSize() {
        return size;
    }

    public float[] get(int x, int y) {
        return pixels[x + y * width];
    }

    public float[] get(int index) {
        return pixels[index];
    }

    public void set(int x, int y, float[] value) {
        pixels[x + y * width] = value;
    }

    public void set(int index, float[] value) {
        pixels[index] = value;
    }

    public BufferedImage asImage() {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                float[] value = get(x, y);
                image.setRGB(x, y, new Color(value[0], value[1], value[2]).getRGB());

            }
        }
        return image;
    }
}
