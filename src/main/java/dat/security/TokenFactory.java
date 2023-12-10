package dat.security;

import dat.exception.TokenException;
import dat.security.factory.TokenCreator;
import dat.security.factory.TokenVerifier;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class TokenFactory
{

    private static final TokenCreator creator = new TokenCreator();
    private static final TokenVerifier verifier = new TokenVerifier();

    public static String createToken(ClaimBuilder claimBuilder, byte[] secretKey) throws TokenException
    {
        return creator.createToken(claimBuilder, secretKey);
    }

    public static String verifyToken(String token, String secretKey, ClaimBuilder claimBuilder) throws TokenException
    {
        return verifier.verifyTokenAndReturnClaim(token, secretKey.getBytes(), claimBuilder);
    }

    public static String getUsernameFromToken(String token) throws TokenException
    {
        return verifier.getUsernameFromToken(token);
    }

}
