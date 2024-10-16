package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor

class UserController {

    private final UserServiceImpl userService;

    private final UserMapper userMapper;
    private final UserRepository userRepository;


    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsersByAge() {
        List<User> users = userService.findAllUsers();

        List<UserDto> userDtos = users.stream()
                .map(userMapper::toDto)
                .toList();

        return ResponseEntity.ok(userDtos);
    }

    @GetMapping("/age")
    public ResponseEntity<List<UserDto>> getAllUsersByAge(@RequestParam Integer age) {
        LocalDate cutoffDate = LocalDate.now().minusYears(age);

        List<User> users = userService.findAllUsers().stream()
                            .filter(user -> user.getBirthdate().isBefore(cutoffDate))
                            .toList();

        List<UserDto> userDtos = users.stream()
                .map(userMapper::toDto)
                .toList();

        return ResponseEntity.ok(userDtos);
    }

    @PostMapping("/add")
    public User addUser(@RequestBody User user) throws InterruptedException {

        // Demonstracja how to use @RequestBody
        System.out.println("User with e-mail: " + user.getEmail() + "passed to the request");

        // TODO: saveUser with Service and return User
        return userRepository.saveUser(user);
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
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }


    @PutMapping("/update/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        return userService.updateUser(id, user);
    }

    @GetMapping("/search/email/prefix")
    public List<UserEmailAndID> getUsersByEmail(@RequestParam String emailPrefix) {

        return userService.getUserByEmail(emailPrefix);

    }
}