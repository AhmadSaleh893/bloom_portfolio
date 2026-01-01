package com.portfolio.bloom.domain.model;

import com.portfolio.bloom.common.BaseEntity;
import com.portfolio.bloom.domain.dto.TokenType;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * JWT token entity for token management.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Token extends BaseEntity<String> {
    
    private String token;
    
    @NotBlank
    @Builder.Default
    private TokenType tokenType = TokenType.BEARER;
    
    private boolean revoked;
    private boolean expired;
    private String userId;
}
