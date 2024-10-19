package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.UserService;
import com.capgemini.wsb.fitnesstracker.user.api.dto.NewUserDto;
import com.capgemini.wsb.fitnesstracker.user.api.dto.UpdateUserDto;
import com.capgemini.wsb.fitnesstracker.user.api.dto.UserDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.time.LocalDate.now;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Slf4j
class UserControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserService userService;

    private UserDto user1;
    private UserDto user2;

    @BeforeEach
    void setUp() {
        user1 = userService.createUser(new NewUserDto(
                "John",
                "Doe",
                now().minusYears(30),
                "john.doe@example.com",
                "25",
                List.of("ADMIN"
                )));
        user2 = userService.createUser(new NewUserDto(
                "Jane",
                "Doe",
                now().minusYears(25),
                "jane.doe@example.com",
                "25",
                List.of("ADMIN"
                )));
    }

    @AfterEach
    void tearDown() {
        userService.deleteUserById(user1.id());
        userService.deleteUserById(user2.id());
    }

    @Test
    void getAllUsersByAge_shouldReturnAllUsers() throws Exception {
        mockMvc.perform(get("/v1/users/search/all")
                        .with(httpBasic("john.doe@example.com", "25")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].firstName", is("John")))
                .andExpect(jsonPath("$[1].firstName", is("Jane")));
    }

    @Test
    void getAllUsersByAge_withAgeParam_shouldReturnFilteredUsers() throws Exception {
        mockMvc.perform(get("/v1/users/search/age")
                        .with(httpBasic("john.doe@example.com", "25"))
                        .param("age", "25"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].firstName", is("John")));
    }

    @Test
    void getAllBasicInfo_shouldReturnAllBasicInfo() throws Exception {
        mockMvc.perform(get("/v1/users/search/basic")
                        .with(httpBasic("john.doe@example.com", "25")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].fullName", is("John Doe")))
                .andExpect(jsonPath("$[1].fullName", is("Jane Doe")));
    }

    @Test
    void getUserById_shouldReturnUser() throws Exception {
        assert user1.id() != null;
        mockMvc.perform(get("/v1/users/search/{id}", user1.id())
                        .with(httpBasic("john.doe@example.com", "25")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(user1.id().intValue())))
                .andExpect(jsonPath("$.firstName", is("John")));
    }

    @Test
    void getUsersByEmail_shouldReturnUsersMatchingEmail() throws Exception {
        mockMvc.perform(get("/v1/users/search/email")
                        .with(httpBasic("john.doe@example.com", "25"))
                        .param("fragment", "jane"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1))) // Assumes one user has this email fragment
                .andExpect(jsonPath("$[0].email", is("jane.doe@example.com")));
    }

    @Test
    void createUser_shouldCreateNewUser() throws Exception {
        NewUserDto newUser = new NewUserDto("Alice", "Wonderland", now().minusYears(30), "alice@example.com", "22", List.of("ROLE_ADMIN"));
        mockMvc.perform(post("/v1/users/admin/create")
                        .with(httpBasic("john.doe@example.com", "25"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is("Alice")));
    }

    @Test
    void updateUser_shouldUpdateUser() throws Exception {
        UpdateUserDto updateUser = new UpdateUserDto(user2.id(), "Mateusz", "Doe", now().minusYears(30), "updated@example.com", List.of("ROLE_ADMIN"));
        mockMvc.perform(put("/v1/users/admin/update")
                        .with(httpBasic("john.doe@example.com", "25"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is("Mateusz")))
                .andExpect(jsonPath("$.email", is("updated@example.com")));
    }

    @Test
    void deleteUser_shouldDeleteUserSuccessfully() throws Exception {
        mockMvc.perform(delete("/v1/users/admin/delete/{id}", user2.id())
                        .with(httpBasic("john.doe@example.com", "25")))
                .andExpect(status().isNoContent());
    }
}

