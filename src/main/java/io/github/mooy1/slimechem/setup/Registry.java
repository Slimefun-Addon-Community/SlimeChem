package io.github.mooy1.slimechem.setup;

import com.google.common.collect.Maps;
import io.github.mooy1.slimechem.SlimeChem;
import io.github.mooy1.slimechem.implementation.atomic.Element;
import io.github.mooy1.slimechem.implementation.atomic.Ingredient;
import io.github.mooy1.slimechem.implementation.atomic.IngredientItem;
import io.github.mooy1.slimechem.implementation.atomic.Isotope;
import io.github.mooy1.slimechem.implementation.atomic.Molecule;
import io.github.mooy1.slimechem.implementation.machines.ChemicalCombiner;
import io.github.mooy1.slimechem.implementation.machines.ChemicalDissolver;
import io.github.mooy1.slimechem.implementation.machines.NuclearFurnace;
import io.github.mooy1.slimechem.implementation.machines.RTG1;
import io.github.mooy1.slimechem.implementation.machines.RTG2;
import io.github.mooy1.slimechem.lists.Categories;
import io.github.mooy1.slimechem.lists.Items;
import io.github.mooy1.slimechem.lists.RecipeTypes;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

public final class Registry {

    public static final Map<SlimefunItem, Ingredient> items = Maps.newHashMapWithExpectedSize(Element.values().length + Isotope.values().length + Molecule.values().length);
    public static final Set<Ingredient> radioactiveItems = new HashSet<>();
    
    public static void setup(SlimeChem plugin) {

        new ElementCategory(plugin).register();

        new SlimefunItem(Categories.MACHINES, Items.ADDON_INFO, RecipeType.NULL, null);

        for (Element element : Element.values()) {
            if (element.getNumber() > 82) {
                radioactiveItems.add(element);
            }
            SlimefunItem slimefunItem = new IngredientItem(Categories.ELEMENTS, element, RecipeType.NULL, null); //should later be changed to proton+neutron+electron recipe
            slimefunItem.register(plugin);
            items.put(slimefunItem, element);
        }
        radioactiveItems.add(Element.TECHNETIUM);
        radioactiveItems.add(Element.PROMETHIUM);
        
        plugin.getLogger().log(Level.INFO, "Registered " + Element.values().length + " Elements!");

        for (Isotope isotope : Isotope.values()) {
            if (isotope.isRadioactive()) {
                radioactiveItems.add(isotope);
            }
            SlimefunItem slimefunItem = new IngredientItem(Categories.ELEMENTS, isotope, RecipeTypes.RTG, null); //show what it decays from
            slimefunItem.register(plugin);
            items.put(slimefunItem, isotope);
        }
        
        plugin.getLogger().log(Level.INFO, "Registered " + Isotope.values().length + " Isotopes!");

        for (Molecule molecule : Molecule.values()) {
            SlimefunItem slimefunItem = new IngredientItem(Categories.MOLECULES, molecule, RecipeTypes.COMBINER, molecule.getNewRecipe());
            slimefunItem.register(plugin);
            items.put(slimefunItem, molecule);
        }
        
        plugin.getLogger().log(Level.INFO, "Registered " + Molecule.values().length + " Molecules!");

        new ChemicalDissolver().register(plugin);
        new ChemicalCombiner().register(plugin);
        new NuclearFurnace().register(plugin);
        new RTG1().setCapacity(32).setEnergyProduction(8).register(plugin);
        new RTG2().setCapacity(64).setEnergyProduction(16).register(plugin);

    }
    
}
