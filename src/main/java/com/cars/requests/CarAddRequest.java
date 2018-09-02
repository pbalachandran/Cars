package com.cars.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CarAddRequest {
    String name;
    String manufacturerName;
    int numberOfCylinders;

}
