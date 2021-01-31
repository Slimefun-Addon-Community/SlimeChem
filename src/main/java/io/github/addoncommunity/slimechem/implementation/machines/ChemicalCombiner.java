package io.github.addoncommunity.slimechem.implementation.machines;

import io.github.addoncommunity.slimechem.implementation.atomic.Molecule;
import io.github.addoncommunity.slimechem.implementation.atomic.MoleculeIngredient;
import io.github.addoncommunity.slimechem.lists.Categories;
import io.github.addoncommunity.slimechem.lists.Items;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * Combines element and molecules into molecules and substances
 * 
 * @author Mooy1
 *
 */
public class ChemicalCombiner extends SlimefunItem { // TODO: change to some sort of AContainer with 6 slots for recipes

    public static final int ENERGY = 15;
    
    private static final int[] inputSlots = {10, 11, 12, 19, 20, 21};
    private static final int[] outputSlots = {25};
    
    public ChemicalCombiner() {
        super(Categories.MACHINES, Items.CHEMICAL_COMBINER, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                
        });

        for (Molecule m : Molecule.values()) {
            // register a container recipe
        }

        addRecipe(new SlimefunItemStack(SlimefunItems.COPPER_INGOT, 2), null, MoleculeIngredient.DIHYDROGEN);
        
    }

    public void addRecipe(@Nonnull ItemStack item, @Nonnull MoleculeIngredient... is) {
       // register a container recipe
    }

}

