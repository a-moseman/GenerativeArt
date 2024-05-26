package org.amoseman.generativeart;

import org.amoseman.generativeart.filter.*;
import org.amoseman.generativeart.filter.Composite;
import org.amoseman.generativeart.filter.draw.Ellipse;
import org.amoseman.generativeart.filter.draw.Text;
import org.amoseman.generativeart.filter.noise.FBMNoise;
import org.amoseman.generativeart.filter.noise.Noise;
import org.amoseman.generativeart.image.ImageData;
import org.amoseman.generativeart.piece.Piece;

import java.awt.*;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        //long seed = System.currentTimeMillis();
        long seed = 1716741432348L;
        Piece piece = new Piece(640, 640, seed);

        piece.addFilter(new Text(20, 620, "amoseman 2024-05-25", new ColorValue(1, 1, 1, 0.5f), new Font("Monospaced", Font.PLAIN, 12)));
        piece.build("png");
    }
}
