package edu.northeastern.cs5500.recipe.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.Data;

@Data
public class User {
    private UUID id;
    private String userName;
    private String email;
    private String password;
    private List<Recipe> favoriteRecipes;
    private Map<String, String> userPreferences;

    /** @return true if this recipe is valid */
    @JsonIgnore
    public boolean isValid() {
        return userName != null && !userName.isEmpty();
    }
}
