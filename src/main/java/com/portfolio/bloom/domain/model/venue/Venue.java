package com.portfolio.bloom.domain.model.venue;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.portfolio.bloom.common.BaseEntity;
import com.portfolio.bloom.domain.model.offer.Offer;
import com.portfolio.bloom.domain.model.user.Address;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.List;

/**
 * Venue domain entity.
 */
@Document(collection = "venues")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
@JsonIgnoreProperties
@EqualsAndHashCode(callSuper = true)
public class Venue extends BaseEntity<String> {
    
    @NotBlank(message = "Venue name is required")
    @Size(max = 100, message = "Venue name must not exceed 100 characters")
    private String venueName;
    
    @Indexed
    @NotBlank(message = "User ID is required")
    private String userId;

    @Valid
    @NotNull(message = "Address is required")
    private Address address;

    @Min(value = 1, message = "Capacity must be at least 1")
    @Max(value = 50000, message = "Capacity cannot exceed 50,000")
    @NotNull(message = "Venue capacity is required")
    private int capacity;

    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;

    @DecimalMax(value = "999999.99", message = "Venue high price must be less than 1,000,000")
    @DecimalMin(value = "0.0", inclusive = true, message = "Venue high price must be a positive number")
    private BigDecimal highPrice;

    @DecimalMax(value = "999999.99", message = "Venue low price must be less than 1,000,000")
    @DecimalMin(value = "0.0", inclusive = true, message = "Venue low price must be a positive number")
    private BigDecimal lowPrice;

    @NotNull(message = "Venue type is required")
    private VenueType venueType;
    
    @NotNull(message = "People type is required")
    private PeopleType peopleType;

    @Min(value = 0, message = "Rating must be at least 0")
    @Max(value = 5, message = "Rating cannot exceed 5")
    @Builder.Default
    private double rating = 0.0;

    @Builder.Default
    private PriceType priceType = PriceType.LOWPRICE;

    @NotBlank(message = "Base currency is required")
    @Size(min = 3, max = 3, message = "Base currency must be 3 characters (ISO 4217)")
    @Builder.Default
    private String baseCurrency = "USD";

    private List<@Valid Offer> offers;

    private String baseImage;
}
