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
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        long seed;
        Optional<Long> maybe = parseSeed(args);
        seed = maybe.orElseGet(System::currentTimeMillis);
        Piece piece = new Piece(640, 640, seed);

        piece.addFilter(new Text(20, 620, "amoseman 2024-05-25", new ColorValue(1, 1, 1, 0.5f), new Font("Monospaced", Font.PLAIN, 12)));
        piece.build("png");
    }

    private static Optional<Long> parseSeed(String[] args) {
        if (0 == args.length) {
            return Optional.empty();
        }
        try {
            return Optional.of(Long.parseLong(args[0]));
        }
        catch (NumberFormatException e) {
            return Optional.empty();
        }
    }
}
