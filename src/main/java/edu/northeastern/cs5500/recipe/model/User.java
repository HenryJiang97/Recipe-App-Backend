package edu.northeastern.cs5500.recipe.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import java.util.Map;
import lombok.Data;
import org.bson.types.ObjectId;

@Data
public class User implements Model {
    private ObjectId id;
    private String userName;
    private String email;
    private String password;
    private List<Recipe> favoriteRecipes;
    private Map<String, String> userPreferences;

    /** Construct a User class */
    // public User() {
    //     this.favoriteRecipes = new ArrayList<>();
    //     this.userPreferences = new HashMap<>();
    // }

    /** @return true if this recipe is valid */
    @JsonIgnore
    public boolean isValid() {
        return userName != null && !userName.isEmpty();
    }

    /** Get the unique id of the user. */
    public ObjectId getId() {
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
    public void setId(ObjectId id) {
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

    /** Add favorite recipe to the favoriteRecipe list of the user. */
    public void addFavoriteRecipe(Recipe recipe) {
        this.favoriteRecipes.add(recipe);
    }
}
