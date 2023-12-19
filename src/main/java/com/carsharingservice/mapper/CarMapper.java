package com.carsharingservice.mapper;

import com.carsharingservice.config.MapperConfig;
import com.carsharingservice.dto.car.CarRequestDto;
import com.carsharingservice.dto.car.CarResponseDto;
import com.carsharingservice.model.Car;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface CarMapper {
    CarResponseDto toDto(Car car);

    Car toModel(CarRequestDto carRequestDto);

    void updateCar(CarRequestDto carRequestDto, @MappingTarget Car car);
}
