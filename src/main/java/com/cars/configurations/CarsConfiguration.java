package com.cars.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class CarsConfiguration {

    @Value("${autos.microservice.url}")
    private String autosMicroServiceURL;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public String autosMicroServiceURL() {
        return autosMicroServiceURL;
    }
}
