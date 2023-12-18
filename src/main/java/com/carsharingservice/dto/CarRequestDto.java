package com.carsharingservice.dto;

import com.carsharingservice.model.Car;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class CarRequestDto {
    private String model;

    private String brand;

    private Car.CarType carType;

    private Integer inventory;

    private BigDecimal dailyFee;
}
