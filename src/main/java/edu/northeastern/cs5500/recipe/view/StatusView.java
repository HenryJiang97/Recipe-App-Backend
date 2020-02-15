package edu.northeastern.cs5500.recipe.view;

import static spark.Spark.get;

import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

@Singleton
@Slf4j
public class StatusView implements View {

    @Inject
    StatusView() {}

    @Override
    public void register() {
        log.info("StatusView > register");
        get(
                "/",
                (request, response) -> {
                    log.debug("/");
                    response.type("application/json");
                    return "";
                });
    }
}
