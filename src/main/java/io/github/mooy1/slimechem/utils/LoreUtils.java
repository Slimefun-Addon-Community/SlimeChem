package io.github.mooy1.slimechem.utils;

import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;

import java.text.DecimalFormat;

/**
 * Collection of utils for building item lore
 *
 * @author Mooy1
 */
public final class LoreUtils {
    
    public static final float SERVER_TICK_RATIO = (float) 20 / SlimefunPlugin.getCfg().getInt("URID.custom-ticker-delay");
    
    public static String energyPerSecond(int energy) {
        return "&8\u21E8 &e\u26A1 &7" + format(Math.round(energy * SERVER_TICK_RATIO)) + " J/s";
    }
    public static String energyBuffer(int energy) {
        return "&8\u21E8 &e\u26A1 &7" + format(energy) + " J Buffer";
    }

    public static String energy(int energy) {
        return "&8\u21E8 &e\u26A1 &7" + format(energy) + " J ";
    }

    public static String speed(int speed) {
        return "&8\u21E8 &b\u26A1 &7Speed: &b" + speed + 'x';
    }
    
    public static String roundHundreds(float number) {
        return format(Math.round(number * 100) / 100);
    }

    private static final DecimalFormat decimalFormat = new DecimalFormat("###,###,###,###");

    public static String format(int number) {
        return decimalFormat.format(number);
    }
    
}
