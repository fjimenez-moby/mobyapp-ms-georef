package com.mobydigital.academy.mobyapp.georef.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
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
// La lista de municipios que proviene de la API
public class LocalityResponse {
    @JsonProperty("localidades")
    private List<Locality> localities;

    public List<Locality> getLocalities() {
        return localities;
    }
}

