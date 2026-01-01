package com.portfolio.bloom.security;

import com.portfolio.bloom.config.JwtService;
import com.portfolio.bloom.domain.dto.AuthenticationRequest;
import com.portfolio.bloom.domain.dto.AuthenticationResponse;
import com.portfolio.bloom.domain.dto.TokenType;
import com.portfolio.bloom.domain.dto.UserResponseDto;
import com.portfolio.bloom.domain.model.user.User;
import com.portfolio.bloom.domain.repository.UserRepository;
import com.portfolio.bloom.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Authentication service for handling login and token generation.
 */
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        Optional<User> userOptional = userRepository.findByEmailOrPhoneNumberAndDeletedIsFalse(request.getUsername());

        if (userOptional.isEmpty()) {
            throw new BadCredentialsException("Invalid username or password");
        }

        User user = userOptional.get();

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        
        userService.saveUserToken(user, jwtToken);

        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .tokenType(TokenType.BEARER)
                .user(UserResponseDto.fromUser(user))
                .build();
    }
}
