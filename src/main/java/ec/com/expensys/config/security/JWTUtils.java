package ec.com.expensys.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class JWTUtils {

    private static final String SECRET = "secret";
    private final Algorithm algorithm = Algorithm.HMAC256(SECRET);

    public String createOnRegister(String username) {
        return JWT.create()
                .withSubject(username)
                .withIssuer("moneyatic")
                .withIssuedAt(new Date())
//                .withExpiresAt(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(2)))//expira en 15 dias
                .withExpiresAt(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(3))) // expira en 3 minutos
                .sign(algorithm);
    }

    public boolean isValid(String jwt){
        try {
            JWT.require(algorithm).build().verify(jwt);
            return true;
        }catch (JWTVerificationException e){
            return false;
        }
    }
}