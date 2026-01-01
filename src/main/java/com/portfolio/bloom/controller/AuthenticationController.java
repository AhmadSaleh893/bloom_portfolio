package com.portfolio.bloom.controller;

import com.portfolio.bloom.domain.dto.AuthenticationRequest;
import com.portfolio.bloom.domain.dto.AuthenticationResponse;
import com.portfolio.bloom.domain.dto.UserDto;
import com.portfolio.bloom.domain.dto.UserResponseDto;
import com.portfolio.bloom.domain.model.user.Role;
import com.portfolio.bloom.domain.service.UserService;
import com.portfolio.bloom.security.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Authentication controller for registration and login.
 */
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid UserDto request) {
        Optional<UserResponseDto> user = userService.registerNewUser(request, Role.USER);
        
        if (user.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(user.get());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Email already exists or invalid data");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody @Valid AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
}
