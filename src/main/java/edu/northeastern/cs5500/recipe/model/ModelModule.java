package edu.northeastern.cs5500.recipe.model;

import dagger.Module;
import dagger.Provides;

@Module
public class ModelModule {
    @Provides
    public Class<Recipe> provideRecipeClass() {
        return Recipe.class;
    }

    @Provides
    public Class<User> provideUserClass() {
        return User.class;
    }
}
