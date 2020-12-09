package io.github.mooy1.slimechem.implementation.atomic;

import io.github.mooy1.slimechem.implementation.subatomic.Boson;
import io.github.mooy1.slimechem.implementation.subatomic.Lepton;
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
    LEAD_206(Element.LEAD, 206), // stable
    LEAD_210(Element.LEAD, 210, Isotope.BISMUTH_210, Lepton.ELECTRON, Lepton.ELECTRON_NEUTRINO),
    LEAD_214(Element.LEAD, 214, Isotope.BISMUTH_214, Lepton.ELECTRON, Lepton.ELECTRON_NEUTRINO),
    BISMUTH_210(Element.BISMUTH, 210, Isotope.POLONIUM_210, Lepton.ELECTRON, Lepton.ELECTRON_NEUTRINO),
    BISMUTH_214(Element.BISMUTH, 214, Isotope.POLONIUM_214, Lepton.ELECTRON, Lepton.ELECTRON_NEUTRINO),
    POLONIUM_210(Element.POLONIUM, 210, Isotope.LEAD_206, Element.HELIUM, Boson.PHOTON),
    POLONIUM_214(Element.POLONIUM, 214, Isotope.LEAD_210, Element.HELIUM, Boson.PHOTON),
    RADON_222(Element.RADON, 222, Isotope.THORIUM_230, Element.HELIUM, Boson.PHOTON),
    THORIUM_230(Element.THORIUM, 230, Element.RADIUM, Element.HELIUM, Boson.PHOTON),
    THORIUM_234(Element.THORIUM, 234, Isotope.PROTACTINIUM_234, Lepton.ELECTRON, Lepton.ELECTRON_NEUTRINO),
    PROTACTINIUM_234(Element.PROTACTINIUM, 234, Isotope.URANIUM_234, Lepton.ELECTRON, Lepton.ELECTRON_NEUTRINO),
    URANIUM_234(Element.URANIUM, 234, Isotope.THORIUM_230, Element.HELIUM, Boson.PHOTON),
    URANIUM_235(Element.URANIUM, 235),
    PLUTONIUM_238(Element.PLUTONIUM, 238, Isotope.URANIUM_234, Element.HELIUM, Boson.PHOTON),
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
