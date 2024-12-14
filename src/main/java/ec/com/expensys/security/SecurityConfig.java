package ec.com.expensys.security;

import ec.com.expensys.service.UserDetailServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {


    @Autowired
    private JWTUtils jwtUtils;

    // Configuracion explicita sin anotaciones del @EnableMethodSecurity
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity
//                .csrf(AbstractHttpConfigurer::disable)
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .authorizeHttpRequests(http -> {
//
//                    // Endpoints publicos
//                    http.requestMatchers(HttpMethod.POST, "/v1/register/").permitAll();
//
//                    // Endpoints privados
//                    http.requestMatchers(HttpMethod.GET, "/person/sec").hasAuthority("READ");
//
//                    //Endpoints no considerados
//                    http.anyRequest().authenticated();
//                })
//                .httpBasic(Customizer.withDefaults())// solo para pruebas
//                .cors(cors -> cors.configurationSource(corsConfigurationSource()));
//
//        return httpSecurity.build();
//    }


    // Se usa cuando tengo la anotacion @EnableMethodSecurity
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity ) throws Exception {
            return httpSecurity
                    .csrf(AbstractHttpConfigurer::disable)
                    .httpBasic(Customizer.withDefaults())// TODO solo para pruebas
                    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                    .addFilterBefore(new JWTValidationFilter(jwtUtils), BasicAuthenticationFilter.class)
                    .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailServiceImpl userDetailServiceImpl) {
        DaoAuthenticationProvider daoProvider = new DaoAuthenticationProvider();
        daoProvider.setPasswordEncoder(getPasswordEncoder());
        daoProvider.setUserDetailsService(userDetailServiceImpl);
        return daoProvider;
    }

    @Bean
    public UserDetailsService userDetailsServiceTest() {
        UserDetails user = User.withUsername("tebo@mail.com")
                .password("admin123")
                .roles("ADMIN")
                .authorities("READ")
                .build();

        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //Para hacer pruebas
//    @Bean
//    public PasswordEncoder getPasswordEncoderTest() {
//        return NoOpPasswordEncoder.getInstance();
//    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(Arrays.asList("*")); // Permite todos los orígenes
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE")); // Permite todos los métodos
        corsConfiguration.setAllowedHeaders(Arrays.asList("*")); // Permite todos los encabezados

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);

        return source;
    }
}
