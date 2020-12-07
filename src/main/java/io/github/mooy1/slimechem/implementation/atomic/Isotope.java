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
    URANIUM_235(Element.URANIUM, 235, true),
    PLUTONIUM_238(Element.PLUTONIUM, 238, true),
    CALIFORNIUM_250(Element.CALIFORNIUM, 250, true),
    AMERICIUM_241(Element.AMERICIUM, 241, true),
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
    private final boolean radioactive;
    @Nonnull
    private final SlimefunItemStack item;
    
    Isotope(@Nonnull Element element, @Nonnull String name, double mass, boolean radioactive) {
        this.element = element;
        this.mass = mass;
        this.number = (int) Math.round(mass);
        this.name = name;
        this.formula = SuperNum.fromInt(this.number) + element.getSymbol();
        this.neutrons = this.number - element.getNumber();
        this.radioactive = radioactive;
        this.item = new SlimefunItemStack(
                "ISOTOPE_" + this.name(),
                Objects.requireNonNull(Material.getMaterial(element.getSeries().getColor() + "_DYE")),
                "&b" + name,
                "&7" +  this.formula,
                "&7Mass: " + this.mass
        );
    }
    
    Isotope(@Nonnull Element element, double mass, boolean radioactive) {
        this(element, (element.getName() + "-" + Math.round(mass)), mass, radioactive);
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
