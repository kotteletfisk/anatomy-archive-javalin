package dat.controller;

import dat.dao.AuthDao;
import dat.entities.Role;
import dat.entities.User;
import dat.exception.ApiException;
import dat.exception.TokenException;
import dat.security.TokenFactory;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.security.RouteRole;

import java.util.Objects;
import java.util.Set;

public class AccessManagerController
{
    public void accessManagerHandler(Handler handler, Context context, Set<? extends RouteRole> permittedRoles) throws Exception
    {
        if (permittedRoles.contains(Role.ANYONE) || Objects.equals(context.method().toString(), "OPTIONS"))
        {
            handler.handle(context);
            return;
        }
        else
        {
            Role role = getRole(context);
            if (permittedRoles.contains(role))
            {
                handler.handle(context);
                return;
            }
            else
            {
                throw new ApiException(401, "You are not authorized to perform this action");
            }
        }
    }

    private Role getRole(Context context) throws TokenException, ApiException
    {
        AuthDao dao = AuthDao.getInstance();
        String token = context.header("Authorization").split(" ")[1];

        String usernameFromToken = TokenFactory.getUsernameFromToken(token);
        User user = dao.readByName(usernameFromToken);
        return user.getRole();
    }
}
