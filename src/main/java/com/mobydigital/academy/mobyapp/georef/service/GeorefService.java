package com.mobydigital.academy.mobyapp.georef.service;

import java.util.List;
import java.util.Objects;

import com.mobydigital.academy.mobyapp.georef.exception.LocalityNotFoundException;
import com.mobydigital.academy.mobyapp.georef.exception.ProvinceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import com.mobydigital.academy.mobyapp.georef.model.Locality;
import com.mobydigital.academy.mobyapp.georef.model.LocalityResponse;
import com.mobydigital.academy.mobyapp.georef.model.Province;
import com.mobydigital.academy.mobyapp.georef.model.ProvinceResponse;

@Service
public class GeorefService {

    private static final Logger logger = LoggerFactory.getLogger(GeorefService.class);

    private final RestTemplate restTemplate;

    @Value("${georef.api.primary.url}")
    private String primaryApiURL;

    @Value("${georef.api.fallback.url}")
    private String fallbackApiURL;

    @Value("${georef.api.max-results:5000}")
    private int maxResults;

    @Autowired
    public GeorefService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Retryable(
            retryFor = {RestClientException.class},
            maxAttempts = 3,
            backoff = @Backoff(delay = 1000, multiplier = 2)
    )
    public List<String> getProvinces() {
        return getProvincesFromApi(primaryApiURL);
    }

    @Recover
    public List<String> recoverGetProvinces(RestClientException e) {
        logger.warn("Primary API failed, using fallback API for getProvinces", e);
        return getProvincesFromApi(fallbackApiURL);
    }

    private List<String> getProvincesFromApi(String apiURL) {
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

    @Retryable(
            retryFor = {RestClientException.class},
            maxAttempts = 3,
            backoff = @Backoff(delay = 1000, multiplier = 2)
    )
    public List<String> getLocalitiesByIdProvince(Long provinceId) throws ProvinceNotFoundException {
        return getLocalitiesByIdProvinceFromApi(primaryApiURL, provinceId);
    }

    @Recover
    public List<String> recoverGetLocalitiesByIdProvince(RestClientException e, Long provinceId) throws ProvinceNotFoundException {
        logger.warn("Primary API failed, using fallback API for getLocalitiesByIdProvince", e);
        return getLocalitiesByIdProvinceFromApi(fallbackApiURL, provinceId);
    }

    private List<String> getLocalitiesByIdProvinceFromApi(String apiURL, Long provinceId) throws ProvinceNotFoundException {
        String url = apiURL + "/localidades?provincia=" + provinceId + "&max=" + maxResults + "&orden=nombre";
        LocalityResponse response = restTemplate.getForObject(url, LocalityResponse.class);

        if(response == null || response.getLocalities() == null || response.getLocalities().isEmpty()){
            throw new ProvinceNotFoundException();
        }

        List<Locality> localities = response.getLocalities();

        return localities.stream()
                .map(Locality::getName)
                .toList();
    }

    @Retryable(
            retryFor = {RestClientException.class},
            maxAttempts = 3,
            backoff = @Backoff(delay = 1000, multiplier = 2)
    )
    public String getProvinceById(Long provinceId) throws ProvinceNotFoundException {
        return getProvinceByIdFromApi(primaryApiURL, provinceId);
    }

    @Recover
    public String recoverGetProvinceById(RestClientException e, Long provinceId) throws ProvinceNotFoundException {
        logger.warn("Primary API failed, using fallback API for getProvinceById", e);
        return getProvinceByIdFromApi(fallbackApiURL, provinceId);
    }

    private String getProvinceByIdFromApi(String apiURL, Long provinceId) throws ProvinceNotFoundException {
        String url = apiURL + "/provincias?id=" + provinceId;

        ProvinceResponse response = restTemplate.getForObject(url, ProvinceResponse.class);

        if(response == null || response.getProvinces() == null || response.getProvinces().isEmpty()) {
            throw new ProvinceNotFoundException();
        }

        return response.getProvinces().get(0).getName();
    }

    @Retryable(
            retryFor = {RestClientException.class},
            maxAttempts = 3,
            backoff = @Backoff(delay = 1000, multiplier = 2)
    )
    public List<String> getLocalitiesByProvinceName(String provinceName) throws ProvinceNotFoundException {
        return getLocalitiesByProvinceNameFromApi(primaryApiURL, provinceName);
    }

    @Recover
    public List<String> recoverGetLocalitiesByProvinceName(RestClientException e, String provinceName) throws ProvinceNotFoundException {
        logger.warn("Primary API failed, using fallback API for getLocalitiesByProvinceName", e);
        return getLocalitiesByProvinceNameFromApi(fallbackApiURL, provinceName);
    }

    private List<String> getLocalitiesByProvinceNameFromApi(String apiURL, String provinceName) throws ProvinceNotFoundException {
        if(provinceName == null || provinceName.trim().isEmpty()){
            throw new ProvinceNotFoundException();
        }

        String url = apiURL + "/localidades?provincia=" + provinceName + "&orden=nombre&max=" + maxResults;
        LocalityResponse response = restTemplate.getForObject(url, LocalityResponse.class);

        if(response == null || response.getLocalities() == null || response.getLocalities().isEmpty()) {
            throw new ProvinceNotFoundException();
        }

        return response.getLocalities().stream()
                .map(Locality::getName)
                .toList();
    }

    @Retryable(
            retryFor = {RestClientException.class},
            maxAttempts = 3,
            backoff = @Backoff(delay = 1000, multiplier = 2)
    )
    public String getLocalityById(Long localityId) throws LocalityNotFoundException {
        return getLocalityByIdFromApi(primaryApiURL, localityId);
    }

    @Recover
    public String recoverGetLocalityById(RestClientException e, Long localityId) throws LocalityNotFoundException {
        logger.warn("Primary API failed, using fallback API for getLocalityById", e);
        return getLocalityByIdFromApi(fallbackApiURL, localityId);
    }

    private String getLocalityByIdFromApi(String apiURL, Long localityId) throws LocalityNotFoundException {
        String url = apiURL + "/localidades?id=" + localityId;

        LocalityResponse response = restTemplate.getForObject(url, LocalityResponse.class);

        if (response == null ||
                response.getLocalities() == null ||
                response.getLocalities().isEmpty()) {

            throw new LocalityNotFoundException();
        }

        return response.getLocalities().get(0).getName();
    }
}
