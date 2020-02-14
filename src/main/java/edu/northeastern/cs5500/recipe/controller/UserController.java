package edu.northeastern.cs5500.recipe.controller;

import edu.northeastern.cs5500.recipe.model.User;
import java.util.List;
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
    UserController(User user) {
        this.users = new HashMap<>();
    }

    /**
    * Initialize the database and add in default user samples.
    */
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
        // TODO: Should this be null or should this throw an exception?
        log.debug("UserController > getUser({})", uuid);
        return users.get(uuid);
    }

    /**
    * Get the entire list of users from the database.
    */
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
            // TODO: replace with a real duplicate key exception
            throw new Exception("DuplicateKeyException");
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

    /**
    * Delete the user from the database.
    *
    * @param id - the unique id of the user needed to be deleted
    */
    public void deleteUser(@Nonnull UUID id) throws Exception {
        log.debug("UserController > deleteUser(...)");
        if (!users.containsKey(id)) {
            // TODO: replace with a real user not found exception
            throw new Exception("KeyNotFoundException");
        }

        users.remove(id);
    }
}
