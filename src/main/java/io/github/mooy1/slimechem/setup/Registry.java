package io.github.mooy1.slimechem.setup;

import com.google.common.collect.Maps;
import io.github.mooy1.slimechem.SlimeChem;
import io.github.mooy1.slimechem.implementation.MachineMaterial;
import io.github.mooy1.slimechem.implementation.atomic.Element;
import io.github.mooy1.slimechem.implementation.atomic.Molecule;
import io.github.mooy1.slimechem.implementation.atomic.isotopes.Isotope;
import io.github.mooy1.slimechem.implementation.attributes.Ingredient;
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
import io.github.mooy1.slimechem.objects.IngredientItem;
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

public final class Registry {

    private static final int registrySize = Element.values().length + Isotope.getIsotopes().size() + Molecule.values().length;
    
    @Getter
    private static final Map<SlimefunItem, Ingredient> items = Maps.newHashMapWithExpectedSize(registrySize);
    @Getter
    private static final Map<String, Ingredient> ids = Maps.newHashMapWithExpectedSize(registrySize);

    @Getter
    private static final Set<Ingredient> radioactiveItems = new HashSet<>((Element.values().length / 3) + Isotope.getIsotopes().size());

    public static void setup(SlimeChem plugin) {

        new ElementCategory(plugin).register();

        new SlimefunItem(Categories.MACHINES, Items.ADDON_INFO, RecipeType.NULL, null);

        for (Element element : Element.values()) {
            if (element.isRadioactive()) {
                radioactiveItems.add(element);
            }
            registerItem(new IngredientItem(Categories.ELEMENTS, element, RecipeType.NULL, null), plugin); //should later be changed to proton+neutron+electron recipe in fusion
        }
        
        plugin.getLogger().log(Level.INFO, "Registered " + Element.values().length + " Elements!");

        final EnumMap<Element, Set<Isotope>> isotopes = Isotope.getIsotopes();
        int isocount = 0;
        for (Set<Isotope> isotopeSet : isotopes.values()) {
            for (Isotope isotope : isotopeSet) {
                isocount++;
                if (isotope.isRadioactive()) {
                    radioactiveItems.add(isotope);
                }
                List<Isotope> superIsotopes = new ArrayList<>();
                for (Set<Isotope> isotopeCollection : isotopes.values()) {
                    for (Isotope iso : isotopeCollection) {
                        iso.getDecayProduct().ifPresent(i -> {
                            if (i.equals(isotope)) {
                                superIsotopes.add(iso);
                            }
                        });
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
                        } catch (IndexOutOfBoundsException e) {
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
            registerItem(new IngredientItem(Categories.MOLECULES, molecule, RecipeTypes.COMBINER, molecule.getRecipe()), plugin);
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

        new ChemicalDissolver().register(plugin);
        new ChemicalCombiner().register(plugin);
        new NuclearFurnace().register(plugin);

        for (RTG.Type type : RTG.Type.values()) {
            new RTG(type).register(plugin);
        }

        new RTG1().setCapacity(32).setEnergyProduction(4).register(plugin);
        new RTG2().setCapacity(64).setEnergyProduction(8).register(plugin);

    }
    
    private static void registerItem(@Nonnull IngredientItem item, @Nonnull SlimeChem plugin) {
        items.put(item, item.getIngredient());
        ids.put(item.getId(), item.getIngredient());
        item.register(plugin);
    }

}
