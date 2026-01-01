package com.portfolio.bloom.domain.service;

import com.portfolio.bloom.domain.dto.UserDto;
import com.portfolio.bloom.domain.dto.UserResponseDto;
import com.portfolio.bloom.domain.model.Token;
import com.portfolio.bloom.domain.dto.TokenType;
import com.portfolio.bloom.domain.model.user.Role;
import com.portfolio.bloom.domain.model.user.User;
import com.portfolio.bloom.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

/**
 * User service implementation.
 */
@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public Optional<UserResponseDto> registerNewUser(UserDto request, Role role) {
        // Check if user already exists
        if (userRepository.existsByEmailAndDeletedIsFalse(request.getEmail())) {
            return Optional.empty();
        }

        User user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role != null ? role : Role.USER)
                .phone(request.getPhone())
                .address(request.getAddress())
                .emailVerified(false)
                .tokens(new ArrayList<>())
                .build();

        user = userRepository.save(user);
        return Optional.of(UserResponseDto.fromUser(user));
    }

    @Override
    @Transactional
    public void saveUserToken(User user, String jwtToken) {
        Token token = Token.builder()
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .userId(user.getId())
                .created(new Date().getTime())
                .build();

        if (user.getTokens() == null) {
            user.setTokens(new ArrayList<>());
        }
        user.getTokens().add(token);
        user.setUpdated(new Date().getTime());
        userRepository.save(user);
    }

    @Override
    @Transactional
    public Optional<UserResponseDto> updateUser(UserDto userDto) {
        Optional<User> userOpt = userRepository.findById(userDto.getId());
        
        if (userOpt.isEmpty() || userOpt.get().isDeleted()) {
            return Optional.empty();
        }

        User user = userOpt.get();
        
        if (userDto.getFirstname() != null) {
            user.setFirstname(userDto.getFirstname());
        }
        if (userDto.getLastname() != null) {
            user.setLastname(userDto.getLastname());
        }
        if (userDto.getPhone() != null) {
            user.setPhone(userDto.getPhone());
        }
        if (userDto.getAddress() != null) {
            user.setAddress(userDto.getAddress());
        }
        if (userDto.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }

        user.setUpdated(new Date().getTime());
        user = userRepository.save(user);
        
        return Optional.of(UserResponseDto.fromUser(user));
    }

    @Override
    public Optional<UserResponseDto> getUserById(String userId) {
        Optional<User> user = userRepository.findById(userId);
        
        if (user.isEmpty() || user.get().isDeleted()) {
            return Optional.empty();
        }
        
        return Optional.of(UserResponseDto.fromUser(user.get()));
    }
}
