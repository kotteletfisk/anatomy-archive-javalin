package dat.controller;

import io.javalin.http.Context;

public class AuthController
{

    public void login(Context context)
    {
        context.result("Hello from login");
    }

    public void register(Context context)
    {
        context.result("Hello from register");
    }
}
