package edu.northeastern.cs5500.recipe.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.UUID;
import lombok.Data;

@Data
public class Recipe {
    private UUID id;
    private String title;
    private String description;
    // private Style style;
    // private ArrayList<Rating> ratings;
    // private List<Direction> directions;
    private int yield;
    private int prepTime;
    private int waitTime;
    private int totalTime;
    private ArrayList<String> tag;
    private UUID author;

    /** @return true if this recipe is valid */
    @JsonIgnore
    public boolean isValid() {
        return title != null && !title.isEmpty();
    }
}
