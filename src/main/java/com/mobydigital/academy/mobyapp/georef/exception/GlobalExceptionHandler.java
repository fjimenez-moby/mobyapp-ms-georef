package com.mobydigital.academy.mobyapp.georef.exception;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Value("${REDIRECT_URI}")
    private String redirectUri;

    @ApiResponse(
            responseCode = "404",
            description = "La localidad no fue encontrada",
            content = @Content(
                    schema = @Schema(implementation = String.class),
                    examples = @ExampleObject(value = "La localidad no se pudo encontrar")
            )
    )
    @ExceptionHandler(LocalityNotFoundException.class)
    public ResponseEntity<String> handleLocalityNotFoundException(LocalityNotFoundException ex) {
        logger.error(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .header(HttpHeaders.LOCATION, redirectUri + "/error&type=client_error")
                .body(ex.getMessage());
    }
    @ApiResponse(
            responseCode = "404",
            description = "La provincia no fue encontrada",
            content = @Content(
                    schema = @Schema(implementation = String.class),
                    examples = @ExampleObject(value = "La provincia no se pudo encontrar")
            )
    )
    @ExceptionHandler(ProvinceNotFoundException.class)
    public ResponseEntity<String> handleProvinceNotFoundException(ProvinceNotFoundException ex) {
        logger.error(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .header(HttpHeaders.LOCATION, redirectUri + "/error&type=client_error")
                .body(ex.getMessage());
    }

}
