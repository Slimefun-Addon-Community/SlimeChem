package io.github.mooy1.slimechem.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

/**
 * General utility methods
 * 
 * @author Seggan
 * @author Mooy1
 * 
 */
public final class Util {
    
    private static final String PREFIX = ChatColor.AQUA + "SlimeChem " + ChatColor.WHITE; // should improve at some point
    
    /**
     * broadcasts a message with prefix
     */
    public static void broadcast(@Nonnull String message) {
        Bukkit.broadcastMessage(PREFIX + message);
    }
    
    /**
     * sends a player a message with prefix
     */
    public static void message(@Nonnull Player p, @Nonnull String message) {
        p.sendMessage(PREFIX + message);
    }
    
}
