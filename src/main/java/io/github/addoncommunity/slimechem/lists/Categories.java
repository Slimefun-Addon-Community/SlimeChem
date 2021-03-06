package io.github.addoncommunity.slimechem.lists;


import io.github.addoncommunity.slimechem.SlimeChem;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;

public final class Categories {
    
    private static final SlimeChem instance = SlimeChem.getInstance();
    
    public static final Category MACHINES = new Category(new NamespacedKey(instance, "machines"), new CustomItem(Material.DIAMOND_BLOCK, "Chemical Machines"), 3);
    public static final Category MOLECULES = new Category(new NamespacedKey(instance, "molecules"), new CustomItem(Material.DIAMOND, "Molecules"), 3);
    public static final Category ELEMENTS = new Category(new NamespacedKey(instance, "elements"), new CustomItem(Material.DIAMOND, "Elements"), 3);
    public static final Category ISOTOPES = new Category(new NamespacedKey(instance, "isotopes"), new CustomItem(Material.DIAMOND, "Isotopes"), 3);
    public static final Category SUBATOMIC = new Category(new NamespacedKey(instance, "subatomic"), new CustomItem(Material.DIAMOND, "Subatomic Particles"), 3);

}
