package ec.com.expensys.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import ec.com.expensys.service.TokenRedisManagerService;
import ec.com.expensys.web.exception.InvalidTokenException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;

@Component
@RequiredArgsConstructor
public class JWTValidationFilter extends OncePerRequestFilter {

    private final TokenRedisManagerService tokenRedisManagerService;
    private final JWTUtils jwtUtils;


    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        try {
            if (jwtToken != null && jwtToken.startsWith("Bearer ")) {
                jwtToken = jwtToken.substring(7); // Extrae el bearer y el espacio

                if (tokenRedisManagerService.existToken(jwtToken)) {
                    DecodedJWT decodedJwt = jwtUtils.verifyToken(jwtToken);

                    String username = jwtUtils.getUsernameFromToken(decodedJwt);
                    String authorityString = jwtUtils.getClaim(decodedJwt, "authorities").asString();

                    Collection<? extends GrantedAuthority> authorities = AuthorityUtils
                            .commaSeparatedStringToAuthorityList(authorityString);

                    SecurityContext securityContext = SecurityContextHolder.getContext();
                    Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);
                    securityContext.setAuthentication(authentication);
                    SecurityContextHolder.setContext(securityContext);
                } else {
                    throw new InvalidTokenException("Token invalid because it was used",
                            JWTValidationFilter.class.getName(), false, null);
                }
            }

            filterChain.doFilter(request, response);

        } catch (InvalidTokenException e) {
            handleException(response, e);
        } catch (Exception e) {
            handleException(response, new InvalidTokenException("Error on processing token.",
                    JWTValidationFilter.class.getName(), false, e.getMessage()));
        }
    }

    private void handleException(HttpServletResponse response, InvalidTokenException e) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write("{\"customMessage\": \"" + e.getMessage() + "\"}");
    }

}
