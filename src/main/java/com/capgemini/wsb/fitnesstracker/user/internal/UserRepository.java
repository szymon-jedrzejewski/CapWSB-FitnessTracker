package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.dto.UserEmailAndIdDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

interface UserRepository extends JpaRepository<User, Long> {


    /**
     * Save user
     * @param user User
     * @return User
     */
    default User saveUser(User user){
        return save(user);
    }

    /**
     * Query searching users by email address. It matches by exact match.
     *
     * @param email email of the user to search
     * @return {@link Optional} containing found user or {@link Optional#empty()} if none matched
     */
    default List<User> findByEmail(String email) {
        return findAll().stream()
                .filter(user ->  user.getEmail().toLowerCase().contains(email.toLowerCase()))
                .toList();
    }

    /**
     * List all users by their first name, last name and ID
     * @param id User's ID
     * @param firstName User's first name
     * @param lastName User's last name
     * @return {@link List} containing users
     */
    default List<User> findUsersByBasicInfo(Long id, String firstName, String lastName) {
        return findAll().stream()
                .filter(user -> id == null || Objects.equals(user.getId(), id))
                .filter(user -> firstName == null || Objects.equals(user.getFirstName(), firstName))
                .filter(user -> lastName == null || Objects.equals(user.getLastName(), lastName))
                .collect(Collectors.toList());
    }

    /**
     * Delete user by his ID
     * @param id User ID
     */
    default void  deleteUserById(Long id){
        Optional<User> user = findById(id);
        if(user.isPresent()){
            delete(user.get());
        }
        else{
            throw new IllegalArgumentException("User ID: " + id + " not found");
        }
    }

    default List<UserEmailAndIdDto> getUserByEmail(String email){

        List <User> users = this.findByEmail(email);

        List<UserEmailAndIdDto> userDto;

        userDto = users.stream()
                .map(user -> new UserEmailAndIdDto(user.getId(), user.getEmail()))
                .toList();

        return userDto;
    }
}

