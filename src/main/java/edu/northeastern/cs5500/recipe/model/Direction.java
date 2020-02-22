package edu.northeastern.cs5500.recipe.model;

import lombok.Data;

@Data
public class Direction {
    enum DirecitonTypes {
        PREP, WAIT, COOK;
    }

    // TODO: Ingredients
    // private List<Ingredient> ingredients;
    private int time;
    private DirecitonTypes direcitonType;
    private int temp;
    

    /**
     * Construct a User class
     *
     */
    public Direction() {

    }

    /** Get the unique id of the user. */
    

    
}
