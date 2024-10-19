package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
class UserController {

    private final UserServiceImpl userService;

    @GetMapping("/search/all")
    public ResponseEntity<List<UserDto>> getAllUsersByAge() {
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @GetMapping("/search/age")
    public ResponseEntity<List<UserDto>> getAllUsersByAge(@RequestParam Integer age) {
        return ResponseEntity.ok(userService.findAllUsersOlderThan(age));
    }

    @GetMapping("/search/basic")
    public ResponseEntity<List<UserBasicInfoDto>> getAllBasicInfo() {
        return ResponseEntity.ok(userService.findAllUsersBasicInfo());
    }

    @GetMapping("/search/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable long id) {
        return ResponseEntity.ok(userService.findUserById(id));
    }

    @GetMapping("/search/email")
    public ResponseEntity<List<UserEmailAndIdDto>> getUsersByEmail(@RequestParam String fragment) {
        return ResponseEntity.ok(userService.findUserByEmail(fragment));
    }

    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUserById(id);
            return ResponseEntity.noContent().build(); // HTTP 204 No Content on successful deletion
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build(); // HTTP 404 Not Found if user not found
        }
    }

    @PostMapping("/admin/create")
    public ResponseEntity<UserDto> createUser(@RequestBody NewUserDto user) {
        return ResponseEntity.ok(userService.createUser(user));
    }

    @PutMapping("/admin/update")
    public ResponseEntity<UserDto> updateUser(@RequestBody UpdateUserDto user) {
        return ResponseEntity.ok(userService.updateUser(user));
    }
}