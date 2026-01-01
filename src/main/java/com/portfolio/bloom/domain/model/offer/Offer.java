package com.portfolio.bloom.domain.model.offer;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.portfolio.bloom.common.BaseEntity;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * Offer domain entity for venue discounts.
 */
@Document(collection = "offers")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
@JsonIgnoreProperties
@EqualsAndHashCode(callSuper = true)
public class Offer extends BaseEntity<String> {
    
    @NotNull(message = "Offer start date is required")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Instant startDate;
    
    @NotNull(message = "Offer end date is required")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Instant endDate;

    @NotNull(message = "Offer discount percentage is required")
    @Positive(message = "Offer discount percentage must be greater than 0")
    @Max(value = 100, message = "Offer discount percentage cannot exceed 100%")
    private Double discountPercentage;

    @NotNull(message = "Offer final price is required")
    @DecimalMax(value = "999999.99", message = "Offer final price must be less than 1,000,000")
    @DecimalMin(value = "0.0", inclusive = true, message = "Offer final price must be zero or positive")
    private BigDecimal finalPrice;

    @NotNull(message = "Offer user ID is required")
    @NotBlank(message = "Offer user ID is required")
    private String userId;

    @NotNull(message = "Offer venue ID is required")
    @NotBlank(message = "Offer venue ID is required")
    private String venueId;

    @Builder.Default
    private boolean active = true;

    @AssertTrue(message = "Offer start date must be today or in the future")
    public boolean isStartDateValid() {
        return startDate == null || !startDate.isBefore(Instant.now());
    }

    @AssertTrue(message = "Offer end date must be after start date")
    public boolean isEndDateValid() {
        if (startDate == null || endDate == null) {
            return true; // Let @NotNull handle null validation
        }
        return endDate.isAfter(startDate);
    }
}
