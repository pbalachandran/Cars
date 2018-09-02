package com.cars.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CarTrimResponse {
    String carName;
    int numberOfCylinders;
    List<TrimResponse> trims;
}
