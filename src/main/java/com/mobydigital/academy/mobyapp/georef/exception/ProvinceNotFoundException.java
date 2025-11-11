package com.mobydigital.academy.mobyapp.georef.exception;

public class ProvinceNotFoundException extends Exception {
    public ProvinceNotFoundException(Long provinceId) {
       super("Provincia con ID " + provinceId + " no existe.");
    }
}
