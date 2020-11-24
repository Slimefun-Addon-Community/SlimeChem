package io.github.mooy1.slimechem.implementation;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import io.github.mooy1.slimechem.utils.SubNum;
import lombok.Getter;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.Material;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * Enum of molecules: name, formula, ingredients
 * 
 * @author Seggan
 * @author Mooy1
 * 
 */
@Getter
public enum Molecule implements Ingredient.IngredientObject {
    
    WATER("Water", new Ingredient(Element.HYDROGEN, 2), Ingredient.MONOXIDE),
    CARBON_DIOXIDE("Carbon Dioxide", new Ingredient(Element.CARBON, 1), Ingredient.DIOXIDE);
    
    @Nonnull
    private final String name;
    @Nonnull
    private final String formula;
    @Nonnull
    private final List<Ingredient> ingredients;
    @Nonnull
    private final SlimefunItemStack item;
    
    public static final BiMap<Molecule, SlimefunItem> ITEMS = HashBiMap.create(values().length);
    
    Molecule(@Nonnull String name, @Nonnull Ingredient... ingredients) {
        this.name = name;
        this.ingredients = new ArrayList<>();
        
        StringBuilder formula = new StringBuilder();
        
        for (Ingredient ingredient : ingredients) {
            formula.append(ingredient.getFormula());
            this.ingredients.add(ingredient);
        }
        
        this.formula = formula.toString();
    
        this.item = new SlimefunItemStack(
                "MOLECULE_" + this.name(),
                Material.SPONGE,
                "&b" + name,
                "&7" + formula
        );
    }
    
    @Nonnull
    @Override
    public String getFormula(int i) {
        return (i != 1 ? "(" : "") + this.formula + (i != 1 ? ")" : "") + SubNum.fromInt(i);
    }
    
}
