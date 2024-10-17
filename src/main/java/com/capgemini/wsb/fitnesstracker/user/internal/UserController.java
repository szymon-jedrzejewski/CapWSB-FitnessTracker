package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.dto.UserBasicInfoDto;
import com.capgemini.wsb.fitnesstracker.user.api.dto.UserDto;
import com.capgemini.wsb.fitnesstracker.user.api.dto.UserEmailAndIdDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
class UserController {

    private final UserServiceImpl userService;

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsersByAge() {
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @GetMapping("/age")
    public ResponseEntity<List<UserDto>> getAllUsersByAge(@RequestParam Integer age) {
        return ResponseEntity.ok(userService.findAllUsersOlderThan(age));
    }

    @GetMapping("/basic")
    public List<UserBasicInfoDto> getAllBasicInfo() {
        return userService.findAllUsersBasicInfo();
    }

    @GetMapping("/{id}")
    public Optional<User> getUserById(@PathVariable long id) {
        return userService.findUserById(id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUserById(id);
            return ResponseEntity.noContent().build(); // HTTP 204 No Content on successful deletion
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build(); // HTTP 404 Not Found if user not found
        }
    }

    @PostMapping("/create")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto user) {
        return ResponseEntity.ok(userService.createUser(user));
    }

    @PutMapping("/update")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto user) {
        return ResponseEntity.ok(userService.updateUser(user));
    }

    @GetMapping("/search/email/prefix")
    public List<UserEmailAndIdDto> getUsersByEmail(@RequestParam String emailPrefix) {
        return userService.getUserByEmail(emailPrefix);
    }
}