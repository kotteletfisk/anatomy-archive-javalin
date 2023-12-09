package dat.routes;

import dat.controller.AuthController;
import dat.entities.Role;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.post;

public class AuthRoutes
{
    private final AuthController authController = new AuthController();
    public EndpointGroup getRoutes()
    {
        return () ->
        {
            post("/login", authController::login, Role.ANYONE);
            post("/register", authController::register, Role.ANYONE); //TODO: only admin can register
        };
    }
}
