package io.github.mooy1.slimechem.implementation.atomic;

import io.github.mooy1.slimechem.utils.SubNum;
import io.github.mooy1.slimechem.utils.SuperNum;
import lombok.Getter;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.Material;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Enum of isotopes: name, mass, element, neutrons
 *
 * @author Mooy1
 * 
 */
@Getter
public enum Isotope implements Ingredient {
    
    DEUTERIUM(Element.HYDROGEN, "Deuterium", 2.014, false),
    TRITIUM(Element.HYDROGEN, "Tritium", 3.016, true),
    U235(Element.URANIUM, 235, true),
    PU239(Element.PLUTONIUM, 239, true),
    CA252(Element.CALIFORNIUM, 252, true),
    ;
    
    @Nonnull
    private final Element element;
    @Nonnull
    private final String name;
    private final double mass;
    private final int number;
    @Nonnull
    private final String formula;
    private final int neutrons;
    private final boolean isRadioactive;
    @Nonnull
    private final SlimefunItemStack item;
    
    Isotope(@Nonnull Element element, @Nonnull String name, double mass, boolean isRadioactive) {
        this.element = element;
        this.mass = mass;
        this.number = (int) Math.round(mass);
        this.name = name;
        this.formula = SuperNum.fromInt(this.number) + element.getSymbol();
        this.neutrons = this.number - element.getNumber();
        this.isRadioactive = isRadioactive;
        this.item = new SlimefunItemStack(
                "ISOTOPE_" + this.name(),
                Objects.requireNonNull(Material.getMaterial(element.getSeries().getColor() + "_DYE")),
                "&b" + name,
                "&7" +  this.formula,
                "&7Mass: " + this.mass
        );
    }
    
    Isotope(@Nonnull Element element, double mass, boolean isRadioactive) {
        this(element, (element.getName() + "-" + Math.round(mass)), mass, isRadioactive);
    }
    
    @Nonnull
    public List<Isotope> getAllFromElement(@Nonnull Element e) {
        List<Isotope> list = new ArrayList<>(values().length);
        for (Isotope isotope : values()) {
            if (isotope.getElement() == e) {
                list.add(isotope);
            }
        }
        return list;
    }
    
    @Nonnull
    @Override
    public String getFormula(int i) {
        return this.formula + SubNum.fromInt(i);
    }

}
