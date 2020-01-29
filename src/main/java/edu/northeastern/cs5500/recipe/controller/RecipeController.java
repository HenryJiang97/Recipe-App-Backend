package edu.northeastern.cs5500.recipe.controller;

import edu.northeastern.cs5500.recipe.model.Recipe;
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
        // TODO: Should this be null or should this throw an exception?
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
            // TODO: replace with a real invalid object exception
            // probably not one exception per object type though...
            throw new Exception("InvalidRecipeException");
        }

        if (recipes.containsKey(id)) {
            // TODO: replace with a real duplicate key exception
            throw new Exception("DuplicateKeyException");
        }

        recipes.put(id, recipe);
        return id;
    }

    public void updateRecipe(@Nonnull Recipe recipe) throws Exception {
        log.debug("RecipeController > updateRecipe(...)");
        final UUID id = recipe.getId();
        if (id == null) {
            // TODO: replace with a real null key exception
            throw new Exception("NullKeyException");
        }

        if (!recipe.isValid()) {
            // TODO: replace with a real invalid object exception
            // probably not one exception per object type though...
            throw new Exception("InvalidRecipeException");
        }

        if (!recipes.containsKey(id)) {
            // TODO: replace with a real recipe not found exception
            throw new Exception("KeyNotFoundException");
        }

        recipes.put(id, recipe);
    }

    public void deleteRecipe(@Nonnull UUID id) throws Exception {
        log.debug("RecipeController > deleteRecipe(...)");
        if (!recipes.containsKey(id)) {
            // TODO: replace with a real recipe not found exception
            throw new Exception("KeyNotFoundException");
        }

        recipes.remove(id);
    }
}
