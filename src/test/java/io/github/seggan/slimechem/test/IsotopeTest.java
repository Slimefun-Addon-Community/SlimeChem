package io.github.seggan.slimechem.test;

import io.github.mooy1.slimechem.implementation.atomic.isotopes.Isotope;
import io.github.mooy1.slimechem.lists.Constants;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Optional;

public class IsotopeTest {

    @BeforeClass
    public static void setUp() {
        Constants.isTestingEnvironment = true;
    }

    @Test
    public void testGetDecayProduct() {
        Isotope.getIsotopes().clear();

        Assertions.assertEquals(Isotope.addIsotope(1, "H", Isotope.DecayType.STABLE).getDecayProduct(),
            Optional.empty());

        Isotope isotope = Isotope.addIsotope(2, "H", Isotope.DecayType.ALPHA);
        isotope.loadDecayProduct(1, "H");
        isotope.getDecayProduct();

        Assertions.assertThrows(
            () -> Isotope.addIsotope(3, "H", Isotope.DecayType.ALPHA).getDecayProduct(),
            IllegalStateException.class
        );
    }

    @Test
    public void testSetAmount() {
        Isotope.getIsotopes().clear();

        Assertions.assertThrows(
            () -> Isotope.addIsotope(1, "H", Isotope.DecayType.ALPHA).setAmount(2),
            UnsupportedOperationException.class
        );

        Isotope.addIsotope(2, "H", Isotope.DecayType.PROTON).setAmount(2);
        Isotope.addIsotope(3, "H", Isotope.DecayType.NEUTRON).setAmount(2);
    }
}
