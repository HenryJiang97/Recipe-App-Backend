package edu.northeastern.cs5500.recipe.controller;

import edu.northeastern.cs5500.recipe.exceptions.DuplicateKeyException;
import edu.northeastern.cs5500.recipe.exceptions.KeyNotFoundException;
import edu.northeastern.cs5500.recipe.model.Direction;
import edu.northeastern.cs5500.recipe.model.Direction.DirectionTypes;
import edu.northeastern.cs5500.recipe.model.Ingredient;
import edu.northeastern.cs5500.recipe.model.Rating;
import edu.northeastern.cs5500.recipe.model.Recipe;
import edu.northeastern.cs5500.recipe.model.Unit;
import edu.northeastern.cs5500.recipe.repository.GenericRepository;
import java.io.InvalidObjectException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;

@Singleton
@Slf4j
public class RecipeController {
    public final GenericRepository<Recipe> recipes;
    protected static final String invalidRecipe =
            "Recipe is not valid. Please replace with valid recipe";
    protected static final String duplicateKey =
            "Key is a duplicate, Please replace with different key";
    protected static final String nullKey = "Please enter a key! Cannot search no Key!";
    protected static final String keyNotFound = "Key was not found please enter a real key!";

    @Inject
    RecipeController(GenericRepository<Recipe> recipeRepository) {
        recipes = recipeRepository;

        log.info("RecipeController > construct");

        if (recipes.count() > 0) {
            return;
        }

        /** Creates default recipes and puts them in database. */
        final Recipe defaultRecipe1 = new Recipe();
        defaultRecipe1.setTitle("Chicken wings");

        final Recipe defaultRecipe2 = new Recipe();
        defaultRecipe2.setTitle("A steak");
        defaultRecipe2.setDescription("Not a hot dog");

        final Recipe defaultRecipe3 = new Recipe();
        defaultRecipe3.setTitle("Creamy Chickpea Pasta With Spinach and Rosemary");
        defaultRecipe3.setDescription(
                "Luxurious and hearty, cheap and easy, this vegetarian pasta uses mostly pantry staples, requiring just a few fresh ingredients, like baby spinach, rosemary and heavy cream. Canned chickpeas form the foundation of the dish: They’re cooked until crisp and caramelized. Half are then saved as a garnish, while the rest are simmered until they break down and thicken the sauce. You can swap out your greens or beans, and if you want to experiment with flavor, raid your spice cabinet: Ground coriander, toasted fennel seeds, coarsely crumbled pink peppercorns or a sprinkle of smoked paprika perk up the dish.");
        defaultRecipe3.setYield(4);

        final Direction directionStep1 = new Direction();
        directionStep1.setName("Step 1");
        directionStep1.setDescription("Bring a large pot of salted water to a boil over high.");
        directionStep1.setDirectionType(DirectionTypes.PREP);
        directionStep1.setTime(200);
        final Direction directionStep2 = new Direction();
        directionStep2.setName("Step 2");
        directionStep2.setDescription(
                "In a wide, deep skillet, heat the oil over medium-high. Add the chickpeas, rosemary and Aleppo pepper, if using. Season generously with salt and pepper, and cook, stirring occasionally, until chickpeas start to caramelize at their edges and pop, 5 to 7 minutes. Using a slotted spoon, transfer about half the chickpeas to a bowl. Reserve for garnish.");
        directionStep2.setDirectionType(DirectionTypes.COOK);
        directionStep2.setTime(300);
        ArrayList<Ingredient> direction2Ingridents = new ArrayList<>();
        final Ingredient ingredient1 = new Ingredient();
        ingredient1.setAmount(.25);
        ingredient1.setName("olive oil");
        ingredient1.setUnit(Unit.CUP);
        final Ingredient ingredient2 = new Ingredient();
        ingredient2.setAmount(14.0);
        ingredient2.setName("chickpeas");
        ingredient2.setUnit(Unit.OUNCE);
        final Ingredient ingredient3 = new Ingredient();
        ingredient3.setAmount(2.0);
        ingredient3.setName("rosemary");
        ingredient3.setUnit(Unit.TEASPOON);
        final Ingredient ingredient4 = new Ingredient();
        ingredient4.setAmount(.5);
        ingredient4.setName("Aleppo Pepper");
        ingredient4.setUnit(Unit.TEASPOON);
        direction2Ingridents.add(ingredient1);
        direction2Ingridents.add(ingredient2);
        direction2Ingridents.add(ingredient3);
        direction2Ingridents.add(ingredient4);
        directionStep2.setIngredients(direction2Ingridents);
        ArrayList<Direction> directions = new ArrayList<>();
        directions.add(directionStep1);
        directions.add(directionStep2);
        defaultRecipe3.setDirections(directions);
        try {
            addRecipe(defaultRecipe1);
            addRecipe(defaultRecipe2);
            addRecipe(defaultRecipe3);
        } catch (Exception e) {
            log.error("RecipeController > construct > adding default recipes > failure?");
            e.printStackTrace();
        }
    }
    /** Gets recipe from database based on UUID. */
    @Nullable
    public Recipe getRecipe(@Nonnull ObjectId uuid) {
        log.debug("RecipeController > getRecipe({})", uuid);
        return recipes.get(uuid);
    }
    /** Gets all recipes from database. */
    @Nonnull
    public Collection<Recipe> getRecipes() {
        log.debug("RecipeController > getRecipes()");
        return recipes.getAll();
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
    public Recipe addRecipe(@Nonnull Recipe recipe) throws Exception {
        log.debug("RecipeController > addRecipe(...)");

        if (!recipe.isValid()) {
            throw new InvalidObjectException(invalidRecipe);
        }

        ObjectId id = recipe.getId();

        if (id != null && recipes.get(id) != null) {
            throw new DuplicateKeyException(duplicateKey);
        }
        this.computeTime(recipe);
        return recipes.add(recipe);
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
        this.computeTime(recipe);
        recipes.update(recipe);
    }
    /**
     * Deletes a recipe
     *
     * @throws KeyNotFoundException - If recipe UUID is not found
     */
    public void deleteRecipe(@Nonnull ObjectId id) throws Exception {
        recipes.delete(id);
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
