package edu.northeastern.cs5500.recipe.repository;

import dagger.Module;
import dagger.Provides;
import edu.northeastern.cs5500.recipe.model.Recipe;
import edu.northeastern.cs5500.recipe.model.User;
import edu.northeastern.cs5500.recipe.service.MongoDBService;

@Module
public class RepositoryModule {
    @Provides
    public GenericRepository<Recipe> provideRecipeRepository(MongoDBService mongoDBService) {
        return new MongoDBRepository<>(Recipe.class, mongoDBService);
    }

    @Provides
    public GenericRepository<User> provideUserRepository(MongoDBService mongoDBService) {
        return new MongoDBRepository<>(User.class, mongoDBService);
    }
}
