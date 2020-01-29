package edu.northeastern.cs5500.recipe.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.UUID;
import lombok.Data;

@Data
public class Recipe {
    private UUID id;
    private String title;
    private String description;

    /** @return true if this recipe is valid */
    @JsonIgnore
    public boolean isValid() {
        return title != null && !title.isEmpty();
    }
}
