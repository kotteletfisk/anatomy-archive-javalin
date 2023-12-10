package dat.routes;

import dat.controller.ExerciseController;
import dat.entities.Role;
import io.javalin.apibuilder.EndpointGroup;
import static io.javalin.apibuilder.ApiBuilder.*;
public class ExerciseRoutes
{
    ExerciseController exerciseController = new ExerciseController();

    public EndpointGroup getRoutes()
    {
        return () ->
        {
            path("/exercise", () ->
            {
                get("/", exerciseController::getAll, Role.ANYONE);
                get("/{id}", exerciseController::getById, Role.ANYONE);
                post("/", exerciseController::create, Role.ADMIN);
                put("/{id}", exerciseController::update, Role.ADMIN);
                delete("/{id}", exerciseController::delete, Role.ADMIN);
                // muscle by exercise
                post("/muscle", exerciseController::addMuscle, Role.ADMIN); // query param: exerciseId, muscleId
                get("/{id}/muscle", exerciseController::getMuscle, Role.ANYONE);
                // type by exercise
                post("/type", exerciseController::addType, Role.ADMIN); // query param: exerciseId, typeId
                get("/{id}/type", exerciseController::getType, Role.ANYONE);
                // equipment by exercise
                post("/equipment", exerciseController::addEquipment, Role.ADMIN); // query param: exerciseId, equipmentId
                get("/{id}/equipment", exerciseController::getEquipment, Role.ANYONE);
            });
        };
    }
}
