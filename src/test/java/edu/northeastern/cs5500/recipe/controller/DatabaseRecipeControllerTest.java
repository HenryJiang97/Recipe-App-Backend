/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package edu.northeastern.cs5500.recipe.controller;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

import edu.northeastern.cs5500.recipe.model.Recipe;
import edu.northeastern.cs5500.recipe.repository.MongoDBRepository;
import edu.northeastern.cs5500.recipe.service.MongoDBService;
import org.junit.jupiter.api.Test;

/** Note that this test suite will fail with timeout exceptions if mongodb is not running. */
class DatabaseRecipeControllerTest {
    static MongoDBService mongoDBService;

    RecipeController getRecipeController() {
        if (mongoDBService == null) {
            mongoDBService = new MongoDBService();
        }
        return new RecipeController(new MongoDBRepository<Recipe>(Recipe.class, mongoDBService));
    }

    @Test
    void testRegisterCreatesRecipes() {
        RecipeController recipeController = getRecipeController();
        assertThat(recipeController.getRecipes()).isNotEmpty();
    }

    @Test
    void testRegisterCreatesValidRecipes() {
        RecipeController recipeController = getRecipeController();

        for (Recipe recipe : recipeController.getRecipes()) {
            assertWithMessage(recipe.getTitle()).that(recipe.isValid()).isTrue();
        }
    }

    @Test
    void testCanAddRecipe() throws Exception {
        RecipeController recipeController = getRecipeController();
        recipeController.recipes.dropAll();
        final Recipe defaultRecipe1 = new Recipe();
        defaultRecipe1.setTitle("A frog");
        defaultRecipe1.setCookTime(0);
        defaultRecipe1.setWaitTime(0);
        defaultRecipe1.setPrepTime(0);
        defaultRecipe1.setTotalTime(0);
        Recipe test_recipe = recipeController.addRecipe(defaultRecipe1);

        final Recipe defaultRecipe2 = new Recipe();
        defaultRecipe2.setTitle("Hot ");
        System.out.println(defaultRecipe1.getId());
        for (Recipe recipe : recipeController.getRecipes()) {
            assertThat(recipe).isEqualTo(defaultRecipe1);
        }

        assertThat(test_recipe).isEqualTo(defaultRecipe1);
    }

    @Test
    void testCanReplaceRecipe() throws Exception {
        RecipeController recipeController = getRecipeController();
        final Recipe defaultRecipe1 = new Recipe();
        defaultRecipe1.setTitle("Hot dog");
        recipeController.addRecipe(defaultRecipe1);

        final Recipe defaultRecipe2 = new Recipe();
        defaultRecipe2.setTitle("Cold");
        defaultRecipe2.setId(defaultRecipe1.getId());
        assertThat(recipeController.getRecipe(defaultRecipe1.getId())).isEqualTo(defaultRecipe1);
        recipeController.updateRecipe(defaultRecipe2);
        assertThat(recipeController.getRecipe(defaultRecipe1.getId())).isEqualTo(defaultRecipe2);
    }

    @Test
    void testCanDeleteRecipe() throws Exception {
        RecipeController recipeController = getRecipeController();
        final Recipe defaultRecipe1 = new Recipe();
        defaultRecipe1.setTitle("Hot dog");
        recipeController.addRecipe(defaultRecipe1);

        final Recipe defaultRecipe2 = new Recipe();
        defaultRecipe2.setTitle("Cold");
        recipeController.addRecipe(defaultRecipe2);
        recipeController.deleteRecipe(defaultRecipe1.getId());
        assertThat(recipeController.getRecipe(defaultRecipe1.getId())).isEqualTo(null);
        assertThat(recipeController.getRecipe(defaultRecipe2.getId())).isEqualTo(defaultRecipe2);
    }
}
