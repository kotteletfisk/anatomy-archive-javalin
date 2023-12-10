package dat.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.nimbusds.jose.JOSEException;
import dat.config.ApplicationConfig;
import dat.dao.AuthDao;
import dat.dto.LoginDTO;
import dat.dto.RegisterDTO;
import dat.entities.Role;
import dat.entities.User;
import dat.exception.ApiException;
import dat.exception.TokenException;
import dat.security.ClaimBuilder;
import dat.security.KeyGenerator;
import dat.security.TokenFactory;
import io.javalin.http.Context;

import java.io.IOException;

public class AuthController
{
    AuthDao dao = AuthDao.getInstance();

    public void login(Context context) throws ApiException, IOException, JOSEException, TokenException
    {
        LoginDTO loginDTO = context.bodyAsClass(LoginDTO.class);

        User user = dao.readByName(loginDTO.username());

        if (user == null || !user.verifyPassword(loginDTO.password()))
        {
            throw new ApiException(400, "Invalid username or password");
        }

        ClaimBuilder claimBuilder = ApplicationConfig.getClaimBuilder(user, user.getRole().toString());
        String token = TokenFactory.createToken(claimBuilder, KeyGenerator.getSecretKey());
        context.status(200);
        context.json(createResponseObject(user.getUsername(), user.getRole(), token));
    }

    public void register(Context context) throws ApiException, IOException, JOSEException, TokenException
    {
        RegisterDTO registerDTO = validateUser(context);

        if (dao.exists(registerDTO.username()))
        {
            throw new ApiException(400, "User already exists");
        }

        User user = new User(registerDTO.username(), registerDTO.password());
        user.setRole(registerDTO.role());
        dao.create(user);

        ClaimBuilder claimBuilder = ApplicationConfig.getClaimBuilder(user, user.getRole().toString());
        String token = TokenFactory.createToken(claimBuilder, KeyGenerator.getSecretKey());
        context.status(201);
        context.json(createResponseObject(user.getUsername(), user.getRole(), token));
    }


    private ObjectNode createResponseObject(String userName, Role role, String token) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode respondNode = mapper.createObjectNode();
        respondNode.put("username", userName);
        respondNode.putPOJO("role", role);
        respondNode.put("token", token);
        return respondNode;
    }

    private RegisterDTO validateUser(Context context)
    {
        return context.bodyValidator(RegisterDTO.class)
                .check(registerDTO -> registerDTO.username() != null && !registerDTO.username().isEmpty(), "Username cannot be empty")
                .check(registerDTO -> registerDTO.password() != null && registerDTO.password().length() > 8, "Password must be longer than 8 characters")
                .check(registerDTO -> registerDTO.role() != null, "Role cannot be empty")
                .get();
    }
}
