package io.github.mooy1.slimechem.implementation;

import lombok.Getter;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

/**
 * This class holds a {@link Element}, {@link Isotope},
 * or {@link Molecule} as an ingredient of a {@link Molecule}
 *
 * @author Seggan
 * @author Mooy1
 * 
 */
@Getter
public class Ingredient {
    
    public static final Ingredient MONOXIDE = new Ingredient(Element.OXYGEN, 1);
    public static final Ingredient DIOXIDE = new Ingredient(Element.OXYGEN, 2);
    
    @Nonnull
    private final String formula;
    @Nonnull
    private final Map<IngredientObject, Integer> objects = new HashMap<>();
    
    public Ingredient(@Nonnull IngredientObject o , int i) {
        this.objects.put(o, i);
        this.formula = o.getFormula(i);
    }
    
    /**
     * This interface should be implemented by any {@link Object}
     * that could be an {@link Ingredient} of a {@link Molecule}
     */
    interface IngredientObject {
        @Nonnull
        String getFormula(int i);

        boolean isElement();
        
    }
    
}

