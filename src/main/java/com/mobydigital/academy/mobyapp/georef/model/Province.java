package com.mobydigital.academy.mobyapp.georef.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Component
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "Representa una provincia obtenida desde la API Georef (IGN Argentina).")
public class Province {

    @Schema(
            description = "Identificador único de la provincia.",
            example = "06"
    )
    @JsonProperty("id")
    private Long id;

    @Schema(
            description = "Nombre oficial de la provincia.",
            example = "Buenos Aires"
    )
    @JsonProperty("nombre")
    private String name;

}