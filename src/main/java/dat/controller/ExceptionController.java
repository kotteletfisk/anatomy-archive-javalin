package dat.controller;

import dat.routes.Routes;
import dat.exception.Message;
import io.javalin.http.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class ExceptionController {
    private final Logger LOGGER = LoggerFactory.getLogger(Routes.class);

    public void exceptionHandler(Exception e, Context ctx) {
        LOGGER.error(ctx.attribute("requestInfo") + " " + ctx.res().getStatus() + " " + e.getMessage());

        System.out.println("======================================");
        System.out.println(Arrays.toString(e.getStackTrace()));
        System.out.println("======================================");

        ctx.status(500);
        ctx.json(new Message(500, e.getMessage()));
    }
}