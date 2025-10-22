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
// Esta clase es para obtener la respuesta de la API de provincias
public class ProvinceResponse {
    @JsonProperty("provincias")
    private List<Province> provinces;

}
