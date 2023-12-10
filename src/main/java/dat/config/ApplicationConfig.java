package dat.config;

import dat.controller.AccessManagerController;
import dat.entities.User;
import dat.security.ClaimBuilder;
import io.javalin.Javalin;
import io.javalin.config.JavalinConfig;
import io.javalin.plugin.bundled.RouteOverviewPlugin;
import io.javalin.rendering.template.JavalinThymeleaf;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import dat.routes.Routes;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class ApplicationConfig
{
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationConfig.class);
    private static final AccessManagerController ACCESS_MANAGER_HANDLER = new AccessManagerController();

    private static void configuration(JavalinConfig config)
    {
        config.routing.contextPath = "/"; // base path for all routes
        config.http.defaultContentType = "application/json"; // default content type for requests
        config.plugins.register(new RouteOverviewPlugin("/routes")); // enables route overview at /
        config.accessManager(ACCESS_MANAGER_HANDLER::accessManagerHandler);
    }

    public static void startServer(Javalin app, int port)
    {
        Routes routes = new Routes();
        app.updateConfig(ApplicationConfig::configuration);
        HibernateConfig.setTest(false); // TODO: remove this line
        app.routes(routes.getRoutes(app));
        app.start(port);
    }

    public static ClaimBuilder getClaimBuilder(User user, String role) throws IOException
    {
        return ClaimBuilder.builder()
                .issuer(ApplicationConfig.getProperty("issuer"))
                .audience(ApplicationConfig.getProperty("audience"))
                .claimSet(Map.of("username", user.getUsername(), "role", role))
                .expirationTime(Long.parseLong(ApplicationConfig.getProperty("token.expiration.time")))
                .issueTime(3600000L)
                .build();
    }

    public static void stopServer(Javalin app)
    {
        app.stop();
    }

    public static String getProperty(String propName) throws IOException
    {
        try (InputStream is = HibernateConfig.class.getClassLoader().getResourceAsStream("properties-from-pom.properties"))
        {
            Properties prop = new Properties();
            prop.load(is);
            return prop.getProperty(propName);
        } catch (IOException ex)
        {
            LOGGER.error("Could not read property from pom file. Build Maven!");
            throw new IOException("Could not read property from pom file. Build Maven!");
        }
    }
}