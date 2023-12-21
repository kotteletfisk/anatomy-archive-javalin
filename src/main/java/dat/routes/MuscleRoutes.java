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
        {   // DEPRECATED
            get("/", muscleController::getAll, Role.ANYONE);
            get("/{name}", muscleController::getLikeName, Role.ANYONE);

            post("/", muscleController::create, Role.ADMIN);
            put("/{id}", muscleController::update, Role.ADMIN);
        };
    }
}
