package ec.com.expensys.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
public class CorsConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        //Indica los origenes a permitir
        corsConfiguration.setAllowedOrigins(Arrays.asList("*"));

        //Se define los metodos http a permitir
        corsConfiguration.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE"));

        //Se puede definir los encabezados
        corsConfiguration.setAllowedHeaders(Arrays.asList("*"));

        UrlBasedCorsConfigurationSource origin  = new UrlBasedCorsConfigurationSource();

        //El pattern indica que se aplicara la configuracion a todos los controladores
        origin.registerCorsConfiguration("/**",corsConfiguration);

        return origin;
    }
}
