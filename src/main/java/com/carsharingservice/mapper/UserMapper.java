package com.carsharingservice.mapper;

import com.carsharingservice.config.MapperConfig;
import com.carsharingservice.dto.user.UserRegistrationRequestDto;
import com.carsharingservice.dto.user.UserResponseDto;
import com.carsharingservice.model.User;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    User toModel(UserRegistrationRequestDto requestDto);

    UserResponseDto toUserResponseDto(User user);
}
