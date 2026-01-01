package com.portfolio.bloom.domain.model.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Phone number value object.
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Phone {
    
    @NotBlank(message = "Phone number is required")
    @NotNull(message = "Phone number is required")
    @Pattern(regexp = "^[0-9]{9,10}$", 
         message = "Phone number must contain only digits and be 9-10 characters long")
    @Size(min = 9, max = 10, message = "Phone number must be 9-10 characters long")
    private String phoneNumber;

    @NotNull(message = "Country code is required")
    @NotBlank(message = "Country code is required")
    @Pattern(regexp = "^\\+?[1-9][0-9]{0,2}$", 
         message = "Country code must be between 1 and 999, optionally prefixed with +")
    @Size(min = 1, max = 4, message = "Country code must be 1-3 digits, optionally prefixed with +")
    private String countryCode;

    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber == null) {
            this.phoneNumber = null;
            return;
        }
        this.phoneNumber = phoneNumber.trim();
    }

    public void setCountryCode(String countryCode) {
        if (countryCode == null) {
            this.countryCode = null;
            return;
        }
        this.countryCode = countryCode.trim();
    }
}
