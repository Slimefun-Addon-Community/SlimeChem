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
public enum Isotope implements Ingredient, DecayProduct {
    
    DEUTERIUM(Element.HYDROGEN, "Deuterium", 2.014, false),
    TRITIUM(Element.HYDROGEN, "Tritium", 3.016, true),
    URANIUM_235(Element.URANIUM, 235),
    PLUTONIUM_238(Element.PLUTONIUM, 238),
    CALIFORNIUM_250(Element.CALIFORNIUM, 250),
    AMERICIUM_241(Element.AMERICIUM, 241),
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
    private final DecayProduct[] decayProducts;
    @Nonnull
    private final SlimefunItemStack item;
    
    Isotope(@Nonnull Element element, @Nonnull String name, double mass, boolean radioactive, DecayProduct... decayProducts) {
        this.element = element;
        this.mass = mass;
        this.number = (int) Math.round(mass);
        this.name = name;
        this.formula = SuperNum.fromInt(this.number) + element.getSymbol();
        this.neutrons = this.number - element.getNumber();
        this.radioactive = radioactive;
        this.decayProducts = radioactive ? decayProducts : new DecayProduct[0];
        this.item = new SlimefunItemStack(
                "ISOTOPE_" + this.name(),
                Objects.requireNonNull(Material.getMaterial(element.getSeries().getColor() + "_DYE")),
                "&b" + name,
                "&7" +  this.formula,
                "&7Mass: " + this.mass
        );
    }

    // No decay products = stable
    Isotope(@Nonnull Element element, double mass) {
        this(element, (element.getName() + "-" + Math.round(mass)), mass, false);
    }

    // Decay products? RUN!!!!!!
    Isotope(@Nonnull Element element, double mass, DecayProduct... decayProducts) {
        this(element, (element.getName() + "-" + Math.round(mass)), mass, true, decayProducts);
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
