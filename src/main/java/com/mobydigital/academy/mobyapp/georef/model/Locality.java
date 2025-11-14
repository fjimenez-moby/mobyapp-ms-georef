package com.mobydigital.academy.mobyapp.georef.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Component
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "Representa una localidad obtenida desde la API Georef (IGN Argentina).")
public class Locality {

    @Schema(
            description = "Identificador único de la localidad.",
            example = "620140100"
    )
    @JsonProperty("id")
    private Long id;

    @Schema(
            description = "Nombre oficial de la localidad.",
            example = "Mar del Plata"
    )
    @JsonProperty("nombre")
    private String name;
}