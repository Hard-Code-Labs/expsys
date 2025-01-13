package ec.com.expensys.config;

import graphql.scalars.ExtendedScalars;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

@Configuration
public class GraphQLConfig {

    @Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurer() {
        return wiringBuilder -> wiringBuilder
//                .scalar(ExtendedScalars.DateTime) // No funciona para java.time.LocalDateTime
                .scalar(ExtendedScalars.GraphQLBigDecimal);
    }
}
