package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.dto.UserBasicInfoDto;
import com.capgemini.wsb.fitnesstracker.user.api.dto.UserDto;
import com.capgemini.wsb.fitnesstracker.user.api.dto.UserEmailAndIdDto;
import org.springframework.stereotype.Component;

@Component
class UserMapper {

    UserDto toUserDto(User user) {
        return new UserDto(user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getBirthdate(),
                user.getEmail());
    }

    User toEntity(UserDto userDto) {
        return new User(
                userDto.firstName(),
                userDto.lastName(),
                userDto.birthdate(),
                userDto.email());
    }

    UserBasicInfoDto toUserBasicInfoDto(User user) {
        return new UserBasicInfoDto(user.getId(), user.getFirstName() + " " + user.getLastName());
    }

    UserEmailAndIdDto toUserEmailAndIdDto(User user) {
        return new UserEmailAndIdDto(user.getId(), user.getEmail());
    }


}
