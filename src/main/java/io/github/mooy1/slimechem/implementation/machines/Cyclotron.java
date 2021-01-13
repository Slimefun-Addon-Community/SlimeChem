package io.github.mooy1.slimechem.implementation.machines;

import io.github.mooy1.slimechem.implementation.atomic.Element;
import io.github.mooy1.slimechem.implementation.atomic.isotopes.Isotope;
import io.github.mooy1.slimechem.lists.Categories;
import io.github.mooy1.slimechem.lists.Items;
import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.AContainer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Cyclotron extends AContainer implements RecipeDisplayItem {

    public Cyclotron() {
        super(Categories.MACHINES, Items.CYCLOTRON, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[0]);
    }

    @Override
    protected void registerDefaultRecipes() {
        Bukkit.getLogger().info("Loading cyclotron recipes, this might take a while...");
        List<Isotope> done = new ArrayList<>();
        for (Set<Isotope> isotopeSet : Isotope.getIsotopes().values()) {
            for (Isotope isotope : isotopeSet) {
                for (Set<Isotope> isotopes : Isotope.getIsotopes().values()) {
                    for (Isotope isotope2 : isotopes) {
                        if (done.contains(isotope2)) continue;

                        Element element = Element.getByNumber(isotope.getNumber() + isotope2.getNumber());
                        if (element == null) continue;
                        Isotope result = Isotope.getIsotope((int) Math.round(isotope.getMass() + isotope2.getMass()), element);
                        if (result == null) continue;

                        registerRecipe(
                            4,
                            new ItemStack[]{isotope.getItem(), isotope2.getItem()},
                            new ItemStack[]{result.getItem()}
                        );
                    }
                }
                done.add(isotope);
            }
        }
    }

    @Override
    public ItemStack getProgressBar() {
        return new ItemStack(Material.SLIME_BALL);
    }

    @Nonnull
    @Override
    public String getMachineIdentifier() {
        return "CYCLOTRON";
    }
}
