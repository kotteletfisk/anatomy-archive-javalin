package dat.security;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.gen.OctetSequenceKeyGenerator;

import java.util.UUID;

public class KeyGenerator
{
    private static byte[] secretKey;

    public static byte[] getSecretKey() throws JOSEException
    {
        if (secretKey == null || secretKey.length < 1)
        {
            secretKey = generateSecretKey();
        }
        return secretKey;
    }

    private static byte[] generateSecretKey() throws JOSEException
    {
        return new OctetSequenceKeyGenerator(256)
                .keyID(UUID.randomUUID().toString())
                .algorithm(JWSAlgorithm.HS256)
                .generate()
                .toByteArray();
    }
}
