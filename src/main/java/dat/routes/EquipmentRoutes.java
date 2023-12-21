package dat.routes;

import dat.controller.EquipmentController;
import dat.entities.Role;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.post;
import static io.javalin.apibuilder.ApiBuilder.put;

public class EquipmentRoutes
{
    EquipmentController equipmentController = new EquipmentController();

    public EndpointGroup getRoutes()
    {
        return () ->
        {
          post("/", equipmentController::create, Role.ADMIN);
          put("/{id}", equipmentController::update, Role.ADMIN);
        };
    }
}
