package com.cars.controllers;

import com.cars.models.ManufacturerTrimsViewModel;
import com.cars.models.TrimViewModel;
import com.cars.requests.CarAddRequest;
import com.cars.requests.CarTrimAddRequest;
import com.cars.requests.ManufacturerAddRequest;
import com.cars.responses.CarResponse;
import com.cars.responses.ManufacturerResponse;
import com.cars.responses.ManufacturerTrimsResponse;
import com.cars.responses.TrimResponse;
import com.cars.services.CarsService;
import com.cars.models.CarViewModel;
import com.cars.models.ManufacturerViewModel;
import com.cars.translators.CarsResponseTranslator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/autos")
public class CarsController {

    private CarsService carsService;

    private CarsResponseTranslator carsResponseTranslator;

    @Autowired
    public CarsController(CarsService carsService,
                          CarsResponseTranslator carsResponseTranslator) {
        this.carsService = carsService;
        this.carsResponseTranslator = carsResponseTranslator;
    }

    @GetMapping("/manufacturers")
    public ResponseEntity<List<ManufacturerViewModel>> manufacturers() {
        List<ManufacturerResponse> manufacturers = carsService.getManufacturers();
        return ResponseEntity
                .ok()
                .body(carsResponseTranslator.translateManufactuerResponses(manufacturers));
    }

    @GetMapping("/cars/{manufacturer}")
    public ResponseEntity<List<CarViewModel>> cars(@PathVariable("manufacturer") String manufacturer) {
        List<CarResponse> cars = carsService.getCarsForManufacturer(manufacturer);
        return ResponseEntity
                .ok()
                .body(carsResponseTranslator.translateCarResponses(cars));
    }

    @PostMapping("/manufacturer")
    public ResponseEntity<ManufacturerViewModel> manufacturer(@RequestBody ManufacturerAddRequest manufacturerAddRequest) {
        ManufacturerResponse manufacturerResponse = carsService.addManufacturer(manufacturerAddRequest);
        return ResponseEntity
                .ok()
                .body(carsResponseTranslator.translateManufacturerResponse(manufacturerResponse));
    }

    @PostMapping("/car")
    public ResponseEntity<CarViewModel> car(@RequestBody CarAddRequest carAddRequest) {
        CarResponse carResponse = carsService.addCar(carAddRequest);
        return ResponseEntity
                .ok()
                .body(carsResponseTranslator.translateCarResponse(carResponse));
    }

    @GetMapping("/trims/{manufacturerName}/{carName}")
    public ResponseEntity<ManufacturerTrimsViewModel> trims(@PathVariable("manufacturerName") String manufacturerName,
                                                            @PathVariable("carName") String carName) {
        ManufacturerTrimsResponse manufacturerTrimsResponse = carsService.trims(manufacturerName, carName);
        return ResponseEntity
                .ok()
                .body(carsResponseTranslator.translateManufacturerTrimsResponse(manufacturerTrimsResponse));

    }

    @PostMapping("/trim")
    public ResponseEntity<TrimViewModel> trim(@RequestBody CarTrimAddRequest carTrimAddRequest) {
        TrimResponse trimResponse = carsService.addTrim(carTrimAddRequest);
        return ResponseEntity.ok().body(carsResponseTranslator.translateTrimResponse(trimResponse));
    }
}