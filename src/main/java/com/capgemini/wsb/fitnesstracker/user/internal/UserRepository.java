package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    /**
     * Query searching users by emailFragment address. It matches by exact match.
     *
     * @param emailFragment emailFragment of the user to search
     * @return {@link Optional} containing found user or {@link Optional#empty()} if none matched
     */
    default List<User> findByEmailFragment(String emailFragment) {
        return findAll().stream()
                .filter(user -> user.getEmail().toLowerCase().contains(emailFragment.toLowerCase()))
                .toList();
    }

    /**
     * Delete user by his ID
     *
     * @param id User ID
     */
    default void deleteUserById(Long id) {
        Optional<User> user = findById(id);
        user.ifPresent(this::delete);
    }
}

