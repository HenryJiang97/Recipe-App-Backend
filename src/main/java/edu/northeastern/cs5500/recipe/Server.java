/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package edu.northeastern.cs5500.recipe;

import dagger.Component;
import edu.northeastern.cs5500.recipe.controller.Controller;
import edu.northeastern.cs5500.recipe.controller.ControllerModule;
import edu.northeastern.cs5500.recipe.view.View;
import edu.northeastern.cs5500.recipe.view.ViewModule;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Singleton;

@Component(modules = {ControllerModule.class, ViewModule.class})
@Singleton
interface ServerComponent {
    public Server server();
}

public class Server {

    @Inject Set<Controller> controllers;

    @Inject Set<View> views;

    @Inject
    Server() {}

    void start() {
        for (Controller controller : controllers) {
            controller.register();
        }

        for (View view : views) {
            view.register();
        }
    }
}