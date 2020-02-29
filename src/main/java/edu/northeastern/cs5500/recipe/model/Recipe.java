package edu.northeastern.cs5500.recipe.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import java.util.UUID;
import lombok.Data;

@Data
public class Recipe {
    private UUID id;
    private String title;
    private String description;
    // TODO: implement style field when style class is ready private Style style;
    private List<Rating> ratings;
    // TODO: implement direction field when direction class is ready private List<Direction>
    // directions;
    private int yield;
    private int prepTime;
    private int waitTime;
    private int totalTime;
    private List<String> tag;
    private UUID author;

    /** @return true if this recipe is valid */
    @JsonIgnore
    public boolean isValid() {
        return title != null && !title.isEmpty();
    }
}
