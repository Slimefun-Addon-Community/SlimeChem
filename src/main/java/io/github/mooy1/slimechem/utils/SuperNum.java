package io.github.mooy1.slimechem.utils;

import javax.annotation.Nonnull;

/**
 * Superscript numbers and getter
 * 
 * @author TheBusyBiscuit
 * @author Mooy1
 * 
 */
public final class SuperNum {

    // I tried unicode but it messes up
    public static final char ZERO_STARTING_POINT = '⁰';

    private SuperNum() {}

    @Nonnull
    public static String fromInt(int i) {
        StringBuilder builder = new StringBuilder();
        int num = i;

        while (num > 0) {
            // Use zero char as a base and then add x onto it.
            builder.append((char) (ZERO_STARTING_POINT + (num % 10)));
            num /= 10;
        }

        return builder.reverse().toString();
    }

    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            System.out.println(fromInt(i));
        }
    }
}
