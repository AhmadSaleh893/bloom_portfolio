package com.portfolio.bloom.domain.model.user;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * Address value object.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Address implements Serializable {
    
    @NotBlank(message = "Country must not be empty")
    @NotNull(message = "Country must not be null")
    private String country;

    @NotBlank(message = "City must not be empty")
    @NotNull(message = "City must not be null")
    private String city;
    
    private String street;
    private String state;
    private String postalCode;

    // GPS Coordinates (optional)
    @DecimalMin(value = "-90.0", message = "Latitude must be between -90 and 90 degrees")
    @DecimalMax(value = "90.0", message = "Latitude must be between -90 and 90 degrees")
    private Double latitude;

    @DecimalMin(value = "-180.0", message = "Longitude must be between -180 and 180 degrees")
    @DecimalMax(value = "180.0", message = "Longitude must be between -180 and 180 degrees")
    private Double longitude;
}
