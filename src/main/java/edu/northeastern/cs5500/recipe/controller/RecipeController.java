package edu.northeastern.cs5500.recipe.controller;

import edu.northeastern.cs5500.recipe.Exceptions.DuplicateKeyException;
import edu.northeastern.cs5500.recipe.Exceptions.KeyNotFoundException;
import edu.northeastern.cs5500.recipe.Exceptions.NullKeyException;
import edu.northeastern.cs5500.recipe.model.Recipe;
import java.io.InvalidObjectException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

@Singleton
@Slf4j
public class RecipeController implements Controller {
    private Map<UUID, Recipe> recipes;
    protected static final String invalidRecipe =
            "Recipe is not valid. Please replace with valid recipe";
    protected static final String duplicateKey =
            "Key is a duplicate, Please replace with different key";
    protected static final String nullKey = "Please enter a key! Cannot search no Key!";
    protected static final String keyNotFound = "Key was not found please enter a real key!";

    @Inject
    RecipeController() {
        recipes = new HashMap<>();
    }

    @Override
    public void register() {
        log.info("RecipeController > register");

        // TODO: This should be in a database
        log.info("RecipeController > register > adding default recipes");
        final Recipe defaultRecipe1 = new Recipe();
        defaultRecipe1.setTitle("Hot dog");

        final Recipe defaultRecipe2 = new Recipe();
        defaultRecipe2.setTitle("A steak");
        defaultRecipe2.setDescription("Not a hot dog");

        try {
            addRecipe(defaultRecipe1);
            addRecipe(defaultRecipe2);
        } catch (Exception e) {
            log.error("RecipeController > register > adding default recipes > failure?");
            e.printStackTrace();
        }
    }

    @Nullable
    public Recipe getRecipe(@Nonnull UUID uuid) {
        log.debug("RecipeController > getRecipe({})", uuid);
        return recipes.get(uuid);
    }

    @Nonnull
    public Collection<Recipe> getRecipes() {
        log.debug("RecipeController > getRecipes()");
        if (recipes.isEmpty()) {
            return new ArrayList<>();
        }
        return recipes.values();
    }

    @Nonnull
    public UUID addRecipe(@Nonnull Recipe recipe) throws Exception {
        log.debug("RecipeController > addRecipe(...)");
        final UUID id;
        if (recipe.getId() == null) {
            id = UUID.randomUUID();
            recipe.setId(id);
        } else {
            id = recipe.getId();
        }

        if (!recipe.isValid()) {
            throw new InvalidObjectException(invalidRecipe);
        }

        if (recipes.containsKey(id)) {
            throw new DuplicateKeyException(duplicateKey);
        }

        recipes.put(id, recipe);
        return id;
    }

    public void updateRecipe(@Nonnull Recipe recipe) throws Exception {
        log.debug("RecipeController > updateRecipe(...)");
        final UUID id = recipe.getId();
        if (id == null) {
            throw new NullKeyException(nullKey);
        }

        if (!recipe.isValid()) {
            throw new InvalidObjectException(invalidRecipe);
        }

        if (!recipes.containsKey(id)) {
            throw new KeyNotFoundException(keyNotFound);
        }

        recipes.put(id, recipe);
    }

    public void deleteRecipe(@Nonnull UUID id) throws Exception {
        log.debug("RecipeController > deleteRecipe(...)");
        if (!recipes.containsKey(id)) {
            throw new KeyNotFoundException(keyNotFound);
        }

        recipes.remove(id);
    }

    public double updateAverageRating(@Nonnull Recipe recipe) {
        /*

        TODO: Needs to be implemented by relies on direction sections of the code
        listOfRatings = recipe.getRatings();
        for( Rating rating : listOfRatings){
            totalRating =+ rating.getRating()
        }
            return totalRating/listOfRating.size()
        listOfRatings
        but probably update each time a rating is added instead
        so it would be
        totalRating = recipe.getAverageRating*listOfRating.size() + new rating
        recipe.getRatings.add(newRating)
        newAverageRating = totalRating/listOfRating.size()
        recipe.setAverageRating(newAverageRating)
        signature would also need a Rating (the new rating) as a input

        */
        return 0.0;
    }

    public Recipe changeRecipeToUserPreferences(Map<String, String> preferences) throws Exception {
        throw new Exception("This has not been implemented yet");
    }

    private int computeTime(@Nonnull Recipe recipe) {
        // TODO: Needs to be implemented by relies on direction section of the code
        // List of Directions takes in the time property of each direction and checks its enum to
        // see if it is the enum of what i want to compute if it is then add it the total time.
        // Enum directionType
        return 0;
    }

    public int getTotalTime(@Nonnull Recipe recipe) {
        // TODO: Needs to be implemented by relies on direction sections of the code
        // This will also need a parameter enum also maybe should check if this already created for
        return computeTime(recipe);
    }
}
