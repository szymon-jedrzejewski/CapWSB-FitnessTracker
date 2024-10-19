package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.dto.NewUserDto;
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

    @GetMapping("/search/all")
    public ResponseEntity<List<UserDto>> getAllUsersByAge() {
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @GetMapping("/search/age")
    public ResponseEntity<List<UserDto>> getAllUsersByAge(@RequestParam Integer age) {
        return ResponseEntity.ok(userService.findAllUsersOlderThan(age));
    }

    @GetMapping("/search/basic")
    public List<UserBasicInfoDto> getAllBasicInfo() {
        return userService.findAllUsersBasicInfo();
    }

    @GetMapping("/search/{id}")
    public Optional<User> getUserById(@PathVariable long id) {
        return userService.findUserById(id);
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
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto user) {
        return ResponseEntity.ok(userService.updateUser(user));
    }

    @GetMapping("/search/email")
    public List<UserEmailAndIdDto> getUsersByEmail(@RequestParam String fragment) {
        return userService.getUserByEmail(fragment);
    }
}