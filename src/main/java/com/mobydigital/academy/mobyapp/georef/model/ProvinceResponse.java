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
@Schema(description = "Respuesta de la API Georef que contiene la lista de provincias.")
public class ProvinceResponse {

    @Schema(
            description = "Listado de provincias devueltas por la API.",
            type = "array",
            example = """
            [
              "Buenos Aires",
              "Catamarca",
              "Córdoba",
              "Mendoza"
            ]
        """
    )
    @JsonProperty("provincias")
    private List<Province> provinces;

}
