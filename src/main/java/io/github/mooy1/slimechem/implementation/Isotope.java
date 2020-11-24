package io.github.mooy1.slimechem.implementation;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import io.github.mooy1.slimechem.utils.SubNum;
import io.github.mooy1.slimechem.utils.SuperNum;
import lombok.Getter;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.Material;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

/**
 * Enum of isotopes: name, mass, element, neutrons
 *
 * @author Mooy1
 * 
 */
@Getter
public enum Isotope implements Ingredient.IngredientObject {
    
    DEUTERIUM(Element.HYDROGEN, "Deuterium", 2.014),
    TRITIUM(Element.HYDROGEN, "Tritium", 3.016);
    
    @Nonnull
    private final Element element;
    @Nullable
    private final String name;
    private final double mass;
    private final int number;
    private final int neutrons;
    @Nonnull
    private final SlimefunItemStack item;
    
    public static final BiMap<Isotope, SlimefunItem> ITEMS = HashBiMap.create(values().length);
    
    Isotope(@Nonnull Element element, @Nullable String name, double mass) {
        this.element = element;
        this.mass = mass;
        this.number = (int) Math.round(mass);
        this.name = name != null ? name : element.getName() + "-" + this.number;
        this.neutrons = this.number - element.getNumber();
        this.item = new SlimefunItemStack(
                "ISOTOPE_" + this.name(),
                Objects.requireNonNull(Material.getMaterial(element.getSeries().getColor() + "_DYE")),
                "&b" + name,
                "Mass: " + this.mass
        );
    }
    
    @Nonnull
    @Override
    public String getFormula(int i) {
        return SuperNum.fromInt(this.number) + element.getSymbol() + SubNum.fromInt(i);
    }
    
}
