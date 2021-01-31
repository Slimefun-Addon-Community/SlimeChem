package io.github.addoncommunity.slimechem.utils;

import javax.annotation.Nonnull;

/**
 * Subscript numbers and getter
 * 
 * @author TheBusyBiscuit
 * @author Mooy1
 * TODO: make better 
 */
public final class SubNum {

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
        if (i == 1) return "";
        
        StringBuilder builder = new StringBuilder();
        
        for (char c : String.valueOf(i).toCharArray()) {
            switch (c) {
                case '0': builder.append(ZERO); break;
                case '1': builder.append(ONE); break;
                case '2': builder.append(TWO); break;
                case '3': builder.append(THREE); break;
                case '4': builder.append(FOUR); break;
                case '5': builder.append(FIVE); break;
                case '6': builder.append(SIX); break;
                case '7': builder.append(SEVEN); break;
                case '8': builder.append(EIGHT); break;
                case '9': builder.append(NINE); break;
            }
        }
        
        return builder.toString();
    }
}
