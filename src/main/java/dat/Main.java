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
                        // Integer.parseInt(ApplicationConfig.getProperty("javalin.port"))); //TODO: Nullpointer
                        7070);
    }
}