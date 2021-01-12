package io.github.seggan.slimechem.test;

import io.github.mooy1.slimechem.implementation.atomic.isotopes.DecayType;
import io.github.mooy1.slimechem.implementation.atomic.isotopes.Isotope;
import io.github.mooy1.slimechem.implementation.atomic.isotopes.IsotopeLoader;
import io.github.mooy1.slimechem.lists.Constants;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.Set;

public class IsotopeTest {

    @BeforeAll
    public static void setUp() {
        Constants.isTestingEnvironment = true;
    }

    @Test
    public void testGetDecayProduct() {
        Isotope.getIsotopes().clear();

        Asserts.assertEquals(Isotope.addIsotope(1, "H", DecayType.STABLE).getDecayProduct(),
            Optional.empty());

        Isotope isotope = Isotope.addIsotope(2, "H", DecayType.ALPHA);
        isotope.loadDecayProduct(1, "H");
        isotope.getDecayProduct();

        Asserts.assertThrows(
            () -> Isotope.addIsotope(3, "H", DecayType.ALPHA).getDecayProduct(),
            IllegalStateException.class
        );
    }

    @Test
    public void testSameDecay() {
        Isotope.getIsotopes().clear();
        IsotopeLoader isotopeLoader = new IsotopeLoader();
        isotopeLoader.load();
        isotopeLoader.loadDecayProducts();

        for (Set<Isotope> isotopeSet : Isotope.getIsotopes().values()) {
            for (Isotope isotope : isotopeSet) {
                Optional<Isotope> decayProduct = isotope.getDecayProduct();

                decayProduct.ifPresent((i) -> {
                    if (i.getElement() == isotope.getElement() &&
                        !(isotope.getDecayType().toString().contains("NEUTRON"))) {
                        System.out.println(isotope);
                    }
                });
            }
        }
    }

    /*@Test
    public void testIsotopeRadioactivity() {
        Isotope.getIsotopes().clear();
        IsotopeLoader isotopeLoader = new IsotopeLoader();
        isotopeLoader.load();
        isotopeLoader.loadDecayProducts();

        Isotope biggestRadioactivity;

        for (int i = 0; i < 1000; i++) {
            biggestRadioactivity = Isotope.getIsotope(1, Element.HYDROGEN);

            for (Set<Isotope> isotopeSet : Isotope.getIsotopes().values()) {
                for (Isotope isotope : isotopeSet) {
                    biggestRadioactivity = isotope.getRadiationLevel() > biggestRadioactivity.getRadiationLevel() ?
                        isotope : biggestRadioactivity;
                }
            }

            System.out.println(biggestRadioactivity.toString() + ' ' + biggestRadioactivity.getRadiationLevel());

            Isotope.getIsotopes().get(biggestRadioactivity.getElement()).remove(biggestRadioactivity);
        }
    }*/
}
