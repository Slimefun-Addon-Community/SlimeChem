package io.github.seggan.slimechem.test;

import io.github.mooy1.slimechem.implementation.atomic.Element;
import io.github.mooy1.slimechem.implementation.atomic.isotopes.Isotope;
import io.github.mooy1.slimechem.implementation.atomic.isotopes.IsotopeLoader;
import io.github.mooy1.slimechem.lists.Constants;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class IsotopeLoadingTest {

    private IsotopeLoader isotopeLoader = null;

    @BeforeAll
    public static void setUp() {
        Constants.isTestingEnvironment = true;
        Isotope.getIsotopes().clear();
    }

    @Test
    @Order(1)
    public void testIsotopeLoading() {
        isotopeLoader = new IsotopeLoader();
        isotopeLoader.load();
        isotopeLoader.loadDecayProducts();
    }

    @Test
    @Order(2)
    public void testIsotopeDecayProducts() {
        System.out.println(Isotope.getIsotope(238, Element.URANIUM).getDecayProduct().get());
        Asserts.assertEquals(
            Isotope.getIsotope(2, Element.HELIUM).getDecayProduct().get(),
            Isotope.getIsotope(1, Element.HYDROGEN)
        );
    }
}
