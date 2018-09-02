package com.cars.services;


import com.cars.requests.CarAddRequest;
import com.cars.requests.CarTrimAddRequest;
import com.cars.requests.ManufacturerAddRequest;
import com.cars.responses.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CarsServiceTest {

    private CarsService subject;

    @Mock
    private RestTemplate mockRestTemplate;

    @Before
    public void setUp() throws Exception {
        String autosMicroServiceURL = "http://localhost:8081";
        subject = new CarsService(autosMicroServiceURL,
                mockRestTemplate);
    }

    @Test
    public void getManufacturers_retunsManufacturers() {
        List<ManufacturerResponse> manufacturers = new ArrayList<>();
        manufacturers.add(ManufacturerResponse
                .builder()
                .name("Ford")
                .countryOfOrigin("USA")
                .build());

        manufacturers.add(ManufacturerResponse
                .builder()
                .name("Honda")
                .countryOfOrigin("Japan")
                .build());

        manufacturers.add(ManufacturerResponse
                .builder()
                .name("Toyota")
                .countryOfOrigin("Japan")
                .build());

        when(mockRestTemplate.exchange("http://localhost:8081/api/autos/manufacturers",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ManufacturerResponse>>(){}))
                .thenReturn(ResponseEntity.ok(manufacturers));

        List<ManufacturerResponse> actualManufacturers = subject.getManufacturers();

        verify(mockRestTemplate).exchange("http://localhost:8081/api/autos/manufacturers",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ManufacturerResponse>>(){});
        assertEquals(manufacturers, actualManufacturers);
    }

    @Test
    public void getCarsForManufacturer_returnsCars() {
        List<CarResponse> carResponses = new ArrayList<>();
        carResponses.add(CarResponse
                .builder()
                .name("Accord")
                .numberOfCylinders(4)
                .manufacturerName("Honda")
                .build());

        carResponses.add(CarResponse
                .builder()
                .name("Pilot")
                .numberOfCylinders(6)
                .manufacturerName("Honda")
                .build());

        when(mockRestTemplate
                .exchange("http://localhost:8081/api/autos/cars/Honda",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<CarResponse>>(){}))
                .thenReturn(ResponseEntity.ok(carResponses));

        List<CarResponse> actualCarResponses = subject.getCarsForManufacturer("Honda");

        verify(mockRestTemplate)
                .exchange("http://localhost:8081/api/autos/cars/Honda",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<CarResponse>>(){});
        assertEquals(carResponses, actualCarResponses);
    }

    @Test
    public void addManufacturer_addsCarManufacturer() {
        ManufacturerAddRequest manufacturerAddRequest =
                ManufacturerAddRequest
                        .builder()
                        .name("Ford")
                        .countryOfOrigin("USA")
                        .build();

        ManufacturerResponse manufacturerResponse = ManufacturerResponse
                .builder()
                .name("Ford")
                .countryOfOrigin("USA")
                .build();

        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<ManufacturerAddRequest> httpEntity = new HttpEntity<>(manufacturerAddRequest, httpHeaders);
        when(mockRestTemplate
                .exchange("http://localhost:8081/api/autos/manufacturer",
                        HttpMethod.POST,
                        httpEntity,
                        new ParameterizedTypeReference<ManufacturerResponse>(){}))
                .thenReturn(ResponseEntity.ok(manufacturerResponse));

        ManufacturerResponse actualResponse = subject.addManufacturer(manufacturerAddRequest);

        verify(mockRestTemplate)
                .exchange("http://localhost:8081/api/autos/manufacturer",
                        HttpMethod.POST,
                        httpEntity,
                        new ParameterizedTypeReference<ManufacturerResponse>(){});
        assertEquals(manufacturerResponse, actualResponse);
    }

    @Test
    public void addCar_addsCarToManufacturer() {
        CarAddRequest carAddRequest = CarAddRequest
                .builder()
                .name("Focus")
                .numberOfCylinders(4)
                .manufacturerName("Ford")
                .build();

        CarResponse carResponse = CarResponse.
                builder()
                .name("Focus")
                .numberOfCylinders(4)
                .manufacturerName("Ford")
                .build();

        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<CarAddRequest> httpEntity =
                new HttpEntity<>(carAddRequest, httpHeaders);

        when(mockRestTemplate.exchange("http://localhost:8081/api/autos/car",
                HttpMethod.POST,
                httpEntity,
                new ParameterizedTypeReference<CarResponse>(){}))
                .thenReturn(ResponseEntity.ok(carResponse));

        CarResponse actualResponse = subject.addCar(carAddRequest);

        verify(mockRestTemplate)
                .exchange("http://localhost:8081/api/autos/car",
                        HttpMethod.POST,
                        httpEntity,
                        new ParameterizedTypeReference<CarResponse>(){});
        assertEquals(carResponse, actualResponse);
    }

    @Test
    public void trims_returnsTrimsForManufacturerCarCombination() {
        TrimResponse trim1 = TrimResponse
                .builder()
                .trimName("LX")
                .build();

        TrimResponse trim2 = TrimResponse
                .builder()
                .trimName("EX")
                .build();

        List<TrimResponse> trims = new ArrayList<>();
        trims.add(trim1);
        trims.add(trim2);

        ManufacturerTrimsResponse manufacturerTrimsResponse = ManufacturerTrimsResponse
                .builder()
                .manufacturerName("Honda")
                .countryOfOrigin("Japan")
                .car(CarTrimResponse
                        .builder()
                        .carName("Accord")
                        .numberOfCylinders(4)
                        .trims(trims)
                        .build())
                .build();

        when(mockRestTemplate.exchange("http://localhost:8081/api/autos/trims/Honda/Accord",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ManufacturerTrimsResponse>() {
                })).thenReturn(ResponseEntity.ok(manufacturerTrimsResponse));

        ManufacturerTrimsResponse actualResponse = subject.trims("Honda", "Accord");

        verify(mockRestTemplate).exchange("http://localhost:8081/api/autos/trims/Honda/Accord",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ManufacturerTrimsResponse>() {
                });

        assertEquals(manufacturerTrimsResponse, actualResponse);
    }


    @Test
    public void trim_addsTrimToManufacturerCarCombination() {
        CarTrimAddRequest carTrimAddRequest = CarTrimAddRequest
                .builder()
                .manufacturerName("Honda")
                .carName("Accord")
                .trimName("LimitedEdition")
                .build();

        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<CarTrimAddRequest> httpEntity =
                new HttpEntity<>(carTrimAddRequest, httpHeaders);

        TrimResponse trimResponse = TrimResponse
                .builder()
                .carName("Accord")
                .trimName("LimitedEdition")
                .build();

        when(mockRestTemplate
                .exchange("http://localhost:8081/api/autos/trim",
                        HttpMethod.POST,
                        httpEntity,
                        new ParameterizedTypeReference<TrimResponse>() {
                        })).thenReturn(ResponseEntity.ok(trimResponse));

        TrimResponse actualResponse = subject.addTrim(carTrimAddRequest);

        verify(mockRestTemplate)
                .exchange("http://localhost:8081/api/autos/trim",
                        HttpMethod.POST,
                        httpEntity,
                        new ParameterizedTypeReference<TrimResponse>() {
                        });

        assertEquals(trimResponse, actualResponse);
    }
}