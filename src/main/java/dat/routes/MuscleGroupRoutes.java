package dat.routes;

import dat.controller.MuscleGroupController;
import dat.entities.Role;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class MuscleGroupRoutes
{
    MuscleGroupController muscleGroupController = new MuscleGroupController();
    public EndpointGroup getRoutes()
    {
        return () ->
        {
            get("/{id}", muscleGroupController::getById, Role.ANYONE);
            post("/", muscleGroupController::create, Role.ADMIN); // TODO: TEST as admin
            put("/{id}", muscleGroupController::update, Role.ADMIN); // TODO: TEST as admin
        };
    }
}
