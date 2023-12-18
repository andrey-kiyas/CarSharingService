package com.carsharingservice.controler;

import com.carsharingservice.dto.CarRequestDto;
import com.carsharingservice.dto.CarResponseDto;
import com.carsharingservice.service.CarService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/cars")
public class CarController {
    private final CarService carService;

    @GetMapping
    public List<CarResponseDto> getAll(Pageable pageable) {
        return carService.getAll(pageable);
    }

    @PostMapping
    public CarResponseDto addCar(@RequestBody @Valid CarRequestDto requestDto) {
        return carService.save(requestDto);
    }

    @GetMapping("/{id}")
    public CarResponseDto get(@PathVariable Long id) {
        return carService.get(id);
    }

    @PutMapping("/{id}")
    public CarResponseDto update(@PathVariable Long id,
                                 @RequestBody @Valid CarRequestDto carRequestDto) {
        return carService.update(id, carRequestDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        carService.delete(id);
    }
}
