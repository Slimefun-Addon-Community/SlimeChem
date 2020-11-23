package io.github.mooy1.slimechem.utils;

import javax.annotation.Nonnull;

public class SubNum {

    public static final String ZERO = "\u2080";
    public static final String ONE = "\u2081";
    public static final String TWO = "\u2082";
    public static final String THREE = "\u2083";
    public static final String FOUR = "\u2084";
    public static final String FIVE = "\u2085";
    public static final String SIX = "\u2086";
    public static final String SEVEN = "\u2087";
    public static final String EIGHT = "\u2088";
    public static final String NINE = "\u2089";
    
    @Nonnull
    public static String fromInt(int i) {
        switch (i) {
            case 0: return ZERO;
            case 1: return ONE;
            case 2: return TWO;
            case 3: return THREE;
            case 4: return FOUR;
            case 5: return FIVE;
            case 6: return SIX;
            case 7: return SEVEN;
            case 8: return EIGHT;
            case 9: return NINE;
            default: return "";
        }
    }
}
