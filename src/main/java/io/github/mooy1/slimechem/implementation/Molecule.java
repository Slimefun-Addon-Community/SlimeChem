package io.github.mooy1.slimechem.implementation;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import io.github.mooy1.slimechem.utils.SubNum;
import lombok.Getter;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.Material;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

/**
 * Enum of molecules: name, formula, ingredients
 * 
 * @author Seggan
 * @author Mooy1
 * 
 */
@Getter
public enum Molecule implements Ingredient.IngredientObject {

    // Radicals. We don't have a clean way of implementing these yet
    CARBONATE("Carbonate", new Ingredient(Element.CARBON, 1), new Ingredient(Element.OXYGEN, 3)),
    SULFATE("Sulfate", new Ingredient(Element.SULFUR, 1), new Ingredient(Element.OXYGEN, 4)),
    HYDROXIDE("Hydroxide", new Ingredient(Element.HYDROGEN, 1), new Ingredient(Element.OXYGEN, 1)),
    NITRATE("Nitrate", new Ingredient(Element.NITROGEN, 1), new Ingredient(Element.OXYGEN, 3)),

    WATER("Water", new Ingredient(Element.HYDROGEN, 2), new Ingredient(Element.OXYGEN, 1)),
    CARBON_DIOXIDE("Carbon Dioxide", new Ingredient(Element.CARBON, 1), Ingredient.DIOXIDE);

    private final String name;
    private final String formula;
    private final Map<Ingredient, Integer> ingredients;
    @Nonnull
    private final SlimefunItemStack item;
    
    public static final BiMap<Molecule, SlimefunItem> ITEMS = HashBiMap.create(values().length);

    // I use a constructor here instead of lombok for the ingredients
    Molecule(String name, Ingredient... ingredients) {
        this.name = name;
        this.ingredients = new HashMap<>();
        
        StringBuilder formula = new StringBuilder();
        for (Ingredient ingredient : ingredients) {
            formula.append(ingredient.getFormula());
            this.ingredients.put(ingredient, ingredient.getAmount());
        }
        this.formula = formula.toString();
    
        this.item = new SlimefunItemStack(
                "MOLECULE_" + this.name(),
                Material.HONEY_BOTTLE,
                "&b" + name,
                "&7" + formula
        );
    }
    
    @Nonnull
    @Override
    public String getFormula(int i) {
        return "(" + this.formula + ")" + SubNum.fromInt(i);
    }

    @Override
    public boolean isElement() {
        return false;
    }
}
