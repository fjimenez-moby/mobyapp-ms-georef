package com.mobydigital.academy.mobyapp.georef.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.mobydigital.academy.mobyapp.georef.model.Locality;
import com.mobydigital.academy.mobyapp.georef.model.LocalityResponse;
import com.mobydigital.academy.mobyapp.georef.model.Province;
import com.mobydigital.academy.mobyapp.georef.model.ProvinceResponse;

// TODO: Handle requests with wrong IDs
@Service
public class GeorefService {

    //TODO: Salió la nueva versión de la API de Georef (v2). La vieja la pueden tirar. Ojo.
    // Nueva API: https://apis.datos.gob.ar/georef/api/v2.0

    @Autowired
    private RestTemplate restTemplate;

    private String apiURL;

    public GeorefService() {
        this.restTemplate = new RestTemplate();
        this.apiURL = "https://apis.datos.gob.ar/georef/api/v2.0";
    }

    public List<String> getProvinces() {
        String url = apiURL + "/provincias?orden=nombre";
        ProvinceResponse response = restTemplate.getForObject(url, ProvinceResponse.class);
        List<Province> provinces = response.getProvinces();
        return provinces.stream()
                .map(Province::getName)
                .toList();
    }

    public List<String> getLocalitiesByIdProvince(Long provinceId) {
        String url = apiURL + "/localidades?provincia=" + provinceId + "&max=5000&orden=nombre";
        LocalityResponse response =  restTemplate.getForObject(url, LocalityResponse.class);
        List<Locality> localities = response.getLocalities();
        return localities.stream()
                .map(Locality::getName)
                .toList();
    }

    public String getProvinceById(Long provinceId) {
        String url = apiURL + "/provincias?id=" + provinceId;
        ProvinceResponse response =  restTemplate.getForObject(url, ProvinceResponse.class);
        return response.getProvinces().get(0).getName();
    }

    // Buscar localidades por nombre de provincia
    public List<String> getLocalitiesByProvinceName(String provinceName) {
        String url = apiURL + "/localidades?provincia=" + provinceName + "&orden=nombre&max=5000";
        List<Locality> localities = restTemplate.getForObject(url, LocalityResponse.class).getLocalities();
        return localities.stream()
                .map(Locality::getName)
                .toList();
    }

    public String getLocalityById(Long localityId) {
        String url = apiURL + "/localidades?id=" + localityId;
        LocalityResponse response =  restTemplate.getForObject(url, LocalityResponse.class);
        return response.getLocalities().get(0).getName();
    }
}
