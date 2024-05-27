package org.amoseman.generativeart;

import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class ColorValueTest {

    @Test
    void getRGB() {
        ColorValue a = new ColorValue(0.4f, 1f, 0.2f, 1f);
        int rgba = a.getARGB();
        ColorValue b = new ColorValue(new Color(rgba));
        assertEquals(a.getRed(), b.getRed());
        assertEquals(a.getGreen(), b.getGreen());
        assertEquals(a.getBlue(), b.getBlue());
        assertEquals(a.getAlpha(), b.getAlpha());
        int rgba2 = b.getARGB();
        assertEquals(rgba, rgba2);
    }
}