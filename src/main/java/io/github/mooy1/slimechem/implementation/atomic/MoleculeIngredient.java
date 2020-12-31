package io.github.mooy1.slimechem.implementation.atomic;

import io.github.mooy1.slimechem.implementation.atomic.isotopes.Isotope;
import io.github.mooy1.slimechem.implementation.attributes.Ingredient;
import io.github.mooy1.slimechem.utils.ItemFilter;
import lombok.Getter;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * This class holds a {@link Element}, {@link Isotope},
 * or {@link Molecule} and an amount as an ingredient of a {@link Molecule}
 *
 * @author Seggan
 * @author Mooy1
 * 
 * @see Ingredient
 * 
 */
@Getter
public class MoleculeIngredient {
    
    public static final MoleculeIngredient MONOXIDE = new MoleculeIngredient(Element.OXYGEN, 1);
    public static final MoleculeIngredient DIOXIDE = new MoleculeIngredient(Element.OXYGEN, 2);
    public static final MoleculeIngredient DIHYDROGEN = new MoleculeIngredient(Element.HYDROGEN, 2);
    
    @Nonnull
    private final String formula;
    @Nonnull
    private final Ingredient ingredient;
    private final int amount;
    
    public MoleculeIngredient(@Nonnull Ingredient ingredient, int amount) {
        this.formula = ingredient.getFormula(amount);
        this.ingredient = ingredient;
        this.amount = amount;
    }

    public MoleculeIngredient(@Nonnull Ingredient ingredient) {
        this.formula = ingredient.getFormula(1);
        this.ingredient = ingredient;
        this.amount = 1;
    }
    
    @Nonnull
    public ItemStack getNewItem() {
        return new CustomItem(this.ingredient.getItem(), this.amount);
    }
    
    @Nonnull
    public ItemFilter toFilter() {
        return new ItemFilter(getNewItem());
    }
    
    @Nonnull
    public static ItemFilter[] getFilter(@Nonnull MoleculeIngredient[] array, int size) {
        ItemFilter[] filter = new ItemFilter[size];
        
        for (int i = 0 ; i < Math.min(array.length, size) ; i++) {
            @Nullable MoleculeIngredient ingredient = array[i];
            if (ingredient != null) {
                filter[i] = array[i].toFilter();
            }
        }
        
        return filter;
    }
    
}
