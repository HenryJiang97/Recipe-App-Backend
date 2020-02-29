package edu.northeastern.cs5500.recipe.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.UUID;
import lombok.Data;

@Data
public class Rating {
    private UUID recipeID;
    private UUID userID;
    private Integer rating;
    private String comment;

    /** @return true if this rating is valid */
    @JsonIgnore
    public boolean isValid() {
        return userID != null && recipeID != null && rating != null;
    }
}
