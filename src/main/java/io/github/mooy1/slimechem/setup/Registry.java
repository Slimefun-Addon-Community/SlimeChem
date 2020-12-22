package io.github.mooy1.slimechem.setup;

import com.google.common.collect.Maps;
import io.github.mooy1.slimechem.SlimeChem;
import io.github.mooy1.slimechem.implementation.MachineMaterial;
import io.github.mooy1.slimechem.implementation.atomic.Element;
import io.github.mooy1.slimechem.implementation.atomic.Ingredient;
import io.github.mooy1.slimechem.implementation.atomic.IngredientItem;
import io.github.mooy1.slimechem.implementation.atomic.Isotope;
import io.github.mooy1.slimechem.implementation.atomic.Molecule;
import io.github.mooy1.slimechem.implementation.generators.RTG1;
import io.github.mooy1.slimechem.implementation.generators.RTG2;
import io.github.mooy1.slimechem.implementation.machines.ChemicalCombiner;
import io.github.mooy1.slimechem.implementation.machines.ChemicalDissolver;
import io.github.mooy1.slimechem.implementation.machines.NuclearFurnace;
import io.github.mooy1.slimechem.implementation.machines.RTG;
import io.github.mooy1.slimechem.implementation.subatomic.Boson;
import io.github.mooy1.slimechem.implementation.subatomic.Lepton;
import io.github.mooy1.slimechem.implementation.subatomic.Nucleon;
import io.github.mooy1.slimechem.lists.Categories;
import io.github.mooy1.slimechem.lists.Items;
import io.github.mooy1.slimechem.lists.RecipeTypes;
import lombok.Getter;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

@Getter
public final class Registry {

    private final int registrySize = Element.values().length + Isotope.values().length + Molecule.values().length;
    
    private final Map<SlimefunItem, Ingredient> items = Maps.newHashMapWithExpectedSize(this.registrySize);
    private final Map<String, Ingredient> ids = Maps.newHashMapWithExpectedSize(this.registrySize);

    private final Set<Ingredient> radioactiveItems = new HashSet<>((Element.values().length / 3) + Isotope.values().length);

    private final SlimeChem plugin;

    public Registry(SlimeChem plugin) {
        this.plugin = plugin;

        new ElementCategory(plugin).register();

        new SlimefunItem(Categories.MACHINES, Items.ADDON_INFO, RecipeType.NULL, null);

        for (Element element : Element.values()) {
            if (element.getNumber() > 82) {
                this.radioactiveItems.add(element);
            }
            registerItem(new IngredientItem(Categories.ELEMENTS, element, RecipeType.NULL, null)); //should later be changed to proton+neutron+electron recipe in fusion
        }
        this.radioactiveItems.add(Element.TECHNETIUM);
        this.radioactiveItems.add(Element.PROMETHIUM);
        
        plugin.getLogger().log(Level.INFO, "Registered " + Element.values().length + " Elements!");

        for (Isotope isotope : Isotope.values()) {
            if (isotope.isRadioactive()) {
                this.radioactiveItems.add(isotope);
            }
            registerItem(new IngredientItem(Categories.ELEMENTS, isotope, RecipeTypes.RTG, null)); //show what it decays from
        }
        
        plugin.getLogger().log(Level.INFO, "Registered " + Isotope.values().length + " Isotopes!");

        for (Molecule molecule : Molecule.values()) {
            registerItem(new IngredientItem(Categories.MOLECULES, molecule, RecipeTypes.COMBINER, molecule.getRecipe()));
        }
        
        plugin.getLogger().log(Level.INFO, "Registered " + Molecule.values().length + " Molecules!");

        for (Boson boson : Boson.values()) {
            new SlimefunItem(Categories.SUBATOMIC, boson.getItem(), RecipeType.NULL, new ItemStack[0]);
        }

        for (Lepton lepton : Lepton.values()) {
            new SlimefunItem(Categories.SUBATOMIC, lepton.getItem(), RecipeType.NULL, new ItemStack[0]);
        }

        for (Nucleon nucleon : Nucleon.values()) {
            new SlimefunItem(Categories.SUBATOMIC, nucleon.getItem(), RecipeType.NULL, new ItemStack[0]);
        }

        IngredientItem.setupInteractions();
        
        for (MachineMaterial m : MachineMaterial.values()) {
            new SlimefunItem(Categories.MACHINES, m.getItem(), m.getRecipeType(), m.getRecipe()).register(plugin);
        }

        new ChemicalDissolver(this).register(plugin);
        new ChemicalCombiner().register(plugin);
        new NuclearFurnace().register(plugin);
        
        for (RTG.Type type : RTG.Type.values()) {
            new RTG(type).register(plugin);
        }
        
        new RTG1().setCapacity(32).setEnergyProduction(8).register(plugin);
        new RTG2().setCapacity(64).setEnergyProduction(16).register(plugin);

    }
    
    private void registerItem(@Nonnull IngredientItem item) {
        this.items.put(item, item.getIngredient());
        this.ids.put(item.getId(), item.getIngredient());
        item.register(this.plugin);
    }
    
}
