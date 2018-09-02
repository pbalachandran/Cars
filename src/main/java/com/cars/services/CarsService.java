package com.cars.services;

import com.cars.requests.CarAddRequest;
import com.cars.requests.CarTrimAddRequest;
import com.cars.requests.ManufacturerAddRequest;
import com.cars.responses.CarResponse;
import com.cars.responses.ManufacturerResponse;
import com.cars.responses.ManufacturerTrimsResponse;
import com.cars.responses.TrimResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class CarsService {

    private String autosMicroServiceURL;

    private RestTemplate restTemplate;

    @Autowired
    public CarsService(String autosMicroServiceURL,
                       RestTemplate restTemplate) {
        this.autosMicroServiceURL = autosMicroServiceURL;
        this.restTemplate = restTemplate;
    }

    public List<ManufacturerResponse> getManufacturers() {
        ResponseEntity<List<ManufacturerResponse>> responseEntity =
                restTemplate.exchange(autosMicroServiceURL + "/api/autos/manufacturers",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<ManufacturerResponse>>() {
                        });
        return responseEntity.getBody();
    }

    public List<CarResponse> getCarsForManufacturer(String manufacturerName) {
        ResponseEntity<List<CarResponse>> responseEntity =
                restTemplate.exchange(autosMicroServiceURL + "/api/autos/cars/" + manufacturerName,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<CarResponse>>() {
                        });
        return responseEntity.getBody();
    }

    public ManufacturerResponse addManufacturer(ManufacturerAddRequest manufacturerAddRequest) {
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<ManufacturerAddRequest> httpEntity = new HttpEntity<>(manufacturerAddRequest, httpHeaders);

        ResponseEntity<ManufacturerResponse> responseEntity =
                restTemplate.exchange(autosMicroServiceURL + "/api/autos/manufacturer",
                                HttpMethod.POST,
                                httpEntity,
                                new ParameterizedTypeReference<ManufacturerResponse>() {
                                });
        return responseEntity.getBody();
    }

    public CarResponse addCar(CarAddRequest carAddRequest) {
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<CarAddRequest> httpEntity = new HttpEntity<>(carAddRequest, httpHeaders);

        ResponseEntity<CarResponse> responseEntity =
                restTemplate.exchange(autosMicroServiceURL + "/api/autos/car",
                        HttpMethod.POST,
                        httpEntity,
                        new ParameterizedTypeReference<CarResponse>() {
                        });
        return responseEntity.getBody();
    }

    public ManufacturerTrimsResponse trims(String manufacturerName, String carName) {
        ResponseEntity<ManufacturerTrimsResponse> responseEntity =
                restTemplate.exchange(autosMicroServiceURL + "/api/autos/trims/" + manufacturerName + "/" + carName,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ManufacturerTrimsResponse>(){
                });
        return responseEntity.getBody();
    }

    public TrimResponse addTrim(CarTrimAddRequest carTrimAddRequest) {
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<CarTrimAddRequest> httpEntity =
                new HttpEntity<>(carTrimAddRequest, httpHeaders);

        ResponseEntity<TrimResponse> responseEntity =
                restTemplate.exchange("http://localhost:8081/api/autos/trim",
                        HttpMethod.POST,
                        httpEntity,
                        new ParameterizedTypeReference<TrimResponse>() {
                        });
        return responseEntity.getBody();
    }
}
