package io.github.mooy1.slimechem.implementation.atomic;

import io.github.mooy1.slimechem.utils.SubNum;
import lombok.Getter;
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
 * @see MoleculeIngredient
 * 
 */
@Getter
public enum Molecule implements Ingredient {

    // Radicals. We don't have a clean way of implementing these yet
    CARBONATE("Carbonate", new MoleculeIngredient(Element.CARBON, 1), new MoleculeIngredient(Element.OXYGEN, 3)),
    SULFATE("Sulfate", new MoleculeIngredient(Element.SULFUR, 1), new MoleculeIngredient(Element.OXYGEN, 4)),
    HYDROXIDE("Hydroxide", new MoleculeIngredient(Element.HYDROGEN, 1), MoleculeIngredient.MONOXIDE),
    NITRATE("Nitrate", new MoleculeIngredient(Element.NITROGEN, 1), new MoleculeIngredient(Element.OXYGEN, 3)),

    // Oxides
    WATER("Water", MoleculeIngredient.DIHYDROGEN, MoleculeIngredient.MONOXIDE),
    HYDROGEN_PEROXIDE("Hydrogen Peroxide", MoleculeIngredient.DIHYDROGEN, MoleculeIngredient.DIOXIDE),
    CARBON_DIOXIDE("Carbon Dioxide", new MoleculeIngredient(Element.CARBON, 1), MoleculeIngredient.DIOXIDE),
    SILICON_DIOXIDE("Silicon Dioxide", new MoleculeIngredient(Element.SILICON, 1), MoleculeIngredient.DIOXIDE),

    // Ores

    // Iron
    IRON_III_OXIDE("Iron(III) Oxide (Hematite)", new MoleculeIngredient(Element.IRON, 2), new MoleculeIngredient(Element.OXYGEN, 3)),
    IRON_II_OXIDE("Iron(II,III) Oxide (Magnetite)", new MoleculeIngredient(Element.IRON, 3), new MoleculeIngredient(Element.OXYGEN, 4)),
    IRON_PERSULFIDE("Iron Persulfide (Pyrite)", new MoleculeIngredient(Element.IRON, 2), new MoleculeIngredient(Element.OXYGEN, 3)),
    COPPER_IRON_SULFIDE("Copper Iron Sulfide (Chalcopyrite)", new MoleculeIngredient(Element.COPPER, 1), new MoleculeIngredient(Element.IRON, 1),
        new MoleculeIngredient(Element.SULFUR, 1)),
    // Gold
    GOLD_TELLURIDE("Gold Telluride (Calaverite)", new MoleculeIngredient(Element.GOLD, 1), new MoleculeIngredient(Element.TELLURIUM, 2)),
    GOLD_ANTIMONIDE("Gold Antimonide (Aurostibite)", new MoleculeIngredient(Element.GOLD, 1), new MoleculeIngredient(Element.ANTIMONY, 2)),
    // Redstone (assuming copper)
    COPPER_I_OXIDE("Copper(I) Oxide (Cuprite)", new MoleculeIngredient(Element.COPPER, 2), MoleculeIngredient.MONOXIDE),
    PENTACOPPER_IRON_TETRASULFIDE("Pentacopper(I) Iron(III) Tetrasulfide (Bornite)", new MoleculeIngredient(Element.COPPER, 5),
        new MoleculeIngredient(Element.IRON, 1), new MoleculeIngredient(Element.SULFUR, 4)),
    COPPER_II_OXIDE("Copper(II) Oxide (Tenorite)", new MoleculeIngredient(Element.COPPER, 1), MoleculeIngredient.MONOXIDE),
    ;
    
    private final String name;
    @Nonnull
    private final String formula;
    @Nonnull
    private final List<MoleculeIngredient> ingredients;
    @Nonnull
    private final SlimefunItemStack item;
    
    Molecule(@Nonnull String name, @Nonnull MoleculeIngredient... ingredients) {
        this.name = name;
        this.ingredients = new ArrayList<>();
        
        StringBuilder formula = new StringBuilder(ingredients.length * 3);
        
        for (MoleculeIngredient ingredient : ingredients) {
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

}
