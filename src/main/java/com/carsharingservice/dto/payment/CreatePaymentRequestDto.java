package com.carsharingservice.dto.payment;

import com.carsharingservice.model.Payment;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreatePaymentRequestDto(
        @Positive
        Long rentalId,
        @NotNull
        Payment.PaymentType paymentType
) {
}
