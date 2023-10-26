package dat.controller;

import io.javalin.http.Context;

public class IndexController
{
    public void handleIndex(Context ctx)
    {
        ctx.render("index.html");
    }
}
