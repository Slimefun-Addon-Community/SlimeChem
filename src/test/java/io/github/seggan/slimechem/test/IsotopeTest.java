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

    @Test(expected = IllegalStateException.class)
    public void testGetDecayProduct() {
        Isotope.getIsotopes().clear();

        Assert.assertEquals(Isotope.addIsotope(1, "H", Isotope.DecayType.STABLE).getDecayProduct(),
            Optional.empty());

        // Should not throw any exception
        Isotope isotope = Isotope.addIsotope(2, "H", Isotope.DecayType.ALPHA);
        isotope.loadDecayProduct(1, "H");
        isotope.getDecayProduct();

        // Should throw IllegalStateException
        Isotope.addIsotope(3, "H", Isotope.DecayType.ALPHA).getDecayProduct();
    }
}
