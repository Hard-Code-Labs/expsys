package ec.com.expensys.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "MoneyAtic by ExpSys",
                version = "1.0.0",
                description = "Expenses Manager System for MoneyAtic",
                termsOfService = "www.hardcodelabs.com/terms",

                contact = @Contact(
                        name = "adr.es",
                        url = "https://github.com/Tebanes",
                        email = "adr.es@icloud.com"
                ),

                license = @License(
                        name = "GPL License",
                        url = "www.hardcodelabs.com/license"
                )
        ),
        servers = {
                @Server(
                        description = "stage dev",
                        url = "http://localhost:8080/xis"
                ),
                @Server(
                        description = "stage prod",
                        url = "https://koyeb:8080"
                )
        },

        security = {
                @SecurityRequirement(
                        name = "Security Token"
                )
        }
)

@SecurityScheme(
        name = "Security Token",
        type = SecuritySchemeType.HTTP,
        paramName = HttpHeaders.AUTHORIZATION,
        in = SecuritySchemeIn.HEADER,
        scheme = "bearer",
        bearerFormat = "JWT",
        description = "Token "

 )
public class OpenApiConfig {
}
