package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserService;
import com.capgemini.wsb.fitnesstracker.user.api.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public List<UserEmailAndIdDto> findUserByEmail(final String email) {
        return userRepository.findAllByEmailContaining(email)
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
        return userRepository.findAll()
                .stream()
                .map(userMapper::toUserBasicInfoDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto findUserById(final Long userId) {
        return userMapper.toUserDto(userRepository.findById(userId).orElseThrow());
    }

    @Override
    public List<UserDto> findAllUsersOlderThan(LocalDate time) {
        return userRepository.findAll()
                .stream()
                .filter(user -> user.getBirthdate().isBefore(time))
                .map(userMapper::toUserDto)
                .toList();
    }

    @Override
    public UserDto createUser(final NewUserDto user) {
        log.info("Creating User {}", user);

        return userMapper.toUserDto(userRepository.save(userMapper.newUserDtoToEntity(user)));
    }

    @Override
    public void deleteUserById(final Long userId) {
        userRepository.deleteUserById(userId);
    }

    @Override
    public UserDto updateUser(Long id, UpdateUserDto userDetails) {
        User user = userRepository.findById(Objects.requireNonNull(id))
                .orElseThrow(() -> new IllegalArgumentException("{User ID not found: " + userDetails.id()));
        user.setFirstName(userDetails.firstName());
        user.setLastName(userDetails.lastName());
        user.setBirthdate(userDetails.birthdate());
        user.setEmail(userDetails.email().toLowerCase());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setAuthorities(String.join(",", userDetails.roles())
        );

        return userMapper.toUserDto(userRepository.save(user));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username);
    }
}