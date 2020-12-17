package io.github.mooy1.slimechem.implementation.atomic.isotopes;

import io.github.mooy1.slimechem.implementation.atomic.Element;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.EnumMap;
import java.util.Set;

/**
 * Class for isotopes
 *
 * @author Seggan
 */
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public final class Isotope {
    private static final EnumMap<Element, Set<Isotope>> isotopes = new EnumMap<>(Element.class);

    private final int mass;
    @EqualsAndHashCode.Include
    private final int protons;
    @EqualsAndHashCode.Include
    private final int neutrons;
    private final Element element;
    private Isotope decayProduct = null;

    private Isotope(int mass, String abbr) {
        element = Element.getByAbbr(abbr);

        this.mass = mass;
        protons = element.getNumber();
        neutrons = this.mass - protons;
    }

    public void loadDecayProduct(int mass, String abbr) {
        for (Isotope isotope : isotopes.get(Element.getByAbbr(abbr))) {
            if (isotope.getMass() == mass) {
                decayProduct = isotope;
            }
        }
    }

    public Isotope getDecayProduct() {
        if (decayProduct == null) {
            throw new IllegalStateException("Decay product not initialized!");
        } else {
            return decayProduct;
        }
    }
}
