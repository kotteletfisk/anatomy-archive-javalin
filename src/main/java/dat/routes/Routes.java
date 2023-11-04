package dat.routes;

import dat.controller.ExceptionController;
import dat.controller.IndexController;
import dat.controller.SearchController;
import io.javalin.Javalin;
import io.javalin.apibuilder.EndpointGroup;
import io.javalin.http.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.javalin.apibuilder.ApiBuilder.path;

public class Routes {

    private final ExceptionController exceptionController = new ExceptionController();
    private final SearchController searchController = new SearchController();
    private int count = 0;

    private final Logger LOGGER = LoggerFactory.getLogger(Routes.class);

    private void requestInfoHandler(Context ctx) {
        String requestInfo = ctx.req().getMethod() + " " + ctx.req().getRequestURI();
        ctx.attribute("requestInfo", requestInfo);
    }

    public EndpointGroup getRoutes(Javalin app) {
        return () -> {
            app.before(this::requestInfoHandler);

            app.get("/", ctx -> new IndexController().handleIndex(ctx));
            // app.get("/search", ctx -> );

            /*app.routes(() -> {
                path("/", new UserRoutes().getRoutes());
            });*/

            app.after(ctx -> LOGGER.info(" Request {} - {} was handled with status code {}", count++, ctx.attribute("requestInfo"), ctx.status()));

            app.exception(Exception.class, exceptionController::exceptionHandler);
        };
    }
}