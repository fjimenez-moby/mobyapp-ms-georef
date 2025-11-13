package com.mobydigital.academy.mobyapp.georef.controller;

import java.util.List;

import com.mobydigital.academy.mobyapp.georef.exception.LocalityNotFoundException;
import com.mobydigital.academy.mobyapp.georef.exception.ProvinceNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.mobydigital.academy.mobyapp.georef.service.GeorefService;

@Tag(name = "Georef ─ Provincias y Localidades",
        description = "API del gobierno para obtener provincias y localidades de Argentina")
@RestController
@RequestMapping("/api/locations")
public class GeorefController {

    private GeorefService georefService;

    @Autowired
    public GeorefController(GeorefService georefService) {
        this.georefService = georefService;
    }

    // Hace falta el ID?
    @Operation(
            summary = "Lista de provincias de Argentina.", description = "Devuelve una lista con las provincias de Argentina.")
    @ApiResponse(responseCode = "200", description = "Lista de strings con provncias de Argentina.")
    @GetMapping("/provinces")
    public ResponseEntity<List<String>> getProvinces() {
        return new ResponseEntity<>(georefService.getProvinces(), HttpStatus.OK);
    }

    @Operation(
            summary = "Provincia por ID", description = "Obtiene una provincia según el ID especificado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Provincia encontrada."),
            @ApiResponse(responseCode = "404", description = "Provincia no encontrada.")
    })
    @GetMapping("/provinces/{id}")
    public ResponseEntity<String> getProvinceById(@PathVariable("id") Long provinceId) throws ProvinceNotFoundException {
        return new ResponseEntity<>(georefService.getProvinceById(provinceId), HttpStatus.OK);
    }

    @Operation(
            summary = "Localidades por ID provincia", description = "Obtiene una lista de localidades según el ID de provincia especificado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de localidades para una provincia."),
            @ApiResponse(responseCode = "404", description = "Provincia no encontrada.")
    })
    @GetMapping("/provinces/id/{id}/localities")
    public ResponseEntity<List<String>> getLocalitiesByIdProvince(@PathVariable("id") Long provinceId) throws  ProvinceNotFoundException {
        return new ResponseEntity<>(georefService.getLocalitiesByIdProvince(provinceId), HttpStatus.OK);
    }

    @Operation(
            summary = "Localidades por nombre de provincia", description = "Obtiene una lista de localidades según el nombre de provincia especificado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de localidades para una provincia."),
            @ApiResponse(responseCode = "404", description = "Provincia no encontrada.")
    })
    @GetMapping("/provinces/name/{name}/localities")
    public ResponseEntity<List<String>> getLocalitiesByProvinceName(@PathVariable("name") String provinceName) throws ProvinceNotFoundException {
        return new ResponseEntity<>(georefService.getLocalitiesByProvinceName(provinceName), HttpStatus.OK);
    }

    @Operation(
            summary = "Localidad por ID", description = "Obtiene una localidad según el nombre de localidad especificado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de localidades para una provincia."),
            @ApiResponse(responseCode = "404", description = "Provincia no encontrada.")
    })
    @GetMapping("/localities/{id}")
    public ResponseEntity<String> getLocalityById(@PathVariable("id") Long localityId) throws LocalityNotFoundException {
        return new ResponseEntity<>(georefService.getLocalityById(localityId), HttpStatus.OK);
    }

}
