package com.mobydigital.academy.mobyapp.georef.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.mobydigital.academy.mobyapp.commons.dto.LocalityDTO;
import com.mobydigital.academy.mobyapp.commons.dto.ProvinceDTO;
import com.mobydigital.academy.mobyapp.georef.model.Locality;
import com.mobydigital.academy.mobyapp.georef.model.LocalityResponse;
import com.mobydigital.academy.mobyapp.georef.model.Province;
import com.mobydigital.academy.mobyapp.georef.model.ProvinceResponse;

// TODO: Handle requests with wrong IDs
@Service
public class GeorefService {

    @Autowired
    private RestTemplate restTemplate;

    private String apiURL;

    public GeorefService() {
        this.restTemplate = new RestTemplate();
        this.apiURL = "https://apis.datos.gob.ar/georef/api";
    }

    public List<Province> getProvinces() {
        String url = apiURL + "/provincias?orden=nombre";
        ProvinceResponse response = restTemplate.getForObject(url, ProvinceResponse.class);
        return response.getProvinces();
    }

    public List<Locality> getLocalitiesByIdProvince(Long provinceId) {
        String url = apiURL + "/localidades?provincia=" + provinceId + "&max=5000&orden=nombre";
        LocalityResponse response =  restTemplate.getForObject(url, LocalityResponse.class);
        return response.getLocalities();
    }

    public ProvinceDTO getProvinceById(Long provinceId) {
        String url = apiURL + "/provincias?id=" + provinceId;
        ProvinceResponse response =  restTemplate.getForObject(url, ProvinceResponse.class);
        return response.getProvinces().get(0).toDTO();
    }

    public LocalityDTO getLocalityById(Long localityId) {
        String url = apiURL + "/localidades?id=" + localityId;
        LocalityResponse response =  restTemplate.getForObject(url, LocalityResponse.class);
        return response.getLocalities().get(0).toDTO();
    }
}
