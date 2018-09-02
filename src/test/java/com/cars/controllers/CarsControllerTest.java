package com.cars.controllers;


import com.cars.CarsApp;
import com.cars.requests.CarAddRequest;
import com.cars.requests.CarTrimAddRequest;
import com.cars.requests.ManufacturerAddRequest;
import com.cars.utils.TestUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ContextConfiguration(classes = {CarsApp.class})
public class CarsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RestTemplate restTemplate;

    private MockRestServiceServer server;

    @Value("${autos.microservice.url}")
    private String autosMicroServiceURL;

    @Before
    public void setUp() throws Exception {
        server = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    public void manufacturers_returnsCarManufacturers() throws Exception {
//        server.expect(requestTo(autosMicroServiceURL + "/api/autos/manufacturers"))
//                .andRespond(withSuccess(TestUtils.readJsonFixture("/responses/manufacturers.json",
//                        new HashMap<>()),
//                        MediaType.APPLICATION_JSON));
//

        server.expect(requestTo(autosMicroServiceURL + "/api/autos/manufacturers"))
                .andRespond(withSuccess(TestUtils.readFixture("/responses/manufacturers.json"),
                        MediaType.APPLICATION_JSON));

        mockMvc.perform(get("/autos/manufacturers")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().json(TestUtils.readFixture("/responses/manufacturers.json")));
    }

    @Test
    public void cars_returnsCarsByManufacturer() throws Exception {
        server.expect(requestTo(autosMicroServiceURL + "/api/autos/cars/ford"))
                .andRespond(withSuccess(TestUtils.readFixture("/responses/cars.json"),
                        MediaType.APPLICATION_JSON));

        mockMvc.perform(get("/autos/cars/ford")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().json(TestUtils.readFixture("/responses/cars.json")));
    }

    @Test
    public void manufacturer_addsCarManufacturer() throws Exception {
        server.expect(requestTo(autosMicroServiceURL + "/api/autos/manufacturer"))
                .andRespond(withSuccess(TestUtils.readFixture("/responses/manufacturer.json"),
                        MediaType.APPLICATION_JSON));

        String jsonPayload =
                new ObjectMapper().writeValueAsString(ManufacturerAddRequest
                        .builder()
                        .name("Nissan")
                        .countryOfOrigin("Japan")
                        .build());
        mockMvc.perform(post("/autos/manufacturer")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonPayload))
                .andExpect(status().isOk())
                .andExpect(content().json(TestUtils.readFixture("/responses/manufacturer.json")));
    }

    @Test
    public void car_addsCarForManufacturer() throws Exception {
        server.expect(requestTo(autosMicroServiceURL + "/api/autos/car"))
                .andRespond(withSuccess(TestUtils.readFixture("/responses/car.json"),
                        MediaType.APPLICATION_JSON));

        String jsonPayload =
                new ObjectMapper().writeValueAsString(CarAddRequest
                        .builder()
                        .name("Pathfinder")
                        .numberOfCylinders(6)
                        .manufacturerName("Nissan")
                        .build());

        mockMvc.perform(post("/autos/car")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonPayload))
                .andExpect(status().isOk())
                .andExpect(content().json(TestUtils.readFixture("/responses/car.json")));
    }

    @Test
    public void trims_returnsTrimsForManufacturerCarCombination() throws Exception{
        server.expect(requestTo(autosMicroServiceURL + "/api/autos/trims/Honda/Accord"))
                .andRespond(withSuccess(TestUtils.readFixture("/responses/manufacturer-trims.json"),
                        MediaType.APPLICATION_JSON));

        mockMvc.perform(get("/autos/trims/Honda/Accord")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(TestUtils.readFixture("/responses/manufacturer-trims.json")));
    }

    @Test
    public void trim_addsTrimForManufacturerCarCombination() throws Exception {
        server.expect(requestTo(autosMicroServiceURL + "/api/autos/trim"))
                .andRespond(withSuccess(TestUtils.readFixture("/responses/trim.json"),
                        MediaType.APPLICATION_JSON));

        String jsonPayload =
                new ObjectMapper().writeValueAsString(CarTrimAddRequest
                        .builder()
                        .carName("Accord")
                        .manufacturerName("Honda")
                        .trimName("LimitedEdition")
                        .build());

        mockMvc.perform(post("/autos/trim")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonPayload))
                .andExpect(status().isOk())
                .andExpect(content().json(TestUtils.readFixture("/responses/trim.json")));
    }
}
