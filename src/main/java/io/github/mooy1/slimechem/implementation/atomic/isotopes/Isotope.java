package io.github.mooy1.slimechem.implementation.atomic.isotopes;

import io.github.mooy1.slimechem.implementation.atomic.Element;
import io.github.mooy1.slimechem.implementation.attributes.Ingredient;
import io.github.mooy1.slimechem.lists.Constants;
import io.github.mooy1.slimechem.utils.SubNum;
import io.github.mooy1.slimechem.utils.SuperNum;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.Material;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Objects;
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

    private final int mass;
    @EqualsAndHashCode.Include
    private final int protons;
    @EqualsAndHashCode.Include
    private final int neutrons;
    private final Element element;

    private final String name;
    private final String formula;

    private final DecayType decayType;
    @Getter(AccessLevel.NONE)
    private Isotope decayProduct = null;

    private final SlimefunItemStack item;

    private Isotope(int mass, String abbr, DecayType decayType) {
        element = Element.getByAbbr(abbr);

        this.mass = mass;
        this.protons = element.getNumber();
        this.neutrons = mass - this.protons;

        this.name = element.getName() + "-" + this.mass;
        this.formula = SuperNum.fromInt(this.mass) + abbr;

        this.decayType = decayType;

        if (!Constants.isTestingEnvironment) {
            this.item = new SlimefunItemStack(
                String.format("ISOTOPE_%s_%d", element.name(), this.mass),
                Objects.requireNonNull(Material.getMaterial(element.getSeries().getColor() + "_DYE")),
                "&b" + this.name,
                "&7" + this.formula,
                "&7Mass: " + this.mass
            );
        } else {
            this.item = null;
        }
    }

    @Nonnull
    public static Isotope addIsotope(int mass, String abbr, DecayType decayType) {
        Isotope isotope = new Isotope(mass, abbr, decayType);
        Element element = isotope.getElement();
        isotopes.compute(element, (key, val) -> {
            if (val == null) {
                return new HashSet<>(Collections.singleton(isotope));
            } else {
                val.add(isotope);
                return val;
            }
        });

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
        if (decayProduct == null) {
            isotopes.get(element).remove(this);
            if (Constants.isTestingEnvironment) {
                System.out.println(this);
            }
        }
    }

    /**
     * Gets the decay product of the isotope
     *
     * @return {@link Optional}; empty if the isotope is stable, otherwise contains the decay product
     * @throws IllegalStateException if the decay product has not been loaded <i>and</i> the isotope is radioactive
     */
    @Nonnull
    public Optional<Isotope> getDecayProduct() {
        if (decayType == DecayType.STABLE) return Optional.empty();

        if (decayProduct == null) {
            throw new IllegalStateException("Decay product not initialized!");
        } else {
            return Optional.of(decayProduct);
        }
    }

    public boolean isRadioactive() {
        return decayType != DecayType.STABLE;
    }

    /**
     * This method returns an {@link Element}mif this isotope is the main isotope.
     * Otherwise, returns null. For example, Hydrogen-1 would return {@link Element#HYDROGEN},
     * but Hydrogen-2 would return null.
     *
     * @return {@link Element}
     * @implNote the check uses {@link #isMainIsotope()}
     */
    public Element getCorrespondingElement() {
        return isMainIsotope() ? this.element : null;
    }

    /**
     * Checks if this isotope is the main isotope of the element
     *
     * @return true if the isotope is the main isotope of the element, false otherwise
     * @implNote executes {@code Math.round(this.element.getMass()) == this.mass}
     */
    public boolean isMainIsotope() {
        return Math.round(this.element.getMass()) == this.mass;
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
}
