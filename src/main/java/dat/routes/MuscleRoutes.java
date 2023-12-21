package dat.routes;

import dat.controller.MuscleController;
import dat.entities.Role;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;


public class MuscleRoutes
{
    MuscleController muscleController = new MuscleController();

    public EndpointGroup getRoutes()
    {
        return () ->
        {
            get("/{id}", muscleController::getById, Role.ANYONE);
            post("/", muscleController::create, Role.ADMIN);
            put("/{id}", muscleController::update, Role.ADMIN);
        };
    }
}
