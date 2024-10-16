package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserProvider;
import com.capgemini.wsb.fitnesstracker.user.api.UserService;
import com.capgemini.wsb.fitnesstracker.user.api.dto.UserBasicInfoDto;
import com.capgemini.wsb.fitnesstracker.user.api.dto.UserDto;
import com.capgemini.wsb.fitnesstracker.user.api.dto.UserEmailAndIdDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
class UserServiceImpl implements UserService, UserProvider {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDto createUser(final UserDto user) {
        log.info("Creating User {}", user);

        if (user.id() != null) {
            throw new IllegalArgumentException("User has already DB ID, update is not permitted!");
        }
        return userMapper.toUserDto(userRepository.save(userMapper.toEntity(user)));
    }

    @Override
    public List<UserEmailAndIdDto> getUserByEmail(final String email) {
        return userRepository.findByEmailFragment(email)
                .stream()
                .map(userMapper::toUserEmailAndIdDto)
                .toList();
    }

    @Override
    public List<UserDto> findAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toUserDto)
                .toList();
    }

    @Override
    public List<UserBasicInfoDto> findAllUsersBasicInfo() {
        return userRepository.findAll().stream()
                .map(user -> new UserBasicInfoDto(user.getId(), user.getFirstName() + " " + user.getLastName()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<User> findUserById(final Long userId) {
        return userRepository.findAll().stream()
                .filter(user -> Objects.equals(user.getId(), userId))
                .findFirst();
    }

    @Override
    public List<UserDto> findAllUsersOlderThan(int age) {
        return userRepository.findAll()
                .stream()
                .filter(user -> user.getBirthdate().isBefore(LocalDate.now().minusYears(age)))
                .map(userMapper::toUserDto)
                .toList();
    }

    @Override
    public void deleteUserById(final Long userId) {
        userRepository.deleteUserById(userId);
    }

    @Override
    public UserDto updateUser(UserDto userDetails) {
        User user = userRepository.findById(Objects.requireNonNull(userDetails.id()))
                .orElseThrow(() -> new IllegalArgumentException("{User ID not found: " + userDetails.id()));
        user.setFirstName(userDetails.firstName());
        user.setLastName(userDetails.lastName());
        user.setBirthdate(userDetails.birthdate());
        user.setEmail(userDetails.email());

        return userMapper.toUserDto(userRepository.save(user));
    }
}