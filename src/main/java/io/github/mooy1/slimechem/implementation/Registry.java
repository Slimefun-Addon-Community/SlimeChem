package io.github.mooy1.slimechem.implementation;

import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.Material;

import java.util.HashMap;
import java.util.Objects;

public class Registry {
    
    public final HashMap<Element, SlimefunItemStack> ELEMENT_STACKS = new HashMap<>();
    public final HashMap<SlimefunItem, Element> ELEMENTS = new HashMap<>();
    
    public Registry() {
        
        for (Element element : Element.values()) {
            ELEMENT_STACKS.put(element, new SlimefunItemStack(
                            "ELEMENT_" + element.name(),
                            Objects.requireNonNull(Material.getMaterial(element.getSeries().getColor() + "_DYE")),
                            "&b" + element.getName(),
                            "&7Protons: " + element.getAtomicNumber(),
                            "&7Neutrons: " + element.getNeutrons(),
                            "&7Electrons: " + element.getAtomicNumber()
                    )
            );
        }
        
    }
    
}
