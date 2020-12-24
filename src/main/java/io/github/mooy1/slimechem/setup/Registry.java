package io.github.mooy1.slimechem.setup;

import com.google.common.collect.Maps;
import io.github.mooy1.slimechem.SlimeChem;
import io.github.mooy1.slimechem.implementation.atomic.Element;
import io.github.mooy1.slimechem.implementation.atomic.IngredientItem;
import io.github.mooy1.slimechem.implementation.atomic.Molecule;
import io.github.mooy1.slimechem.implementation.atomic.isotopes.Isotope;
import io.github.mooy1.slimechem.implementation.attributes.Ingredient;
import io.github.mooy1.slimechem.implementation.generators.RTG1;
import io.github.mooy1.slimechem.implementation.generators.RTG2;
import io.github.mooy1.slimechem.implementation.machines.ChemicalCombiner;
import io.github.mooy1.slimechem.implementation.machines.ChemicalDissolver;
import io.github.mooy1.slimechem.implementation.machines.NuclearFurnace;
import io.github.mooy1.slimechem.implementation.subatomic.Boson;
import io.github.mooy1.slimechem.implementation.subatomic.Lepton;
import io.github.mooy1.slimechem.implementation.subatomic.Nucleon;
import io.github.mooy1.slimechem.lists.Categories;
import io.github.mooy1.slimechem.lists.Items;
import io.github.mooy1.slimechem.lists.RecipeTypes;
import io.github.mooy1.slimechem.utils.Util;
import lombok.Getter;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

@Getter
public final class Registry {

    private final int registrySize = Element.values().length  + Molecule.values().length;
    
    private final Map<SlimefunItem, Ingredient> items = Maps.newHashMapWithExpectedSize(this.registrySize);
    private final Map<String, Ingredient> ids = Maps.newHashMapWithExpectedSize(this.registrySize);

    private static final Set<Ingredient> radioactiveItems = new HashSet<>((Element.values().length / 3));

    private final SlimeChem plugin;

    public Registry(SlimeChem plugin) {
        this.plugin = plugin;

        new ElementCategory(plugin).register();

        new SlimefunItem(Categories.MACHINES, Items.ADDON_INFO, RecipeType.NULL, null);

        for (Element element : Element.values()) {
            if (element.isRadioactive()) {
                radioactiveItems.add(element);
            }
            registerItem(new IngredientItem(Categories.ELEMENTS, element, RecipeType.NULL, null)); //should later be changed to proton+neutron+electron recipe in fusion
        }
        
        plugin.getLogger().log(Level.INFO, "Registered " + Element.values().length + " Elements!");

        final EnumMap<Element, Set<Isotope>> isotopes = Isotope.getIsotopes();
        int isocount = 0;
        for (Element element : isotopes.keySet()) {
            for (Isotope isotope : isotopes.get(element)) {
                isocount++;
                if (isotope.isRadioactive()) {
                    radioactiveItems.add(isotope);
                }
                List<Isotope> superIsotopes = new ArrayList<>();
                for (Element el : isotopes.keySet()) {
                    for (Isotope iso : isotopes.get(el)) {
                        iso.getDecayProduct().ifPresent(superIsotopes::add);
                    }
                }
                Util.trimList(superIsotopes, 9);

                ItemStack[] items;
                if (superIsotopes.size() > 0) {
                    items = new ItemStack[9];
                    for (int i = 0; i < items.length; i++) {
                        ItemStack stack;
                        try {
                            stack = superIsotopes.get(i).getItem();
                        } catch (ArrayIndexOutOfBoundsException e) {
                            break;
                        }
                        items[i] = stack;
                    }
                    if (items[items.length - 1] != null) {
                        items[items.length - 1] = new CustomItem(
                            Material.WHITE_DYE,
                            "&7And more..."
                        );
                    }
                } else {
                    items = null;
                }

                new IngredientItem(Categories.ISOTOPES, isotope, RecipeTypes.RTG, items).register(plugin);
            }
        }

        plugin.getLogger().log(Level.INFO, "Registered " + isocount + " Isotopes!");

        for (Molecule molecule : Molecule.values()) {
            registerItem(new IngredientItem(Categories.MOLECULES, molecule, RecipeTypes.COMBINER, molecule.getNewRecipe()));
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

        new ChemicalDissolver(this).register(plugin);
        new ChemicalCombiner().register(plugin);
        new NuclearFurnace().register(plugin);
        new RTG1().setCapacity(32).setEnergyProduction(8).register(plugin);
        new RTG2().setCapacity(64).setEnergyProduction(16).register(plugin);

    }
    
    private void registerItem(@Nonnull IngredientItem item) {
        this.items.put(item, item.getIngredient());
        this.ids.put(item.getId(), item.getIngredient());
        item.register(this.plugin);
    }

    public static Set<Ingredient> getRadioactiveItems() {
        return radioactiveItems;
    }

}
