package com.josiassantos.desafio_anota_ai.config.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    private static final String API_TITLE = "Project MarketPlace API";
    private static final String API_DESCRIPTION = "MarketPlace API Project API Documentation";
    private static final String API_VERSION = "1.0.0";

    @Bean
    public OpenAPI openApi() {
        return new OpenAPI().info(new Info()
                .title(API_TITLE)
                .description(API_DESCRIPTION)
                .version(API_VERSION));
    }
}