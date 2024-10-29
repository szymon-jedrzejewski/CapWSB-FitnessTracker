package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.dto.NewUserDto;
import com.capgemini.wsb.fitnesstracker.user.api.dto.UserBasicInfoDto;
import com.capgemini.wsb.fitnesstracker.user.api.dto.UserDto;
import com.capgemini.wsb.fitnesstracker.user.api.dto.UserEmailAndIdDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
class UserMapper {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    UserDto toUserDto(User user) {
        return new UserDto(user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getBirthdate(),
                user.getEmail(),
                List.copyOf(user.getAuthorities()));
    }

    User newUserDtoToEntity(NewUserDto user) {
        return new User(
                user.firstName(),
                user.lastName(),
                user.birthdate(),
                user.email().toLowerCase(),
                bCryptPasswordEncoder.encode(user.password()),
                user.roles().stream().map(role -> "ROLE_" + role).collect(Collectors.joining(",")));
    }

    UserBasicInfoDto toUserBasicInfoDto(User user) {
        return new UserBasicInfoDto(user.getId(), user.getFirstName(), user.getLastName());
    }

    UserEmailAndIdDto toUserEmailAndIdDto(User user) {
        return new UserEmailAndIdDto(user.getId(), user.getEmail());
    }


}
