package com.mobydigital.academy.mobyapp.georef.controller;

import com.mobydigital.academy.mobyapp.georef.exception.LocalityNotFoundException;
import com.mobydigital.academy.mobyapp.georef.exception.ProvinceNotFoundException;
import com.mobydigital.academy.mobyapp.georef.model.LocalityResponse;
import com.mobydigital.academy.mobyapp.georef.service.GeorefService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Georef ─ Localidades",
        description = "Endpoint para trabajar con las localidades de una provincia en especifico, o una localidad especificada por un id")
@RestController
@RequestMapping("/api/locations")
@AllArgsConstructor
public class LocalityController {
    private GeorefService georefService;

    @Operation(
            summary = "Localidades por ID provincia", description = "Obtiene una lista de localidades según el ID de provincia especificado."
    )
    @ApiResponse(responseCode = "200", description = "Lista de localidades para una provincia.",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = LocalityResponse.class))
            ))
    @ApiResponse(responseCode = "404", description = "Localidades no encontradas para ese id de provincia.")

    @GetMapping("/provinces/id/{id}/localities")
    public ResponseEntity<List<String>> getLocalitiesByIdProvince(@PathVariable("id") Long provinceId) throws ProvinceNotFoundException {
        return new ResponseEntity<>(georefService.getLocalitiesByIdProvince(provinceId), HttpStatus.OK);
    }

    @Operation(
            summary = "Localidades por nombre de provincia", description = "Obtiene una lista de localidades según el nombre de provincia especificado.")
    @ApiResponse(responseCode = "200", description = "Lista de localidades para una provincia.",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = LocalityResponse.class))
            ))
    @ApiResponse(responseCode = "404", description = "Localidades no encontradas para ese nombre de provincia.")
    @GetMapping("/provinces/name/{name}/localities")
    public ResponseEntity<List<String>> getLocalitiesByProvinceName(@PathVariable("name") String provinceName) throws ProvinceNotFoundException {
        return new ResponseEntity<>(georefService.getLocalitiesByProvinceName(provinceName), HttpStatus.OK);
    }

    @Operation(
            summary = "Localidad por ID", description = "Obtiene una localidad según el nombre de localidad especificado.")
    @ApiResponse(responseCode = "200", description = "Localidad correspondiente a un id.",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                            type = "string",
                            example = "Mar Del Plata"
                    )
            ))
    @ApiResponse(responseCode = "404", description = "Localidad no encontrada con ese id.")
    @GetMapping("/localities/{id}")
    public ResponseEntity<String> getLocalityById(@PathVariable("id") Long localityId) throws LocalityNotFoundException {
        return new ResponseEntity<>(georefService.getLocalityById(localityId), HttpStatus.OK);
    }
}
