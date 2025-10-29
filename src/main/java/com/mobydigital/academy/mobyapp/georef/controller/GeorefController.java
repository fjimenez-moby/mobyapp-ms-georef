package com.mobydigital.academy.mobyapp.georef.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.mobydigital.academy.mobyapp.georef.service.GeorefService;

@RestController
@RequestMapping("/api/locations")
public class GeorefController {

    @Autowired
    private GeorefService georefService;

    // Hace falta el ID?
    @GetMapping("/provinces")
    public ResponseEntity<List<String>> getProvinces() {
        return new ResponseEntity<>(georefService.getProvinces(), HttpStatus.OK);
    }

    @GetMapping("/provinces/{id}")
    public ResponseEntity<String> getProvinceById(@PathVariable("id") Long provinceId) {
        return new ResponseEntity<>(georefService.getProvinceById(provinceId), HttpStatus.OK);
    }

    @GetMapping("/provinces/id/{id}/localities")
    public ResponseEntity<List<String>> getLocalitiesByIdProvince(@PathVariable("id") Long provinceId) {
        return new ResponseEntity<>(georefService.getLocalitiesByIdProvince(provinceId), HttpStatus.OK);
    }

    @GetMapping("/provinces/name/{name}/localities")
    public ResponseEntity<List<String>> getLocalitiesByProvinceName(@PathVariable("name") String provinceName) {
        return new ResponseEntity<>(georefService.getLocalitiesByProvinceName(provinceName), HttpStatus.OK);
    }

    @GetMapping("/localities/{id}")
    public ResponseEntity<String> getLocalityById(@PathVariable("id") Long localityId) {
        return new ResponseEntity<>(georefService.getLocalityById(localityId), HttpStatus.OK);
    }

}
