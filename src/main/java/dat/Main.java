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
        // TODO: add routehandlers
        // TODO: add controllers
        // TODO: require authentication for POST, PUT, PATCH, DELETE
    }
}