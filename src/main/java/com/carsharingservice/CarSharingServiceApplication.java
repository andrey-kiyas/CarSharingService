package com.carsharingservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class CarSharingServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(CarSharingServiceApplication.class, args);
        log.info("API Documentation Overview: http://localhost:8080/api/swagger-ui/index.html#/");
    }
}
