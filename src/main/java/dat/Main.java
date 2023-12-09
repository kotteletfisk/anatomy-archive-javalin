package dat;

import dat.config.ApplicationConfig;
import io.javalin.Javalin;

import java.io.IOException;

public class Main
{
    public static void main(String[] args) throws IOException
    {
        ApplicationConfig
                .startServer(
                        Javalin.create(),
                        Integer.parseInt(ApplicationConfig.getProperty("javalin.port")));

        // DONE: Add wrapper entity for exercise type enum. Maybe use strings instead for easier additions later?
        // DONE: add error handling
        // TODO: require authentication for POST, PUT, PATCH, DELETE
            // - have admin and manager role
            // - Manager: can create, update and delete exercises, equipment, muscle groups and muscles
            // - Admin: can do all above and create and delete managers
            // - password is hashed in database
        // TODO: add routehandlers
           // - Exercise (DOING)
           // - Muscle
           // - MuscleGroup
           // - Equipment
        // TODO: add controllers
        // TODO: find out the mediapath thing
    }
}