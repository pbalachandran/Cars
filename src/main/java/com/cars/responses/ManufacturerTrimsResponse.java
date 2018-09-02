package com.cars.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ManufacturerTrimsResponse {
    String manufacturerName;
    String countryOfOrigin;
    CarTrimResponse car;
}
