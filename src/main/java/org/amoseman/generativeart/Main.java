package org.amoseman.generativeart;

import org.amoseman.generativeart.filter.*;
import org.amoseman.generativeart.filter.blur.BoxBlur;
import org.amoseman.generativeart.filter.blur.GaussianBlur;
import org.amoseman.generativeart.filter.draw.Ellipse;
import org.amoseman.generativeart.filter.draw.Rectangle;
import org.amoseman.generativeart.filter.draw.Text;
import org.amoseman.generativeart.filter.noise.Noise;
import org.amoseman.generativeart.image.ColorValue;
import org.amoseman.generativeart.piece.Piece;

import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Random;

public class Main {

    private static final String SIGNATURE_FORMAT = "%s %s";
    private static final String AUTHOR = "a-moseman";

    public static void main(String[] args) {
        float width = 1920;
        float height = 1080;
        long seed = parseSeed(args).orElseGet(System::currentTimeMillis);
        Piece piece = new Piece((int) width, (int) height, seed);
        ColorValue root = new ColorValue(208f / 255, 47f / 255, 199f / 255);
        ColorValue[] analogous = root.analogousColors();
        piece
                .addFilter(new Noise()
                        .addColor(root, 0.34)
                        .addColor(analogous[0], 0.33)
                        .addColor(analogous[1], 0.33)
                )
                .addFilter(new Grow(512_000_000));


        LocalDateTime now = LocalDateTime.now();
        String signature = String.format(SIGNATURE_FORMAT, AUTHOR, now.format(DateTimeFormatter.ISO_LOCAL_DATE));
        piece.addFilter(new Text(64, height - 64, signature, new ColorValue(1, 1, 1, 0.66f), new Font("Monospaced", Font.BOLD, 24)));
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
