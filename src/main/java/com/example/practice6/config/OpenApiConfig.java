package com.example.practice6.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI galleryOpenApi() {
        return new OpenAPI().info(new Info()
                .title("Gallery API")
                .version("1.0.0")
                .description("REST API for artists, artworks and exhibitions. "
                        + "Lists are paged (page size capped at 50) and sortable."));
    }
}