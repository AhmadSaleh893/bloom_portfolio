package com.portfolio.bloom.domain.service;

import com.portfolio.bloom.domain.dto.UserDto;
import com.portfolio.bloom.domain.dto.UserResponseDto;
import com.portfolio.bloom.domain.model.user.Role;
import com.portfolio.bloom.domain.model.user.User;

import java.util.Optional;

/**
 * User service interface.
 */
public interface UserService {
    
    Optional<UserResponseDto> registerNewUser(UserDto request, Role role);
    
    void saveUserToken(User user, String jwtToken);
    
    Optional<UserResponseDto> updateUser(UserDto user);
    
    Optional<UserResponseDto> getUserById(String userId);
}
