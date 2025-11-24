//Genera automáticamente la documentación interactiva de la API
package com.example.mutant_detector.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI (){
        return new OpenAPI()
                .info (new Info()
                        .title("Mutant Detector API")
                        .version("1.0")
                        .description("API para detectar mutantes basada en secuencias de ADN"));
    }
}
