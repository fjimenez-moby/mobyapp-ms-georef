package com.mobydigital.academy.mobyapp.georef.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "Respuesta de la API Georef que contiene la lista de localidades.")
public class LocalityResponse {

    @Schema(
            description = "Listado de localidades devueltas por la API.",
            example = """
                [
                  { "id": 620140100, "nombre": "Mar del Plata" },
                  { "id": 620070700, "nombre": "Balcarce" }
                ]
            """
    )
    @JsonProperty("localidades")
    private List<Locality> localities;

}