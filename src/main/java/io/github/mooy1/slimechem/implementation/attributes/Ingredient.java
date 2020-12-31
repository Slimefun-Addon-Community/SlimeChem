package io.github.mooy1.slimechem.implementation.attributes;

import io.github.mooy1.slimechem.implementation.atomic.Molecule;
import io.github.mooy1.slimechem.implementation.atomic.MoleculeIngredient;

import javax.annotation.Nonnull;

/**
 * This interface should be implemented by any {@link Object}
 * that could be an {@link MoleculeIngredient} of a {@link Molecule}
 * 
 * @author Mooy1
 * 
 */
public interface Ingredient extends Itemable {
    
    @Nonnull
    String getFormula(int i);
}