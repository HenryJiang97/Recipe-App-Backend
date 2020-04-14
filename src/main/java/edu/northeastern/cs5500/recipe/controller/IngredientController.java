package edu.northeastern.cs5500.recipe.controller;

import edu.northeastern.cs5500.recipe.model.Recipe;
import edu.northeastern.cs5500.recipe.model.User;
import javax.inject.Singleton;

@Singleton
public class IngredientController {
    /**
     * Have a map of each unit to a different unit of the same type Do i have to hard code this, use
     * a library then hard code or some how use a map? A map of maps
     */
    // private Map<Unit, Map<Unit, Double>> mapUnitConverter;

    // private double convertAmount(Unit fromUnit, Unit toUnit, double currentAmount)
    //         throws Exception {
    //     Map<Unit, Double> mapUnitTo = mapUnitConverter.get(fromUnit);
    //     if (mapUnitTo.containsKey(toUnit)) {
    //         return currentAmount * mapUnitTo.get(toUnit);
    //     }
    //     throw new Exception("That unit conversion is not supported.");
    // }
    // TODO: Very confused on how to implement this seems to require direction also and don't know
    // how to make it encapsulated
    private void changeUnitToUserPreference(User currentUser, Recipe currentRecipe) {}
}
