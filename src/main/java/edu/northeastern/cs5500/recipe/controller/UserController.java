package edu.northeastern.cs5500.recipe.controller;

import edu.northeastern.cs5500.recipe.exceptions.DuplicateKeyException;
import edu.northeastern.cs5500.recipe.exceptions.UserNotFoundException;
import edu.northeastern.cs5500.recipe.model.Recipe;
import edu.northeastern.cs5500.recipe.model.User;
import edu.northeastern.cs5500.recipe.repository.GenericRepository;
import java.io.InvalidObjectException;
import java.util.Collection;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;

@Singleton
@Slf4j
public class UserController {
    public final GenericRepository<User> users;

    @Inject
    UserController(GenericRepository<User> userRepository) {
        users = userRepository;

        /** Initialize the database and add in default user samples. */
        log.info("UserController > register");

        log.info("UserController > register > adding default users");

        final User defaultUser1 = new User();
        defaultUser1.setUserName("Jack");

        final User defaultUser2 = new User();
        defaultUser2.setUserName("Mike");
        defaultUser2.setEmail("mike@gmail.com");

        try {
            addUser(defaultUser1);
            addUser(defaultUser2);
        } catch (Exception e) {
            log.error("UserController > register > adding default users > failure?");
            e.printStackTrace();
        }
    }

    /**
     * Get a specific user from the database.
     *
     * @param uuid - the user id of the user
     */
    @Nullable
    public User getUser(@Nonnull ObjectId uuid) {
        log.debug("UserController > getUser({})", uuid);
        return users.get(uuid);
    }

    /** Get the entire list of users from the database. */
    @Nonnull
    public Collection<User> getUsers() {
        log.debug("UserController > getUser()");
        return users.getAll();
    }

    /**
     * Add new user to the database.
     *
     * @param user - the user needed to be added to the database
     * @return the UUID generated for the new user
     */
    @Nonnull
    public User addUser(@Nonnull User user) throws Exception {
        log.debug("UserController > addUser(...)");
        if (!user.isValid()) {
            throw new InvalidObjectException("Invalid User");
        }
        ObjectId id = user.getId();

        if (id != null && users.get(id) != null) {
            throw new DuplicateKeyException("DuplicateKeyException");
        }

        return users.add(user);
    }

    /**
     * Update the user to the database.
     *
     * @param user - the user needed to be added
     */
    public void updateUser(@Nonnull User user) throws Exception {
        log.debug("UserController > updateUser(...)");

        users.update(user);
    }

    /**
     * Delete the user from the database.
     *
     * @param id - the unique id of the user needed to be deleted
     */
    public void deleteUser(@Nonnull ObjectId id) throws Exception {
        log.debug("UserController > deleteUser(...)");
        if (users.get(id) == null) {
            throw new UserNotFoundException("KeyNotFoundException");
        }

        users.delete(id);
    }

    /**
     * Rate the recipe.
     *
     * @param recipe - the recipe to be rated
     * @return a new Rating for the recipe
     */
    // TODO: Implement after Rating class has been created
    // private Rating rateRecipe(User user, Recipe recipe) {

    // }

    /**
     * Connect the smart devices.
     *
     * @return whether the device is connected
     */
    private boolean connectSmartDevices(User user) {
        return true;
    }

    /**
     * Favorite the recipe.
     *
     * @param favoriteRecipe - user's new favorite recipe
     */
    private void favoriteRecipe(User user, Recipe favoriteRecipe) {
        user.addFavoriteRecipe(favoriteRecipe);
    }
}
