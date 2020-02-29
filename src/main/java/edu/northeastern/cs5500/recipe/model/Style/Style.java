package edu.northeastern.cs5500.recipe.model.Style;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public abstract class Style {
    private String name;

    /** @return true if this ingredient is valid */
    @JsonIgnore
    public boolean isValid() {
        return name != null && !name.isEmpty();
    }
}
