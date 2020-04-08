package edu.northeastern.cs5500.recipe.view;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.halt;
import static spark.Spark.post;
import static spark.Spark.put;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.northeastern.cs5500.recipe.JsonTransformer;
import edu.northeastern.cs5500.recipe.controller.RecipeController;
import edu.northeastern.cs5500.recipe.model.Recipe;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;

@Singleton
@Slf4j
public class RecipeView implements View {

    @Inject
    RecipeView() {}

    @Inject JsonTransformer jsonTransformer;

    @Inject RecipeController recipeController;

    @Override
    public void register() {
        log.info("RecipeView > register");

        get(
                "/recipe",
                (request, response) -> {
                    log.debug("/recipe");
                    response.type("application/json");
                    return recipeController.getRecipes();
                },
                jsonTransformer);

        get(
                "/recipe/:id",
                (request, response) -> {
                    final String paramId = request.params(":id");
                    log.debug("/recipe/:id<{}>", paramId);
                    final ObjectId id = new ObjectId(paramId);
                    Recipe recipe = recipeController.getRecipe(id);
                    if (recipe == null) {
                        halt(404);
                    }
                    response.type("application/json");
                    return recipe;
                },
                jsonTransformer);

        post(
                "/recipe",
                (request, response) -> {
                    ObjectMapper mapper = new ObjectMapper();
                    Recipe recipe = mapper.readValue(request.body(), Recipe.class);
                    if (!recipe.isValid()) {
                        response.status(400);
                        return "";
                    }

                    // Ignore the user-provided ID if there is one
                    recipe.setId(null);
                    recipe = recipeController.addRecipe(recipe);

                    response.redirect(
                            String.format("/recipe/{}", recipe.getId().toHexString()), 301);
                    return recipe;
                });

        put(
                "/recipe",
                (request, response) -> {
                    ObjectMapper mapper = new ObjectMapper();
                    Recipe recipe = mapper.readValue(request.body(), Recipe.class);
                    if (!recipe.isValid()) {
                        response.status(400);
                        return "";
                    }

                    recipeController.updateRecipe(recipe);
                    return recipe;
                });

        delete(
                "/recipe",
                (request, response) -> {
                    ObjectMapper mapper = new ObjectMapper();
                    Recipe recipe = mapper.readValue(request.body(), Recipe.class);

                    recipeController.deleteRecipe(recipe.getId());
                    return recipe;
                });
    }
}
