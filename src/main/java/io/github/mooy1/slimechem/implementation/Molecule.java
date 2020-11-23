package io.github.mooy1.slimechem.implementation;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum Molecule implements Ingredient {
    ;

    private final String name;
    private final String formula;
    private final Ingredient[] ingredients;

    // I use a constructor here instead of lombok for the vararg
    Molecule(String name, String formula, Ingredient... ingredients) {
        this.name = name;
        this.formula = formula;
        this.ingredients = ingredients;
    }

    public List<Ingredient> getIngredients() {
        return Arrays.asList(ingredients);
    }

    @Override
    public boolean isElement() {
        return false;
    }
}
