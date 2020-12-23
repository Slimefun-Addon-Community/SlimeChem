package io.github.seggan.slimechem.test;

import io.github.mooy1.slimechem.implementation.atomic.isotopes.DecayType;
import io.github.mooy1.slimechem.implementation.atomic.isotopes.Isotope;
import io.github.mooy1.slimechem.lists.Constants;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Optional;

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
}
