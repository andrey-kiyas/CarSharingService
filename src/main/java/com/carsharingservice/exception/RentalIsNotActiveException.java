package com.carsharingservice.exception;

public class RentalIsNotActiveException extends RuntimeException {
    public RentalIsNotActiveException(String message) {
        super(message);
    }
}
