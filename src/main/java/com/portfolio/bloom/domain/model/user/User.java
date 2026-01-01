package com.portfolio.bloom.domain.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.portfolio.bloom.common.BaseEntity;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * User domain entity implementing Spring Security's UserDetails.
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
@JsonIgnoreProperties
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity<String> implements UserDetails {

    @NotNull(message = "Firstname cannot be null")
    @NotBlank(message = "Firstname cannot be empty or null")
    private String firstname;

    @NotBlank(message = "Lastname cannot be empty or null")
    private String lastname;

    @Valid
    private Phone phone;

    private Address address;

    @Valid
    private Role role;

    @NotBlank(message = "Email cannot be empty")
    @Email
    @Indexed(unique = true)
    private String email;

    @JsonIgnore
    @Size(min = 8, message = "Password must be at least 8 characters")
    @Pattern(regexp = ".*[!@#$%^&*(),.?\":{}|<>].*", 
         message = "Password must contain at least one special character")
    private String password;

    private boolean emailVerified;

    private List<com.portfolio.bloom.domain.model.Token> tokens;

    public void setEmail(String email) {
        if (email == null) return;
        this.email = email.trim().toLowerCase();
    }

    // UserDetails implementation
    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role != null ? role.getAuthorities() : Role.USER.getAuthorities();
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return email;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return !isDeleted();
    }

    public String getPhoneNumber() {
        return phone != null ? phone.getPhoneNumber() : null;
    }
}
