package org.konkuk.foodmeter.external.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("Foodmeter")
                .description("식재료 신선도 AI Classification App API Document")
                .version("1.0.0");

        return new OpenAPI()
                .components(new Components())
                .info(info);
    }

}
