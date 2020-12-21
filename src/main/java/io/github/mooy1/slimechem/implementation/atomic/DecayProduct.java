package io.github.mooy1.slimechem.implementation.atomic;

import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;

import javax.annotation.Nonnull;

/**
 * Marks decay products
 *
 * @author Seggan
 */
public interface DecayProduct {
    @Nonnull
    SlimefunItemStack getItem();
}
