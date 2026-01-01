package com.portfolio.bloom.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.portfolio.bloom.domain.dto.TokenType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Authentication response DTO containing JWT tokens and user info.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

    @JsonProperty("access_token")
    private String accessToken;
    
    @JsonProperty("refresh_token")
    private String refreshToken;
    
    @JsonProperty("token_type")
    private TokenType tokenType;
    
    @JsonProperty("user")
    private UserResponseDto user;
}
