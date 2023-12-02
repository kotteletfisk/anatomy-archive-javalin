package dat.routes;

import dat.controller.ExerciseController;
import io.javalin.apibuilder.EndpointGroup;
import static io.javalin.apibuilder.ApiBuilder.*;
public class ExerciseRoutes
{
    ExerciseController exerciseController = new ExerciseController();

    public EndpointGroup getRoutes()
    {
        return () -> {
            get("/exercise", exerciseController::getAll);
            get("/exercise/{id}", exerciseController::getById);
        };
    }
}
