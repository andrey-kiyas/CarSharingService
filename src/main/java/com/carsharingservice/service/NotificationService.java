package com.carsharingservice.service;

import com.carsharingservice.model.Car;
import com.carsharingservice.model.Payment;
import com.carsharingservice.model.Rental;
import java.util.Set;

public interface NotificationService {

    void sendMessageAboutCreatedRental(Rental rental);

    void sendMessageAboutOverdueRental(Rental rental);

    void sendMessageAboutSuccessPayment(Payment payment, Car car);

    void sendMessageAboutCanceledPayment(Payment payment, Car car);

    void sendScheduledMessageAboutOverdueRentals(Set<Rental> overdueRentals);

    void sendNoRentalsOverdueMessage();
}
