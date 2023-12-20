package dat.routes;

import dat.controller.*;
import dat.entities.Role;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.path;

public class SearchRoutes
{
    ExerciseController exerciseController = new ExerciseController();
    MuscleController muscleController = new MuscleController();
    EquipmentController equipmentController = new EquipmentController();
    ExerciseTypeController exerciseTypeController = new ExerciseTypeController();
    MuscleGroupController muscleGroupController = new MuscleGroupController();

    public EndpointGroup getRoutes()
    {
        return () ->
        {
            path("/exercise", () ->
            {
                get("/byMuscle", exerciseController::getByMusclePattern, Role.ANYONE); // query param: pattern
                get("/byEquipment", exerciseController::getByEquipmentPattern, Role.ANYONE);
                get("/byName", exerciseController::getByNamePattern, Role.ANYONE);
            });
            path("/muscle", () ->
            {
                get("/byName", muscleController::getMuscleByNamePattern, Role.ANYONE); // query param: pattern
            });
            path("/equipment", () ->
            {
                get("/byName", equipmentController::getByEquipmentPattern, Role.ANYONE); // query param: pattern
            });
            path("/type", () ->
            {
                get("/byName", exerciseTypeController::getByTypePattern, Role.ANYONE); // query param: pattern
            });
            path("/musclegroup", () ->
            {
                get("/byName", muscleGroupController::getByNamePattern, Role.ANYONE); // query param: pattern
            });
        };
    }
}
