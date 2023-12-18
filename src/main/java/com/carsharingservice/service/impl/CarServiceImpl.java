package com.carsharingservice.service.impl;

import com.carsharingservice.dto.CarRequestDto;
import com.carsharingservice.dto.CarResponseDto;
import com.carsharingservice.mapper.CarMapper;
import com.carsharingservice.model.Car;
import com.carsharingservice.repository.CarRepository;
import com.carsharingservice.service.CarService;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;
    private final CarMapper carMapper;

    @Override
    public List<CarResponseDto> getAll(Pageable pageable) {
        return carRepository.findAll(pageable).stream()
                .map(carMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CarResponseDto save(CarRequestDto requestDto) {
        final Car car = carMapper.toModel(requestDto);
        return carMapper.toDto(carRepository.save(car));
    }

    @Override
    public CarResponseDto get(Long id) {
        return carMapper.toDto(carById(id));
    }

    @Override
    public CarResponseDto update(Long id, CarRequestDto carRequestDto) {
        Car carToUpdate = carById(id);
        carMapper.updateCar(carRequestDto, carToUpdate);
        return carMapper.toDto(carRepository.save(carToUpdate));
    }

    @Override
    public void delete(Long id) {
        carRepository.deleteById(id);
    }

    private Car carById(Long id) {
        return carRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find car by id: " + id)
        );
    }
}
