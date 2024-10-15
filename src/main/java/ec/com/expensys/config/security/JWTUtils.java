package ec.com.expensys.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.JWTVerifier;
import ec.com.expensys.web.exception.InvalidTokenException;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class JWTUtils {

    private static final String SECRET = "secret";//todo pasar a variable de entorno
    private final Algorithm algorithm;
    private final JWTVerifier verifier;

    public JWTUtils() {
        this.algorithm = Algorithm.HMAC256(SECRET);
        this.verifier = JWT.require(algorithm)
                .withIssuer("moneyatic")
                .build();
    }

    public String createOnRegister(String mail) {
        return JWT.create()
                .withSubject(mail)
                .withIssuer("moneyatic")
                .withIssuedAt(new Date())
                .withExpiresAt(
                        new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(30))) // duracion en minutos
                .sign(algorithm);
    }

    public void validateToken(String token) {
        try {
            verifier.verify(token);
        } catch (TokenExpiredException expiredException) {
            throw expiredException;

        } catch (JWTVerificationException e) {
            throw new InvalidTokenException(
                    "Token is invalid.",
                    JWTUtils.class.getName(),
                    true,
                    e.getLocalizedMessage());
        }
    }
}