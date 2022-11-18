package de.hydrum.rockpaperscissors.configuration;

import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Configuration;

/**
 * The OpenAPI config
 */
@Configuration
public class OpenApiConfiguration {

    /**
     * Creates the open api meta definition
     *
     * @return the open api meta definition
     */
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .addServersItem(new Server().description("localhost").url("http://localhost:8080"))
                .info(new Info()
                        .title("Rock Paper Scissors"));
    }
}
