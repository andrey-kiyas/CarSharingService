package com.carsharingservice.service.impl;

import com.carsharingservice.dto.user.UserRegistrationRequestDto;
import com.carsharingservice.dto.user.UserResponseDto;
import com.carsharingservice.exception.EntityNotFoundException;
import com.carsharingservice.exception.RegistrationException;
import com.carsharingservice.mapper.UserMapper;
import com.carsharingservice.model.Role;
import com.carsharingservice.model.User;
import com.carsharingservice.repository.RoleRepository;
import com.carsharingservice.repository.UserRepository;
import com.carsharingservice.service.UserService;
import java.util.HashSet;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto request)
            throws RegistrationException {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RegistrationException("Email already registered!");
        }
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        Role userRole = roleRepository.findRoleByName(Role.RoleName.CUSTOMER)
                .orElseThrow(() -> new RegistrationException("Can't find role by name"));
        Set<Role> defaultUserRoleSet = new HashSet<>();
        defaultUserRoleSet.add(userRole);
        user.setRoles(defaultUserRoleSet);
        return userMapper.toUserResponseDto(userRepository.save(user));
    }

    @Override
    public UserResponseDto updateUserRole(Long id, String roleDto) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find user name with id: " + id)
        );
        Role roleByName = roleRepository
                .findRoleByName(Role.RoleName.valueOf(roleDto))
                .orElseThrow(
                        () -> new EntityNotFoundException("Can't find role name: " + roleDto)
                );
        user.getRoles().add(roleByName);
        return userMapper.toUserResponseDto(userRepository.save(user));
    }

    @Override
    public UserResponseDto updateInfo(
            Authentication authentication, UserRegistrationRequestDto requestDto) {
        final User userFromAuthentication = (User) authentication.getPrincipal();
        userFromAuthentication.setEmail(requestDto.getEmail());
        userFromAuthentication.setFirstName(requestDto.getFirstName());
        userFromAuthentication.setLastName(requestDto.getLastName());
        userFromAuthentication.setPassword(requestDto.getPassword());
        return userMapper.toUserResponseDto(userRepository.save(userFromAuthentication));
    }
}
