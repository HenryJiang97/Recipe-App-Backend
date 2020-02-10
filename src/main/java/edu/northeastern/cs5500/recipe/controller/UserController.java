package edu.northeastern.cs5500.recipe.controller;

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
        users = new HashMap<>();
    }

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

    @Nullable
    public User getUser(@Nonnull UUID uuid) {
        // TODO: Should this be null or should this throw an exception?
        log.debug("UserController > getUser({})", uuid);
        return users.get(uuid);
    }

    @Nonnull
    public Collection<User> getUser() {
        log.debug("UserController > getUser()");
        if (users.isEmpty()) {
            return new ArrayList<>();
        }
        return users.values();
    }

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
            // TODO: replace with a real duplicate key exception
            throw new Exception("DuplicateKeyException");
        }

        users.put(id, user);
        return id;
    }

    public void updateUser(@Nonnull User user) throws Exception {
        log.debug("UserController > updateUser(...)");
        final UUID id = user.getId();
        if (id == null) {
            // TODO: replace with a real null key exception
            throw new Exception("NullKeyException");
        }

        if (!user.isValid()) {
            // TODO: replace with a real invalid object exception
            // probably not one exception per object type though...
            throw new Exception("InvalidUserException");
        }

        if (!users.containsKey(id)) {
            // TODO: replace with a real user not found exception
            throw new Exception("KeyNotFoundException");
        }

        users.put(id, user);
    }

    public void deleteUser(@Nonnull UUID id) throws Exception {
        log.debug("UserController > deleteUser(...)");
        if (!users.containsKey(id)) {
            // TODO: replace with a real user not found exception
            throw new Exception("KeyNotFoundException");
        }

        users.remove(id);
    }

    
    // Methods
    private Rating rateRecipe(Recipe recipe) {

    }

    private boolean connectSmartDevices() {
        return true;
    }

    private boolean login() {
        return true;
    }

    private List<Recipe> favoriteRacipes() {
        
    }
}
