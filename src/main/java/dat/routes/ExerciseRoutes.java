package dat.routes;

import dat.controller.ExerciseController;
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
                get("/", exerciseController::getAll);
                get("/{id}", exerciseController::getById);
                post("/", exerciseController::create);
                put("/{id}", exerciseController::update);
                delete("/{id}", exerciseController::delete);
                post("/muscle", exerciseController::addMuscle); // query param: exerciseId, muscleId
                get("/{id}/muscle", exerciseController::getMuscle);
                post("/type", exerciseController::addType); // query param: exerciseId, typeId
                get("/{id}/type", exerciseController::getType);
                post("/equipment", exerciseController::addEquipment); // query param: exerciseId, equipmentId
            });
        };
    }
}
