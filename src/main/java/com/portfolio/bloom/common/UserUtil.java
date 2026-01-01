package com.portfolio.bloom.common;

import com.portfolio.bloom.common.translation.LangList;
import com.portfolio.bloom.domain.dto.UserResponseDto;
import com.portfolio.bloom.domain.model.user.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Utility class for getting current authenticated user and language preferences.
 */
@Component
public class UserUtil {

    public UserResponseDto getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication != null && authentication.getPrincipal() instanceof User) {
            User user = (User) authentication.getPrincipal();
            return UserResponseDto.fromUser(user);
        }
        
        return null;
    }

    public User getCurrentUserEntity() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication != null && authentication.getPrincipal() instanceof User) {
            return (User) authentication.getPrincipal();
        }
        
        return null;
    }

    /**
     * Helper method to get language preference from authenticated user or fallback to header.
     * 
     * @param langHeader Language header from request
     * @return Resolved language code
     */
    public String getLanguagePreference(String langHeader) {
        try {
            UserResponseDto currentUser = getCurrentUser();
            // If user has preferred language, use it (when user preference feature is added)
            // For now, use the header
        } catch (Exception e) {
            // User is not authenticated or error occurred, fall back to header
        }
        return LangList.resolveLanguage(langHeader);
    }
}
