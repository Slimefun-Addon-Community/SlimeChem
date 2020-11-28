package io.github.mooy1.slimechem.implementation;

import com.google.common.collect.Maps;
import io.github.mooy1.slimechem.implementation.atomic.Element;
import io.github.mooy1.slimechem.implementation.atomic.Ingredient;
import io.github.mooy1.slimechem.implementation.atomic.Isotope;
import io.github.mooy1.slimechem.implementation.atomic.Molecule;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;

import java.util.Map;

public final class Registry {
    
    public static final Map<SlimefunItem, Ingredient> ITEMS = Maps.newHashMapWithExpectedSize(Element.values().length + Isotope.values().length + Molecule.values().length);
    
}
