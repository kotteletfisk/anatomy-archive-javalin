package dat.entities;

public enum Role implements io.javalin.security.RouteRole
{
    ANYONE, MANAGER, ADMIN;
}
