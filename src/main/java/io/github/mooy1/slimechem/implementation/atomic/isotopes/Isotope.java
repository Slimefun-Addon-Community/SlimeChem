package io.github.mooy1.slimechem.implementation.atomic.isotopes;

import io.github.mooy1.slimechem.implementation.atomic.Element;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.EnumMap;
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
    private static final EnumMap<Element, Set<Isotope>> isotopes = new EnumMap<>(Element.class);

    @AllArgsConstructor
    public static enum DecayType {
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
    @Getter(AccessLevel.NONE)
    private Isotope decayProduct = null;

    private Isotope(int mass, String abbr, DecayType decayType) {
        element = Element.getByAbbr(abbr);

        this.mass = mass;
        protons = element.getNumber();
        neutrons = this.mass - protons;

        this.decayType = decayType;
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
