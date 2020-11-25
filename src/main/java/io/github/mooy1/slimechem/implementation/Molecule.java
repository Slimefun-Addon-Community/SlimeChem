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

    // Radicals. We don't have a clean way of implementing these yet
    CARBONATE("Carbonate", new Ingredient(Element.CARBON, 1), new Ingredient(Element.OXYGEN, 3)),
    SULFATE("Sulfate", new Ingredient(Element.SULFUR, 1), new Ingredient(Element.OXYGEN, 4)),
    HYDROXIDE("Hydroxide", new Ingredient(Element.HYDROGEN, 1), new Ingredient(Element.OXYGEN, 1)),
    NITRATE("Nitrate", new Ingredient(Element.NITROGEN, 1), new Ingredient(Element.OXYGEN, 3)),

    // Oxides
    WATER("Water", new Ingredient(Element.HYDROGEN, 2), new Ingredient(Element.OXYGEN, 1)),
    HYDROGEN_PEROXIDE("Hydrogen Peroxide", new Ingredient(Element.HYDROGEN, 2), Ingredient.DIOXIDE),
    CARBON_DIOXIDE("Carbon Dioxide", new Ingredient(Element.CARBON, 1), Ingredient.DIOXIDE),
    SILICON_DIOXIDE("Silicon Dioxide", new Ingredient(Element.SILICON, 1), Ingredient.DIOXIDE),

    // Ores

    // Iron
    IRON_III_OXIDE("Iron(III) Oxide (Hematite)", new Ingredient(Element.IRON, 2), new Ingredient(Element.OXYGEN, 3)),
    IRON_II_OXIDE("Iron(II,III) Oxide (Magnetite)", new Ingredient(Element.IRON, 3), new Ingredient(Element.OXYGEN, 4)),
    IRON_PERSULFIDE("Iron Persulfide (Pyrite)", new Ingredient(Element.IRON, 2), new Ingredient(Element.OXYGEN, 3)),
    COPPER_IRON_SULFIDE("Copper Iron Sulfide (Chalcopyrite)", new Ingredient(Element.COPPER, 1), new Ingredient(Element.IRON, 1),
        new Ingredient(Element.SULFUR, 1)),
    // Gold
    GOLD_TELLURIDE("Gold Telluride (Calaverite)", new Ingredient(Element.GOLD, 1), new Ingredient(Element.TELLURIUM, 2)),
    GOLD_ANTIMONIDE("Gold Antimonide (Aurostibite)", new Ingredient(Element.GOLD, 1), new Ingredient(Element.ANTIMONY, 2)),
    // Redstone (assuming copper)
    COPPER_I_OXIDE("Copper(I) Oxide (Cuprite)", new Ingredient(Element.COPPER, 2), new Ingredient(Element.OXYGEN, 1)),
    PENTACOPPER_IRON_TETRASULFIDE("Pentacopper(I) Iron(III) Tetrasulfide (Bornite)", new Ingredient(Element.COPPER, 5),
        new Ingredient(Element.IRON, 1), new Ingredient(Element.SULFUR, 4)),
    COPPER_II_OXIDE("Copper(II) Oxide (Tenorite)", new Ingredient(Element.COPPER, 1), new Ingredient(Element.OXYGEN, 1)),
    // Netherite (assuming netherite is a seaborgium/gold alloy)
    SEABORGIUM_III_OXIDE("Seaborgium(III) Oxide", new Ingredient(Element.SEABORGIUM, 2), new Ingredient(Element.OXYGEN, 3)),
    ;
    
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
                Material.HONEY_BOTTLE,
                "&b" + name,
                "&7" + formula
        );
    }
    
    @Nonnull
    @Override
    public String getFormula(int i) {
        return (i != 1 ? "(" : "") + this.formula + (i != 1 ? ")" : "") + SubNum.fromInt(i);
    }

    @Override
    public boolean isElement() {
        return false;
    }
    
}
