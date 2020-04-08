package edu.northeastern.cs5500.recipe.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.northeastern.cs5500.recipe.model.Style.*;
import java.util.List;
import java.util.UUID;
import lombok.Data;
import org.bson.types.ObjectId;

@Data
public class Recipe implements Model {
    private ObjectId id;
    private String title;
    private String description;
    private Style style;
    private List<Rating> ratings;
    private List<Direction> directions;
    private int averageRating;
    private int yield;
    private int prepTime;
    private int waitTime;
    private int totalTime;
    private int cookTime;
    private List<String> tag;
    private UUID author;

    /** @return true if this recipe is valid */
    @JsonIgnore
    public boolean isValid() {
        return title != null && !title.isEmpty();
    }
}
