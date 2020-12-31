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

import java.util.EnumMap;
import java.util.Set;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class IsotopeLoadingTest {

    private IsotopeLoader isotopeLoader = new IsotopeLoader();

    @BeforeAll
    public static void setUp() {
        Constants.isTestingEnvironment = true;
        Isotope.getIsotopes().clear();
    }

    @Test
    @Order(1)
    public void testIsotopeLoading() {
        isotopeLoader.load();
        isotopeLoader.loadDecayProducts();
    }

    @Test
    @Order(2)
    public void testIsotopeDecayProducts() {
        Asserts.assertEquals(
            Isotope.getIsotope(2, Element.HELIUM).getDecayProduct().get(),
            Isotope.getIsotope(1, Element.HYDROGEN)
        );

        final EnumMap<Element, Set<Isotope>> isotopes = Isotope.getIsotopes();

        for (Element el : isotopes.keySet()) {
            for (Isotope iso : isotopes.get(el)) {
                try {
                    iso.getDecayProduct();
                    System.out.println(iso);
                } catch (IllegalStateException e) {
                    System.out.println(iso.toString());
                }
            }
        }
    }
}
