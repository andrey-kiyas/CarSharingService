package com.carsharingservice.service;

import com.carsharingservice.dto.user.UserRegistrationRequestDto;
import com.carsharingservice.dto.user.UserResponseDto;
import com.carsharingservice.exception.RegistrationException;
import org.springframework.security.core.Authentication;

public interface UserService {
    UserResponseDto register(UserRegistrationRequestDto request) throws RegistrationException;

    UserResponseDto updateUserRole(Long id, String roleDto);

    UserResponseDto updateInfo(Authentication authentication,
                               UserRegistrationRequestDto requestDto);
}
