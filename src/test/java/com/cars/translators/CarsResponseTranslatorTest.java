package com.cars.translators;

import com.cars.models.*;
import com.cars.responses.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CarsResponseTranslatorTest {

    private CarsResponseTranslator subject;

    @Before
    public void setUp() throws Exception {
        subject = new CarsResponseTranslator();
    }

    @Test
    public void translateManufactuerResponses_returnsManufacturerViewModels() {

        List<ManufacturerResponse> manufacturerResponses = new ArrayList<>();

        manufacturerResponses.add(ManufacturerResponse
                .builder()
                .name("Ford")
                .countryOfOrigin("USA")
                .build());

        manufacturerResponses.add(ManufacturerResponse
                .builder()
                .name("Honda")
                .countryOfOrigin("Japan")
                .build());

        List<ManufacturerViewModel> actualManufacturerViewModeles =
                subject.translateManufactuerResponses(manufacturerResponses);

        List<ManufacturerViewModel> expectedManufacturerViewModels = new ArrayList<>();
        expectedManufacturerViewModels.add(ManufacturerViewModel
                .builder()
                .name("Ford")
                .countryOfOrigin("USA")
                .build());

        expectedManufacturerViewModels.add(ManufacturerViewModel
                .builder()
                .name("Honda")
                .countryOfOrigin("Japan")
                .build());

        assertEquals(expectedManufacturerViewModels, actualManufacturerViewModeles);
    }

    @Test
    public void translateCarResponses_returnsCarViewModels() {

        List<CarResponse> carResponses = new ArrayList<>();
        carResponses.add(CarResponse
                .builder()
                .name("Focus")
                .numberOfCylinders(4)
                .manufacturerName("Ford")
                .build());

        carResponses.add(CarResponse
                .builder()
                .name("Explorer")
                .numberOfCylinders(6)
                .manufacturerName("Ford")
                .build());

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

        carResponses.add(CarResponse
                .builder()
                .name("Camry")
                .numberOfCylinders(4)
                .manufacturerName("Toyota")
                .build());

        carResponses.add(CarResponse
                .builder()
                .name("Sequoia")
                .numberOfCylinders(8)
                .manufacturerName("Toyota")
                .build());

        List<CarViewModel> actualCarViewModels = subject.translateCarResponses(carResponses);

        List<CarViewModel> expectCarViewModels = new ArrayList<>();
        expectCarViewModels.add(CarViewModel
                .builder()
                .name("Focus")
                .numberOfCylinders(4)
                .manufacturerName("Ford")
                .build());

        expectCarViewModels.add(CarViewModel
                .builder()
                .name("Explorer")
                .numberOfCylinders(6)
                .manufacturerName("Ford")
                .build());

        expectCarViewModels.add(CarViewModel
                .builder()
                .name("Accord")
                .numberOfCylinders(4)
                .manufacturerName("Honda")
                .build());

        expectCarViewModels.add(CarViewModel
                .builder()
                .name("Pilot")
                .numberOfCylinders(6)
                .manufacturerName("Honda")
                .build());

        expectCarViewModels.add(CarViewModel
                .builder()
                .name("Camry")
                .numberOfCylinders(4)
                .manufacturerName("Toyota")
                .build());

        expectCarViewModels.add(CarViewModel
                .builder()
                .name("Sequoia")
                .numberOfCylinders(8)
                .manufacturerName("Toyota")
                .build());

        assertEquals(expectCarViewModels, actualCarViewModels);
    }

    @Test
    public void translateManufacturerResponse_returnsManufacturerViewModel() {
        ManufacturerViewModel actualViewModel =
                subject.translateManufacturerResponse(ManufacturerResponse
                        .builder()
                        .name("Ford")
                        .countryOfOrigin("USA")
                        .build());

        ManufacturerViewModel expectedViewModel = ManufacturerViewModel
                .builder()
                .name("Ford")
                .countryOfOrigin("USA")
                .build();
        assertEquals(expectedViewModel, actualViewModel);
    }

    @Test
    public void translateCarResponse_returnsCarViewModel() {
        CarViewModel actualViewModel =
                subject.translateCarResponse(CarResponse
                        .builder()
                        .name("Focus")
                        .numberOfCylinders(4)
                        .manufacturerName("Ford")
                        .build());

        CarViewModel expectedViewModel = CarViewModel
                .builder()
                .name("Focus")
                .numberOfCylinders(4)
                .manufacturerName("Ford")
                .build();
        assertEquals(expectedViewModel, actualViewModel);
    }

    @Test
    public void translateManufacturerTrimsResponse_returnsManufacturerTrimsViewModel() {

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

        ManufacturerTrimsViewModel actualViewModel =
                subject.translateManufacturerTrimsResponse(manufacturerTrimsResponse);

        List<TrimViewModel> trimViewModels = new ArrayList<>();
        TrimViewModel trimViewModel1 = TrimViewModel
                .builder()
                .trimName("LX")
                .build();

        TrimViewModel trimViewModel2 = TrimViewModel
                .builder()
                .trimName("EX")
                .build();
        trimViewModels.add(trimViewModel1);
        trimViewModels.add(trimViewModel2);
        ManufacturerTrimsViewModel expectedViewModel =
                ManufacturerTrimsViewModel
                        .builder()
                        .manufacturerName("Honda")
                        .countryOfOrigin("Japan")
                        .car(CarTrimViewModel
                                .builder()
                                .carName("Accord")
                                .numberOfCylinders(4)
                                .trims(trimViewModels)
                                .build())
                        .build();
        assertEquals(expectedViewModel, actualViewModel);
    }

    @Test
    public void translateTrimResponse() {
        TrimViewModel actualViewModel =
                subject.translateTrimResponse(TrimResponse
                        .builder()
                        .carName("Accord")
                        .trimName("LimitedEdition")
                        .build());

        TrimViewModel expectedViewModel = TrimViewModel
                .builder()
                .carName("Accord")
                .trimName("LimitedEdition")
                .build();

        assertEquals(expectedViewModel, actualViewModel);
    }
}