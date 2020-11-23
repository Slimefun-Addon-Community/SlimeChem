package io.github.mooy1.slimechem.lists;


import io.github.mooy1.slimechem.SlimeChem;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;

public class Categories {
    
    private static final SlimeChem instance = SlimeChem.getInstance();
    
    public static final Category ELEMENTS = new Category(new NamespacedKey(instance, "elements"), new CustomItem(Material.DIAMOND, "test"), 3);
    
}
