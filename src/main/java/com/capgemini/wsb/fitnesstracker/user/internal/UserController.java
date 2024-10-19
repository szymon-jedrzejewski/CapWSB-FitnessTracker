package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
class UserController {

    private final UserServiceImpl userService;

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @GetMapping("/older/{time}")
    public ResponseEntity<List<UserDto>> getAllUsersByAge(@PathVariable LocalDate time) {
        return ResponseEntity.ok(userService.findAllUsersOlderThan(time));
    }

    @GetMapping("/simple")
    public ResponseEntity<List<UserBasicInfoDto>> getAllBasicInfo() {
        return ResponseEntity.ok(userService.findAllUsersBasicInfo());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable long id) {
        return ResponseEntity.ok(userService.findUserById(id));
    }

    @GetMapping("/email")
    public ResponseEntity<List<UserEmailAndIdDto>> getUsersByEmail(@RequestParam String email) {
        return ResponseEntity.ok(userService.findUserByEmail(email));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {

        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody NewUserDto user) {
        UserDto createdUser = userService.createUser(user);
        return ResponseEntity.created(URI.create("/v1/users/" + createdUser.id())).body(createdUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UpdateUserDto user) {

        return ResponseEntity.ok(userService.updateUser(id, user));
    }
}