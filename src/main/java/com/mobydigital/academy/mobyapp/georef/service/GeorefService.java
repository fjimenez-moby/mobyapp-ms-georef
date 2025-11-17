package com.mobydigital.academy.mobyapp.georef.service;

import java.util.List;
import java.util.Objects;

import com.mobydigital.academy.mobyapp.georef.exception.LocalityNotFoundException;
import com.mobydigital.academy.mobyapp.georef.exception.ProvinceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import com.mobydigital.academy.mobyapp.georef.model.Locality;
import com.mobydigital.academy.mobyapp.georef.model.LocalityResponse;
import com.mobydigital.academy.mobyapp.georef.model.Province;
import com.mobydigital.academy.mobyapp.georef.model.ProvinceResponse;

@Service
public class GeorefService {

    private final RestTemplate restTemplate;

    private final String apiURL;

    @Autowired
    public GeorefService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.apiURL = "https://apis.datos.gob.ar/georef/api/v2.0"; // Nueva versión de la API
    }

    public List<String> getProvinces() {
        String url = apiURL + "/provincias?orden=nombre";
        ProvinceResponse response = restTemplate.getForObject(url, ProvinceResponse.class);
        if (response != null) {
            List<Province> provinces = response.getProvinces();
            return provinces.stream()
                    .map(Province::getName)
                    .toList();
        }

        throw new RestClientException("Hubo un fallo en la petición REST a la API Georef.");

    }

    public List<String> getLocalitiesByIdProvince(Long provinceId) throws ProvinceNotFoundException {
        String url = apiURL + "/localidades?provincia=" + provinceId + "&max=5000&orden=nombre";
        LocalityResponse response =  restTemplate.getForObject(url, LocalityResponse.class);

        if(response == null || response.getLocalities().isEmpty()){
            throw new ProvinceNotFoundException();
        }

        List<Locality> localities = response.getLocalities();

        return localities.stream()
                .map(Locality::getName)
                .toList();
    }

    public String getProvinceById(Long provinceId) throws ProvinceNotFoundException {
        String url = apiURL + "/provincias?id=" + provinceId;

        List<Province> provinces = Objects.requireNonNull(restTemplate
                        .getForObject(url, ProvinceResponse.class))
                .getProvinces();

        if(provinces.isEmpty())
            throw new ProvinceNotFoundException();

        return provinces.get(0).getName();
    }

    public List<String> getLocalitiesByProvinceName(String provinceName) throws ProvinceNotFoundException {
        List<String> provinceList = getProvinces();

        if(provinceName == null || provinceName.trim().isEmpty() || !(provinceList.contains(provinceName))){
            throw new ProvinceNotFoundException();
        }

        String url = apiURL + "/localidades?provincia=" + provinceName + "&orden=nombre&max=5000";
        List<Locality> localities = Objects.requireNonNull(restTemplate.getForObject(url, LocalityResponse.class)).getLocalities();

        return localities.stream()
                .map(Locality::getName)
                .toList();
    }

    public String getLocalityById(Long localityId) throws LocalityNotFoundException {

        String url = apiURL + "/localidades?id=" + localityId;

        ResponseEntity<LocalityResponse> responseEntity =
                restTemplate.getForEntity(url, LocalityResponse.class);

        // Si la API devolvió 4xx, lo tomamos como "no encontrada" o inválida
        if (responseEntity.getStatusCode().is4xxClientError()) {
            throw new LocalityNotFoundException();
        }

        LocalityResponse body = responseEntity.getBody();

        if (body == null ||
                body.getLocalities() == null ||
                body.getLocalities().isEmpty()) {

            throw new LocalityNotFoundException();
        }

        return body.getLocalities().get(0).getName();
    }
}
