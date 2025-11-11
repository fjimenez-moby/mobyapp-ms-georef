package com.mobydigital.academy.mobyapp.georef.exception;

public class ProvinceNotFoundException extends Exception {
    public ProvinceNotFoundException() {
       super("La Provincia no existe.");
    }
}
