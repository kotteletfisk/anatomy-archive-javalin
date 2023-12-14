package dat.routes;

import dat.controller.ExerciseController;
import dat.entities.Role;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.path;

public class SearchRoutes
{
    ExerciseController exerciseController = new ExerciseController();

    public EndpointGroup getRoutes()
    {
        return () ->
        {
            path("/exercise", () ->
            {
                get("/byMuscle", exerciseController::getByMusclePattern, Role.ANYONE);
                get("/byEquipment", exerciseController::getByEquipmentPattern, Role.ANYONE);
                get("/byName", exerciseController::getByNamePattern, Role.ANYONE);
            });
        };
    }
}
