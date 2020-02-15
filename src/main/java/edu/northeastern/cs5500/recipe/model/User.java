package edu.northeastern.cs5500.recipe.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.HashMap;
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

    /**
     * Construct a User class
     *
     * @param id - the unique id of current user
     * @param userName - user's user name
     * @param email - user's registered email
     * @param password - user's password
     */
    public User() {
        this.favoriteRecipes = new ArrayList<>();
        this.userPreferences = new HashMap<>();
    }

    /** @return true if this recipe is valid */
    @JsonIgnore
    public boolean isValid() {
        return userName != null && !userName.isEmpty();
    }

    /**
     * Rate the recipe.
     *
     * @param recipe - the recipe to be rated
     * @return a new Rating for the recipe
     */
    // private Rating rateRecipe(Recipe recipe) {

    // }

    /**
     * Connect the smart devices.
     *
     * @return whether the device is connected
     */
    private boolean connectSmartDevices() {
        return true;
    }

    /**
     * Favorite the recipe.
     *
     * @param favoriteRecipe - user's new favorite recipe
     */
    private void favoriteRecipe(Recipe favoriteRecipe) {
        this.favoriteRecipes.add(favoriteRecipe);
    }

    /** Get the unique id of the user. */
    public UUID getId() {
        return this.id;
    }

    /** Get the user name of the user. */
    public String getUserName() {
        return this.userName;
    }

    /** Get the email of the user. */
    public String getEmail() {
        return this.email;
    }

    /** Set the unique id of the user. */
    public void setId(UUID id) {
        this.id = id;
    }

    /** Set the user name of the user. */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /** Set the email of the user. */
    public void setEmail(String email) {
        this.email = email;
    }

    /** Get the password of the user. */
    public void setPassword(String password) {
        this.password = password;
    }
}