package com.cars.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ManufacturerTrimsViewModel {
    String manufacturerName;
    String countryOfOrigin;
    CarTrimViewModel car;
}
