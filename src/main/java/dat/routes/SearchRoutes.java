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
                get("/bymuscle", exerciseController::getByMusclePattern, Role.ANYONE); // query param: pattern
                get("/byequipment", exerciseController::getByEquipmentPattern, Role.ANYONE);
                get("/byexercise", exerciseController::getByNamePattern, Role.ANYONE);
            });
            path("/muscle", () ->
            {
                get("/bymuscle", muscleController::getMuscleByNamePattern, Role.ANYONE); // query param: pattern
                get("/byequipment", muscleController::getMuscleByEquipmentPattern, Role.ANYONE);
                get("/byexercise", muscleController::getMuscleByExercisePattern, Role.ANYONE);
            });
            path("/equipment", () ->
            {
                get("/bymuscle", equipmentController::getByMusclePattern, Role.ANYONE); // query param: pattern
                get("/byequipment", equipmentController::getByEquipmentPattern, Role.ANYONE); // query param: pattern
                get("/byexercise", equipmentController::getByExercisePattern, Role.ANYONE); // query param: pattern
            });
            path("/type", () ->
            {
                get("/bytype", exerciseTypeController::getByTypePattern, Role.ANYONE); // query param: pattern
            });
            path("/musclegroup", () ->
            {
                get("/bymusclegroup", muscleGroupController::getByNamePattern, Role.ANYONE); // query param: pattern
            });
        };
    }
}
