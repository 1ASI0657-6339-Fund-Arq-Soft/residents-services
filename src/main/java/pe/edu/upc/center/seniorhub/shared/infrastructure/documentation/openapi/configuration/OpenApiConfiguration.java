package pe.edu.upc.center.seniorhub.residents.shared.infrastructure.documentation.openapi.configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {
    @Bean
    public OpenAPI residentsOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Residents Microservice API")
                        .description("Residents Microservice REST API documentation for SeniorHub Platform.")
                        .version("v1.0.0")
                        .license(new License().name("Apache 2.0")
                                .url("https://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("Residents Microservice Documentation")
                        .url("https://github.com/seniorhub-platform"));
    }
}
