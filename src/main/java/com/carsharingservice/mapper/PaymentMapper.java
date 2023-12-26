package com.carsharingservice.mapper;

import com.carsharingservice.config.MapperConfig;
import com.carsharingservice.dto.payment.PaymentDto;
import com.carsharingservice.model.Payment;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface PaymentMapper {
    PaymentDto toDto(Payment payment);
}
