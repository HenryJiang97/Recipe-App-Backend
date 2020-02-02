package edu.northeastern.cs5500.recipe.controller;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

@Singleton
@Slf4j
public class StatusController implements Controller {

    @Inject
    StatusController() {}

    @Nonnull
    public String getStatus() {
        return "OK";
    }

    @Override
    public void register() {
        log.info("StatusController > register");
    }
}
