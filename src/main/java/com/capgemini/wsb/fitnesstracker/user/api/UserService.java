package com.capgemini.wsb.fitnesstracker.user.api;

import com.capgemini.wsb.fitnesstracker.user.api.dto.NewUserDto;
import com.capgemini.wsb.fitnesstracker.user.api.dto.UpdateUserDto;
import com.capgemini.wsb.fitnesstracker.user.api.dto.UserDto;

/**
 * Interface (API) for modifying operations on {@link User} entities through the API.
 * Implementing classes are responsible for executing changes within a database transaction, whether by continuing an existing transaction or creating a new one if required.
 */
public interface UserService extends UserProvider {

    UserDto createUser(NewUserDto user);

    /**
     * Delete user by his ID
     *
     * @param userId User ID
     */
    void deleteUserById(Long userId);

    UserDto updateUser(Long id, UpdateUserDto user);

}
