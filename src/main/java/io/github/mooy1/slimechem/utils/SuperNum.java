package io.github.mooy1.slimechem.utils;

import javax.annotation.Nonnull;

/**
 * Superscript numbers and getter
 * 
 * @author Mooy1
 * 
 */
public class SuperNum {
    
    //I tried unicode but it messes up
    public static final String ZERO = "⁰"; 
    public static final String ONE = "¹";
    public static final String TWO = "²";
    public static final String THREE = "³";
    public static final String FOUR = "⁴";
    public static final String FIVE = "⁵";
    public static final String SIX = "⁶";
    public static final String SEVEN = "⁷";
    public static final String EIGHT = "⁸";
    public static final String NINE = "⁹";
    
    
    @Nonnull
    public static String fromInt(int i) {
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
