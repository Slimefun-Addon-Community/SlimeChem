package io.github.mooy1.slimechem.implementation.attributes;

/**
 * This interface represents an atom
 *
 * @author Seggan
 */
public interface Atom extends Itemable {

    double getMass();

    int getNumber();

    int getRadiationLevel();
}
