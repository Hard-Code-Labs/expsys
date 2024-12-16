package ec.com.expensys.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import ec.com.expensys.web.exception.InvalidTokenException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
public class JWTUtils {

    @Value("${security.jwt.key.private}")
    private String privateKey;

    @Value("${security.jwt.issuer}")
    private String userGenerator;

    private Algorithm algorithm;

    @PostConstruct
    public void init(){
        this.algorithm = Algorithm.HMAC256(privateKey);
    }

    public String getNewTokenOnRegister(String mail) {
        return JWT.create()
                .withIssuer(this.userGenerator)
                .withSubject(mail)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(30)))
                .withJWTId(UUID.randomUUID().toString())
                .withNotBefore(new Date(System.currentTimeMillis()))
                .sign(algorithm);
    }

    public String getNewAccessToken(Authentication authentication) {
        String username = authentication.getName();

        String authorities = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return JWT.create()
                .withIssuer(this.userGenerator)
                .withSubject(username)
                .withClaim("authorities",authorities)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(30)))
                .withJWTId(UUID.randomUUID().toString())
                .withNotBefore(new Date(System.currentTimeMillis()))
                .sign(algorithm);
    }

    public String getNewRefreshToken(Authentication authentication) {
        String username = authentication.getName();

        return JWT.create()
                .withIssuer(this.userGenerator)
                .withSubject(username)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(1)))
                .withJWTId(UUID.randomUUID().toString())
                .withNotBefore(new Date(System.currentTimeMillis()))
                .sign(algorithm);
    }

    public DecodedJWT verifyToken(String token) {
        try{
            JWTVerifier verifier = JWT.require(this.algorithm)
                    .withIssuer(this.userGenerator)
                    .build();

            return verifier.verify(token);
        }
        catch (JWTVerificationException ex) {
            throw new InvalidTokenException(
                    "Token is invalid.",
                    JWTUtils.class.getName(),
                    true,
                    ex.getLocalizedMessage());
        }
    }

    public String getUsernameFromToken(DecodedJWT decodedToken) {
        return decodedToken.getSubject();
    }

    public Claim getClaim(DecodedJWT decodedToken, String claimName) {
        return decodedToken.getClaim(claimName);
    }

    public Map<String, Claim> getClaims(DecodedJWT decodedToken) {
        return decodedToken.getClaims();
    }
}