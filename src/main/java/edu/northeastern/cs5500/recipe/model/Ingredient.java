package edu.northeastern.cs5500.recipe.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class Ingredient {
    private String name;
    private Double amount;
    private Unit unit;

    /** @return true if this ingredient is valid */
    @JsonIgnore
    public boolean isValid() {
        return name != null && !name.isEmpty();
    }
}
