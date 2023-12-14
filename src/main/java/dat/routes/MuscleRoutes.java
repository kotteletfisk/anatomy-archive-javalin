package dat.routes;

import dat.controller.MuscleController;
import dat.entities.Role;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.post;
import static io.javalin.apibuilder.ApiBuilder.get;


public class MuscleRoutes
{
    MuscleController muscleController = new MuscleController();

    public EndpointGroup getRoutes()
    {
        return () ->
        {
            get("/", muscleController::getAll, Role.ANYONE);
        };
    }
}
