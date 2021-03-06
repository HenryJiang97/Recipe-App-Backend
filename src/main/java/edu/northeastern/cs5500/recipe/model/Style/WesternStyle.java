package edu.northeastern.cs5500.recipe.model.Style;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public abstract class WesternStyle extends Style {
    private String name;

    /** @return true if this ingredient is valid */
    @JsonIgnore
    public boolean isValid() {
        return name != null && !name.isEmpty();
    }
}
