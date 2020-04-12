package edu.northeastern.cs5500.recipe.view;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.halt;
import static spark.Spark.post;
import static spark.Spark.put;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.northeastern.cs5500.recipe.JsonTransformer;
import edu.northeastern.cs5500.recipe.controller.UserController;
import edu.northeastern.cs5500.recipe.model.User;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;

@Singleton
@Slf4j
public class UserView implements View {

    @Inject
    UserView() {}

    @Inject JsonTransformer jsonTransformer;

    @Inject UserController userController;

    @Override
    public void register() {
        log.info("UserView > register");

        get(
                "/user",
                (request, response) -> {
                    log.debug("/user");
                    response.type("application/json");
                    return userController.getUsers();
                },
                jsonTransformer);

        get(
                "/user/:id",
                (request, response) -> {
                    final String paramId = request.params(":id");
                    log.debug("/recipe/:id<{}>", paramId);
                    final ObjectId id = new ObjectId(paramId);
                    User user = userController.getUser(id);
                    if (user == null) {
                        halt(404);
                    }
                    response.type("application/json");
                    return user;
                },
                jsonTransformer);

        post(
                "/user",
                (request, response) -> {
                    ObjectMapper mapper = new ObjectMapper();
                    User user = mapper.readValue(request.body(), User.class);
                    if (!user.isValid()) {
                        response.status(400);
                        return "";
                    }

                    // Ignore the user-provided ID if there is one
                    user.setId(null);
                    user = userController.addUser(user);

                    response.redirect(
                            String.format("/user/{}", user.getId().toHexString()), 301);
                    return user;
                });

        put(
                "/user",
                (request, response) -> {
                    ObjectMapper mapper = new ObjectMapper();
                    User user = mapper.readValue(request.body(), User.class);
                    if (!user.isValid()) {
                        response.status(400);
                        return "";
                    }

                    userController.updateUser(user);
                    return user;
                });

        delete(
                "/user",
                (request, response) -> {
                    ObjectMapper mapper = new ObjectMapper();
                    User user = mapper.readValue(request.body(), User.class);

                    userController.deleteUser(user.getId());
                    return user;
                });
    }
}