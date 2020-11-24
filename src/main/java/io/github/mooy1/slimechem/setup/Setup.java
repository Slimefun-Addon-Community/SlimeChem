package io.github.mooy1.slimechem.setup;

import io.github.mooy1.slimechem.SlimeChem;
import io.github.mooy1.slimechem.implementation.Element;
import io.github.mooy1.slimechem.implementation.Isotope;
import io.github.mooy1.slimechem.implementation.Molecule;
import io.github.mooy1.slimechem.lists.Categories;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;

import java.util.logging.Level;

public final class Setup {
    
    public static void setup(SlimeChem plugin) {
        
        new ElementCategory(plugin).register();
        
        for (Element element : Element.values()) {
            SlimefunItem slimefunItem = new SlimefunItem(Categories.ELEMENTS, element.getItem(), RecipeType.ENHANCED_CRAFTING_TABLE, null);
            slimefunItem.register(plugin);
            Element.ITEMS.put(element, slimefunItem);
        }
        plugin.getLogger().log(Level.INFO, "Registered " + Element.values().length + " Elements!");
    
        for (Isotope isotope : Isotope.values()) {
            SlimefunItem slimefunItem = new SlimefunItem(Categories.ELEMENTS, isotope.getItem(), RecipeType.ENHANCED_CRAFTING_TABLE, null);
            slimefunItem.register(plugin);
            Isotope.ITEMS.put(isotope, slimefunItem);
        }
        plugin.getLogger().log(Level.INFO, "Registered " + Isotope.values().length + " Isotopes!");
    
        for (Molecule molecule : Molecule.values()) {
            if (molecule.isReal()) {
                SlimefunItem slimefunItem = new SlimefunItem(Categories.MOLECULES, molecule.getItem(), RecipeType.ENHANCED_CRAFTING_TABLE, null);
                slimefunItem.register(plugin);
                Molecule.ITEMS.put(molecule, slimefunItem);
            }
        }
        plugin.getLogger().log(Level.INFO, "Registered " + Molecule.values().length + " Molecules!");
        
    }
    
}
