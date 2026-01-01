package com.portfolio.bloom.domain.model.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Permission enum for role-based access control.
 */
@Getter
@RequiredArgsConstructor
public enum Permission {
    ADMIN_ACCESS("admin:access"),
    USER_ACCESS("user:access");
    
    private final String permission;
}
