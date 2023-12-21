package dat.routes;

import dat.controller.ExerciseTypeController;
import dat.entities.Role;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class ExerciseTypeRoutes
{
    ExerciseTypeController exerciseTypeController = new ExerciseTypeController();

    public EndpointGroup getRoutes()
    {
        return () ->
        {
            get("/{id}", exerciseTypeController::getById, Role.ANYONE);
            post("/", exerciseTypeController::create, Role.ADMIN);
            put("/{id}", exerciseTypeController::update, Role.ADMIN);
        };
    }
}
