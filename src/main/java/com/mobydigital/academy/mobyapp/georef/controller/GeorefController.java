package com.mobydigital.academy.mobyapp.georef.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mobydigital.academy.mobyapp.commons.dto.LocalityDTO;
import com.mobydigital.academy.mobyapp.commons.dto.ProvinceDTO;
import com.mobydigital.academy.mobyapp.georef.model.Locality;
import com.mobydigital.academy.mobyapp.georef.model.Province;
import com.mobydigital.academy.mobyapp.georef.service.GeorefService;

@RestController
@RequestMapping("/locations")
public class GeorefController {

    @Autowired
    private GeorefService georefService;

    @GetMapping("/provinces")
    public List<Province> getProvinces() {
        return georefService.getProvinces();
    }

    @GetMapping("/provinces/{id}")
    public ProvinceDTO getProvinceById(@PathVariable("id") Long provinceId) {
        return georefService.getProvinceById(provinceId);
    }

    @GetMapping("/provinces/{id}/localities")
    public List<Locality> getLocalitiesByIdProvince(@PathVariable("id") Long provinceId) {
        return georefService.getLocalitiesByIdProvince(provinceId);
    }

    @GetMapping("/localities/{id}")
    public LocalityDTO getLocalityById(@PathVariable("id") Long localityId) {
        return georefService.getLocalityById(localityId);
    }

}
