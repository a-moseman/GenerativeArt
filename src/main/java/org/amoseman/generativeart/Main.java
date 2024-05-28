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
        Random random = new Random(seed);

        Piece piece = new Piece((int) width, (int) height, seed);
        piece
                .addFilter(new Noise(
                        ColorValue.BLUE,
                        ColorValue.WHITE
                ))
                .addFilter(new BoxBlur(3))
                .addFilter(new Degranulate(4));

        for (int i = 0; i < 80; i++) {
            piece.addFilter(new Rescale(2));
            piece.addFilter(new Grow(1_000_000));
        }
        piece.addFilter(new Scrambulation(8, 0.015, Direction.HORIZONTAL));

        for (int i = 1; i < 13; i++) {
            float s = 64f / i;
            float y = height / i - s * 3;
            piece.addFilter(new Rectangle(0, y, width, s, 0, new ColorValue(.804f, .902f, 1f), true));
        }

        piece.addFilter(new GaussianBlur(3));

        piece.addFilter(new Ellipse(width / 2, height / 2, 320, 320, new ColorValue(.804f, .902f, 1f), 100, true));
        piece.addFilter(new Ellipse(width / 2, height / 2, 320 - 8, 320 - 8, new ColorValue(0, .4f, .804f), 100, true));
        for (int i = 1; i < 7; i++) {
            piece.addFilter(new Ellipse(width / 2, height / 2, 320, 320 - (i * (320 / 7)), new ColorValue(.804f, .902f, 1f), 100, false, 3));
            piece.addFilter(new Ellipse(width / 2, height / 2, 320 - (i * (320 / 7)), 320, new ColorValue(.804f, .902f, 1f), 100, false, 3));
        }

        LocalDateTime now = LocalDateTime.now();
        String signature = String.format(SIGNATURE_FORMAT, AUTHOR, now.format(DateTimeFormatter.ISO_LOCAL_DATE));
        piece.addFilter(new Text(64, height - 64, signature, new ColorValue(0, 0, 0, 0.66f), new Font("Monospaced", Font.PLAIN, 24)));
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
