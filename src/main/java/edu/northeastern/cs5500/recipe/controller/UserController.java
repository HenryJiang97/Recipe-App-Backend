package edu.northeastern.cs5500.recipe.controller;

import edu.northeastern.cs5500.recipe.Exceptions.DuplicateKeyException;
import edu.northeastern.cs5500.recipe.Exceptions.InvalidUserException;
import edu.northeastern.cs5500.recipe.Exceptions.NullKeyException;
import edu.northeastern.cs5500.recipe.Exceptions.UserNotFoundException;
import edu.northeastern.cs5500.recipe.model.Recipe;
import edu.northeastern.cs5500.recipe.model.User;
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
public class UserController implements Controller {
    private Map<UUID, User> users;

    @Inject
    UserController() {
        this.users = new HashMap<>();
    }

    /** Initialize the database and add in default user samples. */
    @Override
    public void register() {
        log.info("UserController > register");

        // TODO: This should be in a database
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
    public User getUser(@Nonnull UUID uuid) {
        log.debug("UserController > getUser({})", uuid);
        return users.containsKey(uuid) ? users.get(uuid) : null;
    }

    /** Get the entire list of users from the database. */
    @Nonnull
    public Collection<User> getUsers() {
        log.debug("UserController > getUser()");
        if (users.isEmpty()) {
            return new ArrayList<>();
        }
        return users.values();
    }

    /**
     * Add new user to the database.
     *
     * @param user - the user needed to be added to the database
     * @return the UUID generated for the new user
     */
    @Nonnull
    public UUID addUser(@Nonnull User user) throws Exception {
        log.debug("UserController > addUser(...)");
        final UUID id;
        if (user.getId() == null) {
            id = UUID.randomUUID();
            user.setId(id);
        } else {
            id = user.getId();
        }

        if (users.containsKey(id)) {
            throw new DuplicateKeyException("DuplicateKeyException");
        }

        users.put(id, user);
        return id;
    }

    /**
     * Update the user to the database.
     *
     * @param user - the user needed to be added
     */
    public void updateUser(@Nonnull User user) throws Exception {
        log.debug("UserController > updateUser(...)");
        final UUID id = user.getId();
        if (id == null) {
            throw new NullKeyException("NullKeyException");
        }

        if (!user.isValid()) {
            throw new InvalidUserException("InvalidUserException");
        }

        if (!users.containsKey(id)) {
            throw new UserNotFoundException("KeyNotFoundException");
        }

        users.put(id, user);
    }

    /**
     * Delete the user from the database.
     *
     * @param id - the unique id of the user needed to be deleted
     */
    public void deleteUser(@Nonnull UUID id) throws Exception {
        log.debug("UserController > deleteUser(...)");
        if (!users.containsKey(id)) {
            throw new UserNotFoundException("KeyNotFoundException");
        }

        users.remove(id);
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
