package edu.northeastern.cs5500.recipe.view;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoSet;

@Module
public class ViewModule {
    @Provides
    @IntoSet
    public View provideRecipeView(RecipeView recipeView) {
        return recipeView;
    }

    @Provides
    @IntoSet
    public View provideStatusView(StatusView statusView) {
        return statusView;
    }
}
