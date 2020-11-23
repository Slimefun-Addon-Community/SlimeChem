package io.github.mooy1.slimechem.implementation;

import io.github.mooy1.slimechem.utils.SubNum;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum Molecule implements Ingredient {
    WATER("Water", "H" + SubNum.TWO + "O", new Ingredient[]{Element.HYDROGEN, Element.OXYGEN},
        new int[]{2, 1});

    private final String name;
    private final String formula;
    private final Map<Ingredient, Integer> ingredients;

    // I use a constructor here instead of lombok for the ingredients
    Molecule(String name, String formula, Ingredient[] ingredients, int[] amounts) {
        this.name = name;
        this.formula = formula;
        this.ingredients = new HashMap<>();

        for (int i = 0; i < ingredients.length; i++) {
            this.ingredients.put(ingredients[i], amounts[i]);
        }
    }

    @Override
    public boolean isElement() {
        return false;
    }
}
