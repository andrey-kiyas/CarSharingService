package com.carsharingservice.controler;

import com.carsharingservice.dto.car.CarRequestDto;
import com.carsharingservice.dto.car.CarResponseDto;
import com.carsharingservice.service.CarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Car management", description = "Endpoints for managing cars")
@RequestMapping(value = "/cars")
public class CarController {
    private final CarService carService;

    @Operation(summary = "Getting cars", description = "Getting all available cars")
    @GetMapping
    public List<CarResponseDto> getAll(Pageable pageable) {
        return carService.getAll(pageable);
    }

    @Operation(summary = "Adding a cars", description = "Adding a new car")
    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping
    public CarResponseDto addCar(@RequestBody @Valid CarRequestDto requestDto) {
        return carService.save(requestDto);
    }

    @Operation(summary = "Getting a car by id", description = "Getting a car by it's id")
    @GetMapping("/{id}")
    public CarResponseDto get(@PathVariable Long id) {
        return carService.get(id);
    }

    @Operation(summary = "Updating a car", description = "Updating a car by it's id")
    @PreAuthorize("hasRole('MANAGER')")
    @PutMapping("/{id}")
    public CarResponseDto update(@PathVariable Long id,
                                 @RequestBody @Valid CarRequestDto carRequestDto) {
        return carService.update(id, carRequestDto);
    }

    @Operation(summary = "Deleting a car", description = "Deleting a car by it's id")
    @PreAuthorize("hasRole('MANAGER')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        carService.delete(id);
    }
}
