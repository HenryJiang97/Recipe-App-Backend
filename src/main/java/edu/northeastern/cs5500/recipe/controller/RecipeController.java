package edu.northeastern.cs5500.recipe.controller;

import edu.northeastern.cs5500.recipe.exceptions.DuplicateKeyException;
import edu.northeastern.cs5500.recipe.exceptions.KeyNotFoundException;
import edu.northeastern.cs5500.recipe.exceptions.NullKeyException;
import edu.northeastern.cs5500.recipe.model.Direction;
import edu.northeastern.cs5500.recipe.model.Rating;
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
    /** Creates default recipes and puts them in database. */
    @Override
    public void register() {
        log.info("RecipeController > register");

        // TODO: This should be in a database
        log.info("RecipeController > register > adding default recipes");
        final Recipe defaultRecipe1 = new Recipe();
        defaultRecipe1.setTitle("Chicken wings");

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
    /** Gets recipe from database based on UUID. */
    @Nullable
    public Recipe getRecipe(@Nonnull UUID uuid) {
        log.debug("RecipeController > getRecipe({})", uuid);
        return recipes.get(uuid);
    }
    /** Gets all recipes from database. */
    @Nonnull
    public Collection<Recipe> getRecipes() {
        log.debug("RecipeController > getRecipes()");
        if (recipes.isEmpty()) {
            return new ArrayList<>();
        }
        return recipes.values();
    }
    /**
     * Adds a recipe to the database and makes sure that recipes times are updated.
     *
     * @param recipe - A new recipe to be added.
     * @return - The recipe ID of the recipe added.
     * @throws Exception - Invalid Recipe if recipe is not valid or DuplicateKeyException if recipe
     *     somehow has duplicate UUID
     */
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
        this.computeTime(recipe);
        recipes.put(id, recipe);
        return id;
    }
    /**
     * Updates recipe based on UUID
     *
     * @param recipe - Takes new recipe info to update recipe too.
     * @throws Exception - InvalidObjectException If recipe is not valid or
     * @throws KeyNotFoundException - If key is not vound
     */
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
        this.computeTime(recipe);
        recipes.put(id, recipe);
    }
    /**
     * Deletes a recipe
     *
     * @throws KeyNotFoundException - If recipe UUID is not found
     */
    public void deleteRecipe(@Nonnull UUID id) throws Exception {
        log.debug("RecipeController > deleteRecipe(...)");
        if (!recipes.containsKey(id)) {
            throw new KeyNotFoundException(keyNotFound);
        }

        recipes.remove(id);
    }
    /**
     * Updates average rating for a recipe. Sets average rating in recipe body but also returns new
     * average rating.
     *
     * @param recipe - Recipe average rating to be updated
     * @param rating - New rating to to add to recipe's average
     * @return - New Average rating
     */
    protected double updateAverageRating(@Nonnull Recipe recipe, Rating rating) {

        int numberRatings = recipe.getRatings().size();
        int averageRating = recipe.getAverageRating();
        int newAverageRating = 0;
        if (averageRating == 0) {
            newAverageRating = rating.getRating();
        } else {
            newAverageRating =
                    (averageRating * numberRatings + rating.getRating()) / (numberRatings + 1);
        }
        recipe.setAverageRating(newAverageRating);
        return newAverageRating;
    }
    /**
     * This doesnt do anything
     *
     * @param preferences
     * @return
     * @throws Exception
     */
    public Recipe changeRecipeToUserPreferences(Map<String, String> preferences) throws Exception {
        throw new Exception("This has not been implemented yet");
    }
    /**
     * Computes time for recipe and updates those fields in recipe through setting.
     *
     * @param recipe - Recipe times to be computed
     * @return A map of key "COOK" cookTime "WAIT" waitTime and "PREP" prepTime
     */
    private Map<String, Integer> computeTime(@Nonnull Recipe recipe) {
        int cookTime = 0;
        int waitTime = 0;
        int prepTime = 0;
        Map<String, Integer> recipeTimeMap = new HashMap<String, Integer>();
        if (recipe.getDirections() != null) {

            for (Direction curDirection : recipe.getDirections()) {
                switch (curDirection.getDirectionType()) {
                    case COOK:
                        cookTime = cookTime + curDirection.getTime();
                        break;
                    case PREP:
                        prepTime = prepTime + curDirection.getTime();
                        break;
                    case WAIT:
                        waitTime = prepTime + curDirection.getTemp();
                        break;
                    default:
                        log.debug("computeTime some how ended in non enum case");
                        break;
                }
            }
        }

        recipeTimeMap.put("COOK", cookTime);
        recipeTimeMap.put("WAIT", waitTime);
        recipeTimeMap.put("PREP", prepTime);
        recipe.setWaitTime(waitTime);
        recipe.setPrepTime(prepTime);
        recipe.setCookTime(cookTime);
        int totalTime = waitTime + prepTime + cookTime;
        recipe.setTotalTime(totalTime);
        return recipeTimeMap;
    }
}
