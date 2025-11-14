package com.mobydigital.academy.mobyapp.georef.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.tags.Tag;
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
                        .description("Consumo de API del gobierno para obtener provincias y localidades de Argentina adaptado a las necesidades de la MobyApp.")
                        .contact(new Contact()
                                .name("MobyDigital")
                                .email("adminacademy@mobydigital.com")))
                .addTagsItem(new Tag().name("Georef ─ Provincias"))
                .addTagsItem(new Tag().name("Georef ─ Localidades"));
    }

}
