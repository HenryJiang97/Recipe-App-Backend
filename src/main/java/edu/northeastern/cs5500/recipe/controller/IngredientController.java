package edu.northeastern.cs5500.recipe.controller;

import edu.northeastern.cs5500.recipe.model.Recipe;
import edu.northeastern.cs5500.recipe.model.Unit;
import edu.northeastern.cs5500.recipe.model.User;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Singleton;

@Singleton
public class IngredientController {
    /**
     * Have a map of each unit to a different unit of the same type Do i have to hard code this, use
     * a library then hard code or some how use a map? A map of maps
     */
    private Map<Unit, Map<Unit, Double>> mapUnitConverter;
    HashMap<Unit,Double> cupConversion = new HashMap<Unit, Double>(){
        {
        put(Unit.ML, 236.588);
        put(Unit.FLUIDOUNCE, 8);
        }
    };
    HashMap<Unit,Double> MLConversion = new HashMap<Unit, Double>(){
        {
        put(Unit.CUP, 0.00422675);
        put(Unit.FLUIDOUNCE, .0033814);
        }
    };
    HashMap<Unit,Double> LConversion = new HashMap<Unit, Double>(){
        {
        put(Unit.CUP, 4.25);
        put(Unit.GALLON, 0.264172);
        }
    };
    HashMap<Unit,Double> teaspoonConversion = new HashMap<Unit, Double>(){
        {
        put(Unit.TEASPOON, 4.92891902308806);
        put(Unit.GALLON, 0.264172);
        }
    };
    HashMap<Unit, Map<Unit, Double>> myMap = new HashMap<Unit, Map<Unit, Double>>() {{
        put(Unit.CUP, new HashMap<Unit, Double>(){{
            put(Unit.ML, 236.588);

        }});
        put("c", "d");
    }};

    private double convertAmount(Unit fromUnit, Unit toUnit, double currentAmount)
            throws Exception {
        Map<Unit, Double> mapUnitTo = mapUnitConverter.get(fromUnit);
        if (mapUnitTo.containsKey(toUnit)) {
            return currentAmount * mapUnitTo.get(toUnit);
        }
        throw new Exception("That unit conversion is not supported.");
    }
    // TODO: Very confused on how to implement this seems to require direction also and don't know
    // how to make it encapsulated
    private void changeUnitToUserPreference(User currentUser, Recipe currentRecipe) {}
}
