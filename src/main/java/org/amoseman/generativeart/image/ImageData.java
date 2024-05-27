package org.amoseman.generativeart.image;

import org.amoseman.generativeart.ColorValue;
import org.amoseman.generativeart.filter.Filter;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Represents the color data of an image.
 */
public class ImageData {
    private final int width;
    private final int height;
    private final int size;
    private final ColorValue[] pixels;

    /**
     * Instantiate an image data.
     * @param width the width of the image data.
     * @param height the height of the image data.
     */
    public ImageData(int width, int height) {
        this.width = width;
        this.height = height;
        this.size = width * height;
        this.pixels = new ColorValue[size];
        for (int i = 0; i < size; i++) {
            pixels[i] = ColorValue.BLACK;
        }
    }

    /**
     * Instantiate an image data.
     * @param width the width of the image data.
     * @param height the height of the image data.
     * @param pixels the data to use.
     */
    public ImageData(int width, int height, ColorValue[] pixels) {
        this.width = width;
        this.height = height;
        this.size = width * height;
        if (size != pixels.length) {
            throw new RuntimeException("The number of pixels must equal the product of the provided width and height");
        }
        this.pixels = pixels;
    }

    /**
     * Instantiate an image data.
     * @param image the image to pull data from.
     */
    public ImageData(BufferedImage image) {
        this.width = image.getWidth();
        this.height = image.getHeight();
        this.size = width * height;
        this.pixels = new ColorValue[size];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                float[] rgb = new float[4];
                rgb = new Color(image.getRGB(x, y)).getColorComponents(rgb);
                pixels[x + y * width] =  new ColorValue(rgb[0], rgb[1], rgb[2], rgb[3]);
            }
        }
    }

    public void filter(Random random, Filter filter) {
        filter.apply(this, random);
    }

    public void filter(Random random, int cycles, Filter... filters) {
        for (int i = 0; i < cycles; i++) {
            for (Filter filter : filters) {
                filter(random, filter);
            }
        }
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

    public ColorValue get(int x, int y) {
        return pixels[x + y * width];
    }

    public ColorValue get(int index) {
        return pixels[index];
    }

    public void set(int x, int y, ColorValue value) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            // don't draw outside image
            return;
        }
        set(x + y * width, value);
    }

    public void set(int index, ColorValue value) {
        pixels[index] = value;
    }

    public void draw(int x, int y, ColorValue value) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            // don't draw outside image
            return;
        }
        ColorValue current = get(x, y);
        set(x, y, current.add(value));
    }

    public void draw(int index, ColorValue value) {
        ColorValue current = get(index);
        set(index, current.add(value));
    }

    /**
     * Convert the image data into an image.
     * @return the image.
     */
    public BufferedImage asImage() {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                ColorValue value = get(x, y);
                image.setRGB(x, y, value.getARGB());
            }
        }
        return image;
    }
}
