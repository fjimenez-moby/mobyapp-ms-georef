package com.mobydigital.academy.mobyapp.georef.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mobydigital.academy.mobyapp.commons.dto.ProvinceDTO;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Component
// La anotación @JsonIgnoreProperties se utiliza para ignorar propiedades desconocidas durante la deserialización JSON
@JsonIgnoreProperties(ignoreUnknown = true)
public class Province {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("nombre")
    private String name;

    public ProvinceDTO toDTO() {
        return new ProvinceDTO(this.name);
    }
}
