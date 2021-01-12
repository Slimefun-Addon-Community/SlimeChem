package io.github.mooy1.slimechem.utils;

import io.github.mooy1.slimechem.SlimeChem;
import lombok.Data;
import org.apache.commons.lang.WordUtils;

import javax.annotation.Nonnull;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public final class StringUtil {

    private StringUtil() {}

    @Data
    public static class NumberAndString {
        private final int number;
        private final String string;
    }

    @Nonnull
    public static String enumNameToTitleCaseString(@Nonnull String enumName) {
        return WordUtils.capitalizeFully(enumName.replace('_', ' '));
    }

    @Nonnull
    public static String getResourceAsString(@Nonnull String resource) throws IOException {
        InputStream stream = SlimeChem.class.getResourceAsStream("/" + resource);
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = stream.read(buffer)) != -1) {
            result.write(buffer, 0, length);
        }
        return result.toString(StandardCharsets.UTF_8.name());
    }

    public static NumberAndString splitString(String s) {
        StringBuilder number = new StringBuilder();
        StringBuilder rest = new StringBuilder();

        for (char character : s.toCharArray()) {
            if (Character.isDigit(character)) {
                number.append(character);
            } else {
                rest.append(character);
            }
        }

        return new NumberAndString(Integer.parseInt(number.toString()), rest.toString());
    }
}
