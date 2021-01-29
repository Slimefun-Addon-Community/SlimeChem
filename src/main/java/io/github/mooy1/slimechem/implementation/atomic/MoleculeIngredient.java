package io.github.mooy1.slimechem.implementation.atomic;

import io.github.mooy1.infinitylib.filter.FilterType;
import io.github.mooy1.infinitylib.filter.MultiFilter;
import io.github.mooy1.slimechem.implementation.atomic.isotopes.Isotope;
import io.github.mooy1.slimechem.implementation.attributes.Ingredient;
import lombok.Getter;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

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

    /**
     * Returns more than 1 item if the amount is greater then 64
     */
    @Nonnull
    public ItemStack[] getNewItems() {
        int size = (int) Math.ceil(this.amount / 64f);
        ItemStack[] items = new ItemStack[size];
        for (int i = 0 ; i < size - 1 ; i++) {
            items[i] = new CustomItem(this.ingredient.getItem(), 64);
        }
        items[size - 1] = new CustomItem(this.getIngredient().getItem(), this.amount % 64);
        return items;
    }
    
    @Nonnull
    public static MultiFilter getMultiFilter(@Nonnull MoleculeIngredient[] array, int size) {
        ItemStack[] stacks = new ItemStack[size];

        int i = 0;
        for (MoleculeIngredient ingredient : array) {
            if (ingredient != null) {
                for (ItemStack item : ingredient.getNewItems()) {
                    stacks[i] = item;
                    i++;
                    if (i == size) break;
                }
            } else {
                i++;
            }
            if (i == size) break;
        }
        
        return new MultiFilter(FilterType.MIN_AMOUNT, stacks);
    }
    
}
