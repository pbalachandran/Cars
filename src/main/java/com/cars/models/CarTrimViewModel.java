package com.cars.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CarTrimViewModel {
    String carName;
    int numberOfCylinders;
    List<TrimViewModel> trims;
}
