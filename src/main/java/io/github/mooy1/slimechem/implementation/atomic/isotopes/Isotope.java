package io.github.mooy1.slimechem.implementation.atomic.isotopes;

import io.github.mooy1.slimechem.implementation.atomic.Element;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Class for isotopes
 *
 * @author Seggan
 */
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Isotope {
    @Getter
    private static final EnumMap<Element, Set<Isotope>> isotopes = new EnumMap<>(Element.class);

    @AllArgsConstructor
    public enum DecayType {
        ALPHA("alpha"),
        BETA_PLUS("beta+"),
        BETA_MINUS("beta-"),
        PROTON("p"),
        NEUTRON("n"),
        ELECTRON_CAPTURE("capture"),
        STABLE("stable");

        private final String representation;

        public static DecayType getByRepresentation(String representation) {
            for (DecayType decayType : DecayType.values()) {
                if (decayType.representation.equals(representation)) {
                    return decayType;
                }
            }

            return null;
        }
    }

    private final int mass;
    @EqualsAndHashCode.Include
    private final int protons;
    @EqualsAndHashCode.Include
    private final int neutrons;
    private final Element element;
    private final DecayType decayType;
    /**
     * Indicates amount of decays done at once. Only applicable if {@link #decayType} is {@link DecayType#PROTON}
     * or {@link DecayType#NEUTRON}
     *
     * @see DecayType
     */
    @Setter
    private int amount = 1;
    @Getter(AccessLevel.NONE)
    private Isotope decayProduct = null;

    private Isotope(int mass, String abbr, DecayType decayType) {
        element = Element.getByAbbr(abbr);

        this.mass = mass;
        protons = element.getNumber();
        neutrons = this.mass - protons;

        this.decayType = decayType;
    }

    @Nonnull
    public static Isotope addIsotope(int mass, String abbr, DecayType decayType) {
        Isotope isotope = new Isotope(mass, abbr, decayType);
        Element element = isotope.getElement();
        if (isotopes.containsKey(element)) {
            isotopes.get(element).add(isotope);
        } else {
            isotopes.put(element, new HashSet<>(Collections.singleton(isotope)));
        }

        return isotope;
    }

    @Nullable
    public static Isotope getIsotope(int mass, Element element) {
        for (Isotope isotope : isotopes.get(element)) {
            if (isotope.getElement() == element && isotope.getMass() == mass) {
                return isotope;
            }
        }

        return null;
    }

    public void loadDecayProduct(int mass, String abbr) {
        for (Isotope isotope : isotopes.get(Element.getByAbbr(abbr))) {
            if (isotope.getMass() == mass) {
                decayProduct = isotope;
                return;
            }
        }
    }

    /**
     * Gets the decay product of the isotope
     *
     * @return {@link Optional}; empty if the isotope is stable, otherwise contains the decay product
     * @throws IllegalStateException if the decay product has not been loaded <i>and</i> the isotope is radioactive
     */
    public Optional<Isotope> getDecayProduct() {
        if (decayType == DecayType.STABLE) return Optional.empty();

        if (decayProduct == null) {
            throw new IllegalStateException("Decay product not initialized!");
        } else {
            return Optional.of(decayProduct);
        }
    }
}
