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
            get("/exercise", exerciseController::getAll);
            get("/exercise/{id}", exerciseController::getById);
            post("/exercise", exerciseController::create);
            put("/exercise/{id}", exerciseController::update);
            delete("/exercise/{id}", exerciseController::delete);
            post("/exercise/muscle", exerciseController::addMuscle); // query param: exerciseId, muscleId
            get("/exercise/{id}/muscle", exerciseController::getMuscle);
            post("/exercise/type", exerciseController::addType); // query param: exerciseId, typeId
            get("/exercise/{id}/type", exerciseController::getType);
        };
    }
}
