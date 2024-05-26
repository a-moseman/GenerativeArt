package org.amoseman.generativeart;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ColorValueTest {

    @Test
    void getRGB() {
        ColorValue a = new ColorValue(0.4f, 1f, 0.2f, 1f);
        int rgba = a.getRGB();
        ColorValue b = new ColorValue(rgba);
        assertEquals(a.getRed(), b.getRed());
        assertEquals(a.getGreen(), b.getGreen());
        assertEquals(a.getBlue(), b.getBlue());
        assertEquals(a.getAlpha(), b.getAlpha());
    }
}