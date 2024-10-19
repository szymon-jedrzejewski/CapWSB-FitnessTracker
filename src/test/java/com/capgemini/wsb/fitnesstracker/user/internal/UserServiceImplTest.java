package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.dto.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static java.time.LocalDate.now;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
class UserServiceImplTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userService;

    private User user;

    @BeforeEach
    public void setUp() {
        user = userRepository.save(new User(
                "Emma",
                "Jhonson",
                now().minusYears(30),
                "test@example.com",
                "123",
                "ROLE_ADMIN")
        );
    }

    @AfterEach
    public void tearDown() throws Exception {
        userRepository.deleteAll();
    }

    @Test
    void testFindUserByEmail() {
        List<UserEmailAndIdDto> result = userService.findUserByEmail("t@ex");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("test@example.com", result.get(0).email());
    }

    @Test
    void testFindAllUsers() {
        userRepository.save(new User(
                "Jan",
                "Kowalski",
                now().minusYears(29),
                "test1@example.com",
                "123",
                "ROLE_ADMIN"
        ));

        List<UserDto> result = userService.findAllUsers();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Emma", result.get(0).firstName());
        assertEquals("Jan", result.get(1).firstName());
    }

    @Test
    void testFindAllUsersBasicInfo() {
        userRepository.save(new User(
                "Jan",
                "Kowalski",
                now().minusYears(29),
                "test1@example.com",
                "123",
                "ROLE_ADMIN"
        ));

        List<UserBasicInfoDto> result = userService.findAllUsersBasicInfo();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Emma Jhonson", result.get(0).fullName());
        assertEquals("Jan Kowalski", result.get(1).fullName());
    }

    @Test
    void testFindUserById() {
        UserDto result = userService.findUserById(user.getId());

        assertNotNull(result);
        assertEquals(user.getId(), result.id());
        assertEquals("Emma", result.firstName());
    }

    @Test
    void testFindAllUsersOlderThan() {
        int age = 19;

        List<UserDto> result = userService.findAllUsersOlderThan(age);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Emma", result.get(0).firstName());
    }

    @Test
    void testCreateUser() {
        NewUserDto newUserDto = new NewUserDto(
                "John",
                "Doe",
                now().minusYears(25),
                "john@example.com",
                "password",
                List.of("ROLE_USER")
        );

        UserDto result = userService.createUser(newUserDto);

        assertNotNull(result);
        assertEquals("John", result.firstName());
    }

    @Test
    void testDeleteUserById() {
        userService.deleteUserById(user.getId());

        assert user.getId() != null;
        assertTrue(userRepository.findById(user.getId()).isEmpty());
    }

    @Test
    void testUpdateUser() {
        UpdateUserDto updateUserDto = new UpdateUserDto(
                user.getId(),
                "John",
                "Doe",
                now().minusYears(25),
                "john@example.com",
                List.of("ROLE_USER"));

        UserDto result = userService.updateUser(updateUserDto);

        assertNotNull(result);
        assertEquals("John", result.firstName());
    }

    @Test
    void testLoadUserByUsername() {
        UserDetails result = userService.loadUserByUsername(user.getEmail());

        assertNotNull(result);
        assertEquals(user.getEmail(), result.getUsername());
    }
}
