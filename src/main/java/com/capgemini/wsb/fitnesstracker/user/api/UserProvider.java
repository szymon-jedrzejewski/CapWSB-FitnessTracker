package com.capgemini.wsb.fitnesstracker.user.api;

import com.capgemini.wsb.fitnesstracker.user.api.dto.UserBasicInfoDto;
import com.capgemini.wsb.fitnesstracker.user.api.dto.UserDto;
import com.capgemini.wsb.fitnesstracker.user.api.dto.UserEmailAndIdDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserProvider extends UserDetailsService {
    /**
     * Retrieves a user based on their email.
     * If the user with given email is not found, then {@link Optional#empty()} will be returned.
     *
     * @param email The email of the user to be searched
     * @return An {@link Optional} containing the located user, or {@link Optional#empty()} if not found
     */
    List<UserEmailAndIdDto> findUserByEmail(String email);

    /**
     * Retrieves all users.
     *
     * @return An {@link Optional} containing the all users,
     */
    List<UserDto> findAllUsers();

    /**
     * Retrieve users first name, last name and id
     *
     * @return An {@link List} containing the all users,
     */
    List<UserBasicInfoDto> findAllUsersBasicInfo();


    /**
     * Retrieve users base on then birthdate
     *
     * @return An {@link List} containing the all users older than given age,
     */
    List<UserDto> findAllUsersOlderThan(LocalDate time);

    /**
     * Retrieves a user based on their ID.
     * If the user with given ID is not found, then {@link Optional#empty()} will be returned.
     *
     * @param userId id of the user to be searched
     * @return An {@link Optional} containing the located user, or {@link Optional#empty()} if not found
     */
    UserDto findUserById(Long userId);
}