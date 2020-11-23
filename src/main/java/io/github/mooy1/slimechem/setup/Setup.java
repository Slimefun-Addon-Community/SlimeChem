package io.github.mooy1.slimechem.setup;

import io.github.mooy1.slimechem.SlimeChem;
import io.github.mooy1.slimechem.implementation.Element;
import io.github.mooy1.slimechem.implementation.Registry;
import io.github.mooy1.slimechem.lists.Categories;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;

public final class Setup {
    
    public static void setup(SlimeChem plugin, Registry registry) {
        
        new ElementCategory(plugin).register();
        
        for (Element element : Element.values()) {
            SlimefunItem slimefunItem = new SlimefunItem(Categories.ELEMENTS, registry.ELEMENT_STACKS.get(element), RecipeType.ENHANCED_CRAFTING_TABLE, null);
            slimefunItem.register(plugin);
            registry.ELEMENTS.put(slimefunItem, element);
        }
        
    }
    
}
