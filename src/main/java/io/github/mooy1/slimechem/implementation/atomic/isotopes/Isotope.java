package io.github.mooy1.slimechem.implementation.atomic.isotopes;

import io.github.mooy1.slimechem.implementation.atomic.Element;
import io.github.mooy1.slimechem.implementation.attributes.Ingredient;
import io.github.mooy1.slimechem.lists.Constants;
import io.github.mooy1.slimechem.utils.SubNum;
import io.github.mooy1.slimechem.utils.SuperNum;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;

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
public class Isotope implements Ingredient {
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

    private final String name;
    private final String formula;

    private final DecayType decayType;
    /**
     * Indicates amount of decays done at once. Only applicable if {@link #decayType} is {@link DecayType#PROTON}
     * or {@link DecayType#NEUTRON}. Only mutable once.
     *
     * @see DecayType
     * @see #amountChanged
     */
    private int amount = 1;
    /**
     * Marks if the {@link #amount} variable was set or not. If the decay type is not one of those specified in
     * the #amount doc, it's automatically set to false;
     *
     * @see DecayType
     * @see #amount
     */
    private boolean amountChanged;
    @Getter(AccessLevel.NONE)
    private Isotope decayProduct = null;

    private final SlimefunItemStack item;

    private Isotope(int mass, String abbr, DecayType decayType) {
        element = Element.getByAbbr(abbr);

        this.mass = mass;
        protons = element.getNumber();
        neutrons = mass - protons;

        name = element.getName() + "-" + mass;
        formula = SuperNum.fromInt(mass) + abbr;

        amountChanged = !(decayType == DecayType.PROTON || decayType == DecayType.NEUTRON);

        this.decayType = decayType;

        if (!Constants.isTestingEnvironment) {
            item = new SlimefunItemStack(
                String.format("ISOTOPE_%s_%d", element.name(), mass),
                "&b" + name,
                "&7" +  formula,
                "&7Mass: " + mass
            );
        } else {
            item = null;
        }
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

    @Override
    public String toString() {
        return element.getName() + '-' + mass;
    }

    @Nonnull
    @Override
    public String getFormula(int i) {
        return formula + SubNum.fromInt(i);
    }

    public void setAmount(int i) {
        if (!amountChanged) {
            amount = i;
            amountChanged = true;
        } else {
            throw new IllegalStateException("The amount is already set!");
        }
    }
}
