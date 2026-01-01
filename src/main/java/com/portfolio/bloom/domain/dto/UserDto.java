package com.portfolio.bloom.domain.dto;

import com.portfolio.bloom.common.BaseEntity;
import com.portfolio.bloom.domain.model.user.Address;
import com.portfolio.bloom.domain.model.user.Phone;
import com.portfolio.bloom.domain.model.user.Role;
import com.portfolio.bloom.domain.model.user.User;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * Data Transfer Object for User requests.
 */
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserDto extends BaseEntity<String> {

    @NotNull(message = "Firstname cannot be null")
    @NotBlank(message = "Firstname cannot be empty or null")
    private String firstname;

    @NotBlank(message = "Lastname cannot be empty or null")
    private String lastname;

    @Valid
    @NotNull(message = "Phone number cannot be null")
    private Phone phone;

    @Valid
    private Address address;

    @Valid
    private Role role;

    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Invalid email format")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
        message = "Invalid email format")
    @Size(max = 254, message = "Email must not exceed 254 characters")
    private String email;

    @Size(min = 8, message = "Password must be at least 8 characters")
    @Pattern(regexp = ".*[!@#$%^&*(),.?\":{}|<>].*", 
         message = "Password must contain at least one special character")
    private String password;

    public void setEmail(String email) {
        if (email == null) return;
        this.email = email.trim().toLowerCase();
    }

    public void setPassword(String password) {
        if (password == null) return;
        this.password = password.trim();
    }

    public String getPhoneNumber() {
        return phone != null ? phone.getPhoneNumber() : null;
    }

    public String getUsername() {
        return getPhoneNumber();
    }
}
