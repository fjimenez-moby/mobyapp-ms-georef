package com.mobydigital.academy.mobyapp.georef.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Georef")
                        .version("1.0")
                        .description("API del gobierno para obtener provincias y localidades de Argentina.")
                        .contact(new Contact()
                                .name("MobyDigital")
                                .email("adminacademy@mobydigital.com")));
    }

}
