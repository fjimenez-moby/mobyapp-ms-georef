package com.mobydigital.academy.mobyapp.georef.controller;

import java.util.List;

import com.mobydigital.academy.mobyapp.georef.exception.ProvinceNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
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
import com.mobydigital.academy.mobyapp.georef.service.GeorefService;

@Tag(name = "Georef ─ Provincias",
        description = "Endpoint para trabajar exclusivamente con provincias")
@RestController
@RequestMapping("/api/locations/provinces")
@AllArgsConstructor
public class ProvincesController {

    private GeorefService georefService;

    // Hace falta el ID?
    @Operation(
            summary = "Lista de provincias de Argentina.", description = "Devuelve una lista con las provincias de Argentina.")
    @ApiResponse(
            responseCode = "200",
            description = "Lista de provincias",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                            type = "array",
                            example = """
                        [
                          "Buenos Aires",
                          "Chubut",
                          "Mendoza"
                        ]
                        """
                    )
            )
    )
    @GetMapping()
    public ResponseEntity<List<String>> getProvinces() {
        return new ResponseEntity<>(georefService.getProvinces(), HttpStatus.OK);
    }

    @Operation(summary = "Provincia por ID", description = "Obtiene una provincia según el ID especificado.")
    @ApiResponse(
            responseCode = "200",
            description = "Provincia encontrada.",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                            type = "string",
                            example = "Buenos Aires"
                    )
            )
    )
     @ApiResponse(responseCode = "404", description = "Provincia no encontrada.")
    @GetMapping("/{id}")
    public ResponseEntity<String> getProvinceById(@PathVariable("id") Long provinceId) throws ProvinceNotFoundException {
        return new ResponseEntity<>(georefService.getProvinceById(provinceId), HttpStatus.OK);
    }



}
