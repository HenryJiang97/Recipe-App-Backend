package edu.northeastern.cs5500.recipe.controller;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoSet;

@Module
public class ControllerModule {
    @Provides
    @IntoSet
    public Controller provideRecipeController(RecipeController recipeController) {
        return recipeController;
    }

    @Provides
    @IntoSet
    public Controller provideStatusController(StatusController statusController) {
        return statusController;
    }
}
