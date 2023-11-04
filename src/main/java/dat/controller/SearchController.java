package dat.controller;

import io.javalin.http.Context;

public class SearchController
{
    public void handleSearch(Context ctx)
    {
        String query = ctx.queryParam("q");


    }
}
