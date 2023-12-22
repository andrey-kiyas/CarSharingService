package com.carsharingservice.controler;

import com.carsharingservice.dto.rental.CreateRentalRequestDto;
import com.carsharingservice.dto.rental.RentalDto;
import com.carsharingservice.model.User;
import com.carsharingservice.service.RentalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Positive;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequiredArgsConstructor
@Tag(name = "Rental management", description = "Endpoints for managing cars rentals")
@RequestMapping("/rentals")
public class RentalController {
    private final RentalService rentalService;

    @Operation(summary = "Create new rental",
            description = "Creating a new rental Prams: carId, returnDateTime")
    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping
    public RentalDto createRental(@RequestBody CreateRentalRequestDto requestDto,
                                  Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return rentalService.createRental(requestDto, user.getId());
    }

    @Operation(summary = "Get all rentals",
            description = "Get all user rentals (Pageable default: page = 0, size = 10)")
    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping
    public List<RentalDto> getAll(@PageableDefault(page = 0, size = 10) Pageable pageable,
                                  Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return rentalService.getAllRentals(user.getId(), pageable);
    }

    @Operation(summary = "Get all active rentals",
            description = "Get all rentals by it's activity"
                    + " (Pageable default: page = 0, size = 10)")
    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/")
    public List<RentalDto> getAllRentalsByActivities(
            @RequestParam(name = "is_active") boolean isActive,
            Authentication authentication,
            @PageableDefault(page = 0, size = 10)
                    Pageable pageable) {
        User user = (User) authentication.getPrincipal();
        if (isActive) {
            return rentalService.getAllActiveRentals(user.getId(), pageable);
        } else {
            return rentalService.getAllNotActiveRentals(user.getId(), pageable);
        }
    }

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping("/search/")
    @Operation(summary = "Search rentals",
            description = """
                    Searching rentals using userId and activities,
                    default activities = true
                    default page = 0, size = 10
                    """)
    public List<RentalDto> getAllRentalsByUserAndActivities(
            @RequestParam(name = "user_id") Long userId,
            @RequestParam(name = "is_active", defaultValue = "true") boolean isActive,
            @PageableDefault(page = 0, size = 10) Pageable pageable) {
        if (isActive) {
            return rentalService.getAllActiveRentals(userId, pageable);
        } else {
            return rentalService.getAllNotActiveRentals(userId, pageable);
        }
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping("/{id}/return")
    @Operation(summary = "Return rental by id",
            description = "Returning rental by setting actual return date")
    public RentalDto returnRental(@PathVariable @Positive Long id) {
        return rentalService.setActualReturnDate(id);
    }
}

