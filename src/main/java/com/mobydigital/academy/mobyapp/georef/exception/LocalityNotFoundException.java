package com.mobydigital.academy.mobyapp.georef.exception;

public class LocalityNotFoundException extends Exception {

    public LocalityNotFoundException(Long localityId) {
        super("Localidad con ID " + localityId + " no existe.");
    }

}
