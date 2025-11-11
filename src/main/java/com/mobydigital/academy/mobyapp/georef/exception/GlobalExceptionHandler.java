package com.mobydigital.academy.mobyapp.georef.exception;

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

    @ExceptionHandler(LocalityNotFoundException.class)
    public ResponseEntity<Void> handleLocalityNotFoundException(LocalityNotFoundException ex) {
        logger.error(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .header(HttpHeaders.LOCATION,redirectUri + "/error&type=client_error")
                .build();
    }

    @ExceptionHandler(ProvinceNotFoundException.class)
    public ResponseEntity<Void> handleProvinceNotFoundException(ProvinceNotFoundException ex) {
        logger.error(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .header(HttpHeaders.LOCATION,redirectUri + "/error&type=client_error")
                .build();
    }

}
