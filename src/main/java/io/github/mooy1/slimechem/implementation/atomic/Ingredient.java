package io.github.mooy1.slimechem.implementation.atomic;

import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * This interface should be implemented by any {@link Object}
 * that could be an {@link MoleculeIngredient} of a {@link Molecule}
 * 
 * @author Mooy1
 * 
 */
public interface Ingredient {
    
    @Nonnull
    String getFormula(int i);
    
    @Nonnull
    ItemStack getItem();
    
}