package com.cars.translators;

import com.cars.models.*;
import com.cars.responses.CarResponse;
import com.cars.responses.ManufacturerResponse;
import com.cars.responses.ManufacturerTrimsResponse;
import com.cars.responses.TrimResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CarsResponseTranslator {

    public List<ManufacturerViewModel>
    translateManufactuerResponses(List<ManufacturerResponse> manufacturerResponses) {
        return manufacturerResponses
                .stream()
                .map(manufacturerResponse -> ManufacturerViewModel
                        .builder()
                        .name(manufacturerResponse.getName())
                        .countryOfOrigin(manufacturerResponse.getCountryOfOrigin())
                        .build()).collect(Collectors.toList());
    }

    public List<CarViewModel>
    translateCarResponses(List<CarResponse> carResponses) {
        return carResponses.stream().map(carResponse -> CarViewModel
                .builder()
                .name(carResponse.getName())
                .numberOfCylinders(carResponse.getNumberOfCylinders())
                .manufacturerName(carResponse.getManufacturerName())
                .build())
                .collect(Collectors.toList());
    }

    public ManufacturerViewModel translateManufacturerResponse(ManufacturerResponse manufacturerResponse) {
        return ManufacturerViewModel
                .builder()
                .name(manufacturerResponse.getName())
                .countryOfOrigin(manufacturerResponse.getCountryOfOrigin())
                .build();
    }

    public CarViewModel translateCarResponse(CarResponse carResponse) {
        return CarViewModel
                .builder()
                .name(carResponse.getName())
                .numberOfCylinders(carResponse.getNumberOfCylinders())
                .manufacturerName(carResponse.getManufacturerName())
                .build();
    }

    public ManufacturerTrimsViewModel translateManufacturerTrimsResponse(ManufacturerTrimsResponse manufacturerTrimsResponse) {
        return ManufacturerTrimsViewModel
                .builder()
                .manufacturerName(manufacturerTrimsResponse.getManufacturerName())
                .countryOfOrigin(manufacturerTrimsResponse.getCountryOfOrigin())
                .car(CarTrimViewModel
                        .builder()
                        .carName(manufacturerTrimsResponse.getCar().getCarName())
                        .numberOfCylinders(manufacturerTrimsResponse.getCar().getNumberOfCylinders())
                        .trims(manufacturerTrimsResponse.getCar().getTrims()
                                .stream()
                                .map(trimResponse -> TrimViewModel
                                        .builder()
                                        .trimName(trimResponse.getTrimName())
                                        .carName(manufacturerTrimsResponse.getCar().getCarName())
                                        .build())
                                .collect(Collectors.toList()))
                        .build())
                .build();
    }

    public TrimViewModel translateTrimResponse(TrimResponse trimResponse) {
        return TrimViewModel
                .builder()
                .carName(trimResponse.getCarName())
                .trimName(trimResponse.getTrimName())
                .build();
    }
}
