package edu.northeastern.cs5500.recipe.controller;

import edu.northeastern.cs5500.recipe.exceptions.DirectionNotFoundException;
import edu.northeastern.cs5500.recipe.exceptions.DuplicateKeyException;
import edu.northeastern.cs5500.recipe.exceptions.NullKeyException;
import edu.northeastern.cs5500.recipe.model.Direction;
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
public class DirectionController implements Controller {
    private Map<UUID, Direction> directions;

    @Inject
    DirectionController() {
        this.directions = new HashMap<>();
    }

    /** Initialize the database and add in default direction samples. */
    @Override
    public void register() {
        log.info("UserController > register");

        // TODO: This should be in a database
        log.info("UserController > register > adding default users");

        final Direction defaultDirection1 = new Direction();
        defaultDirection1.setName("Direction1");

        final Direction defaultDirection2 = new Direction();
        defaultDirection2.setName("Mike");
        defaultDirection2.setTemp(120);

        try {
            addDirection(defaultDirection1);
            addDirection(defaultDirection2);
        } catch (Exception e) {
            log.error("UserController > register > adding default users > failure?");
            e.printStackTrace();
        }
    }

    /**
     * Get a specific direction from the database.
     *
     * @param uuid - the id of the dirction
     */
    @Nullable
    public Direction getDirection(@Nonnull UUID uuid) {
        log.debug("UserController > getUser({})", uuid);
        return directions.containsKey(uuid) ? directions.get(uuid) : null;
    }

    /** Get the entire list of directions from the database. */
    @Nonnull
    public Collection<Direction> getDirections() {
        log.debug("UserController > getUser()");
        if (directions.isEmpty()) {
            return new ArrayList<>();
        }
        return directions.values();
    }

    /**
     * Add new direction to the database.
     *
     * @param direction - the direction needed to be added to the database
     * @return the UUID generated for the new direction
     */
    @Nonnull
    public UUID addDirection(@Nonnull Direction direction) throws Exception {
        log.debug("UserController > addUser(...)");
        final UUID id;
        if (direction.getId() == null) {
            id = UUID.randomUUID();
            direction.setId(id);
        } else {
            id = direction.getId();
        }

        if (directions.containsKey(id)) {
            throw new DuplicateKeyException("DuplicateKeyException");
        }

        directions.put(id, direction);
        return id;
    }

    /**
     * Update the direction to the database.
     *
     * @param direction - the direction needed to be added
     */
    public void updateDirection(@Nonnull Direction direction) throws Exception {
        log.debug("UserController > updateUser(...)");
        final UUID id = direction.getId();
        if (id == null) {
            throw new NullKeyException("NullKeyException");
        }

        if (!directions.containsKey(id)) {
            throw new DirectionNotFoundException("KeyNotFoundException");
        }

        directions.put(id, direction);
    }

    /**
     * Delete the direction from the database.
     *
     * @param id - the unique id of the user needed to be deleted
     */
    public void deleteDirection(@Nonnull UUID id) throws Exception {
        log.debug("UserController > deleteUser(...)");
        if (!directions.containsKey(id)) {
            throw new DirectionNotFoundException("KeyNotFoundException");
        }

        directions.remove(id);
    }
    public int getDirectionTime(Direction direction){
        return direction.getTime();
    }
    public DirectionTypes getDirectionType(Direction direction){
        return direction.getDirectionType();
    }

    /**
     * Change the serving size
     *
     * @param servingSize - the new serving size to be served
     */
    // TODO: Implement after Ingredient class has been created
    // private void changeServingSize(double size) {

    // }

    /**
     * Change temperature according to user preference
     *
     * @param servingSize - the new serving size to be served
     */
    public Recipe changeTemperatureToUserPreference(Map<String, String> preferences)
            throws Exception {
        throw new Exception("This has not been implemented yet");
    }
}
