package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Query searching users by email address. It matches by exact match.
     *
     * @param email fragment of the user to search
     * @return {@link User} exact found user
     */
    User findByEmail(String email);

    /**
     * Query searching users by emailFragment address. It matches by fragment match.
     *
     * @param fragment fragment of the user to search
     * @return {@link List} containing found users or empty {@link List} if none matched
     */
    List<User> findAllByEmailContaining(String fragment);

    /**
     * Delete user by users ID
     *
     * @param id User ID
     */
    default void deleteUserById(Long id) {
        Optional<User> user = findById(id);
        user.ifPresent(this::delete);
    }
}

