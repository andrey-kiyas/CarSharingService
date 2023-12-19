package com.carsharingservice.service;

import com.carsharingservice.dto.car.CarRequestDto;
import com.carsharingservice.dto.car.CarResponseDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface CarService {
    List<CarResponseDto> getAll(Pageable pageable);

    CarResponseDto save(CarRequestDto request);

    CarResponseDto get(Long id);

    CarResponseDto update(Long id, CarRequestDto request);

    void delete(Long id);
}
