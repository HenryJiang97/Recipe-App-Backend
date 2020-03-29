package edu.northeastern.cs5500.recipe.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import java.util.UUID;
import lombok.Data;

@Data
public class Direction {
    /** Define the enum for direction types */
    public enum DirectionTypes {
        PREP,
        WAIT,
        COOK;
    }

    private UUID id;
    private String name;
    // TODO: Ingredients
    private List<Ingredient> ingredients;
    private int time;
    private int temp;
    private DirectionTypes directionType;

    /** Construct a User class */
    public Direction() {}

    /** @return true if this recipe is valid */
    @JsonIgnore
    public boolean isValid() {
        return name != null && !name.isEmpty();
    }

    /** Get the unique id of the direction. */
    public UUID getId() {
        return this.id;
    }

    /** Get the name of the direction. */
    public String getName() {
        return this.name;
    }

    // TODO: Define getIngredients class

    /** Get the time of the direction. */
    public int getTime() {
        return this.time;
    }

    /** Get the temp of the direction. */
    public int getTemp() {
        return this.temp;
    }

    /** Get the temp of the direction. */
    public DirectionTypes getDirectionType() {
        return this.directionType;
    }

    /** Set the id of the direction. */
    public void setId(UUID id) {
        this.id = id;
    }

    /** Set the name of the direction. */
    public void setName(String name) {
        this.name = name;
    }

    /** Set the time of the direction. */
    public void setTime(int time) {
        this.time = time;
    }

    /** Set the temperature of the direction. */
    public void setTemp(int temp) {
        this.temp = temp;
    }

    // TODO: Define setIngredients class

    /** Set the unique id of the user. */
    public void setDirectionType(DirectionTypes direcitonType) {
        this.directionType = direcitonType;
    }
}
