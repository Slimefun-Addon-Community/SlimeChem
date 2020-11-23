package io.github.mooy1.slimechem.implementation;

import lombok.Getter;

import javax.annotation.Nonnull;

/**
 * This class holds a {@link Element}, {@link Isotope}, or {@link Molecule} as an ingredient of a {@link Molecule}
 *
 * @author Seggan
 * @author Mooy1
 * 
 */
@Getter
public class Ingredient {
    
    public static final Ingredient DIOXIDE = new Ingredient(Element.OXYGEN, 2);
    
    @Nonnull
    private final String formula;
    @Nonnull
    private final IngredientObject object;
    private final int amount;
    
    public Ingredient(@Nonnull IngredientObject o , int i) {
        this.object = o;
        this.amount = i;
        this.formula = o.getFormula(i);
    }
    
    /**
     * This interface should be implemented by any {@link Object} that could be an {@link Ingredient} in a {@link Molecule}
     *
     * @author Mooy1
     *
     */
    interface IngredientObject {
        @Nonnull
        String getFormula(int i);

        boolean isElement();
    }
}

