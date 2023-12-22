package com.carsharingservice.service.impl;

import com.carsharingservice.dto.rental.CreateRentalRequestDto;
import com.carsharingservice.dto.rental.RentalDto;
import com.carsharingservice.exception.EntityNotFoundException;
import com.carsharingservice.exception.NoAvailableCarsException;
import com.carsharingservice.exception.RentalIsNotActiveException;
import com.carsharingservice.mapper.RentalMapper;
import com.carsharingservice.model.Car;
import com.carsharingservice.model.Rental;
import com.carsharingservice.repository.CarRepository;
import com.carsharingservice.repository.RentalRepository;
import com.carsharingservice.repository.UserRepository;
import com.carsharingservice.service.RentalService;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RentalServiceImpl implements RentalService {
    private final CarRepository carRepository;
    private final UserRepository userRepository;
    private final RentalRepository rentalRepository;
    private final RentalMapper rentalMapper;

    @Override
    @Transactional
    public RentalDto createRental(CreateRentalRequestDto requestDto, Long userId) {
        Car car = carRepository.findById(requestDto.carId())
                .orElseThrow(() -> new EntityNotFoundException("Can't find car by id "
                        + requestDto.carId()));
        if (car.getInventory() < 1) {
            throw new NoAvailableCarsException("There are no free cars left for rent!");
        }
        car.setInventory(car.getInventory() - 1);
        Rental rental = new Rental();
        rental.setCar(car);
        rental.setUser(userRepository.getReferenceById(userId));
        rental.setRentalDateTime(LocalDateTime.now());
        rental.setReturnDateTime(requestDto.returnDateTime());
        return rentalMapper.toDto(rentalRepository.save(rental));
    }

    @Override
    public List<RentalDto> getAllRentals(Long userId, Pageable pageable) {
        return rentalRepository.getAllByUserId(userId, pageable).stream()
                .map(rentalMapper::toDto)
                .toList();
    }

    @Override
    public List<RentalDto> getAllActiveRentals(Long userId, Pageable pageable) {
        return rentalRepository.getAllByUserIdAndActualReturnDateTimeIsNull(userId, pageable)
                .stream()
                .map(rentalMapper::toDto)
                .toList();
    }

    @Override
    public List<RentalDto> getAllNotActiveRentals(Long userId, Pageable pageable) {
        return rentalRepository.getAllByUserIdAndActualReturnDateTimeIsNotNull(userId, pageable)
                .stream()
                .map(rentalMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public RentalDto setActualReturnDate(Long rentalId) {
        Rental rental = rentalRepository.findById(rentalId)
                .orElseThrow(() -> new EntityNotFoundException("Can't find rental by id: "
                        + rentalId));
        if (rental.getActualReturnDateTime() != null) {
            throw new RentalIsNotActiveException("This rental has been already returned!");
        }
        rental.setActualReturnDateTime(LocalDateTime.now());
        if (rental.getActualReturnDateTime().isAfter(rental.getReturnDateTime())) {
            System.out.println("Fine");
        }
        Car car = rental.getCar();
        car.setInventory(car.getInventory() + 1);
        return rentalMapper.toDto(rental);
    }
}
