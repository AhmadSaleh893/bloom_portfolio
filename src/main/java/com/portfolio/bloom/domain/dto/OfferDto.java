package com.portfolio.bloom.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.portfolio.bloom.common.BaseEntity;
import com.portfolio.bloom.domain.model.offer.Offer;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * Data Transfer Object for Offer requests and responses.
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OfferDto extends BaseEntity<String> {

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

    private String userId;
    private String venueId;
    private Boolean active;

    @AssertTrue(message = "Offer start date must be today or in the future")
    public boolean isStartDateValid() {
        return startDate == null || !startDate.isBefore(Instant.now());
    }

    @AssertTrue(message = "Offer end date must be after start date")
    public boolean isEndDateValid() {
        if (startDate == null || endDate == null) {
            return true;
        }
        return endDate.isAfter(startDate);
    }

    public static OfferDto fromEntity(Offer entity) {
        if (entity == null) {
            return null;
        }
        
        return OfferDto.builder()
                .id(entity.getId())
                .deleted(entity.isDeleted())
                .created(entity.getCreated())
                .updated(entity.getUpdated())
                .translations(entity.getTranslations())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .discountPercentage(entity.getDiscountPercentage())
                .finalPrice(entity.getFinalPrice())
                .userId(entity.getUserId())
                .venueId(entity.getVenueId())
                .active(entity.isActive())
                .build();
    }

    public static Offer toEntity(OfferDto dto) {
        return Offer.builder()
                .id(dto.getId())
                .deleted(dto.isDeleted())
                .created(dto.getCreated())
                .updated(dto.getUpdated())
                .translations(dto.getTranslations())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .discountPercentage(dto.getDiscountPercentage())
                .finalPrice(dto.getFinalPrice())
                .userId(dto.getUserId())
                .venueId(dto.getVenueId())
                .active(dto.getActive() != null ? dto.getActive() : true)
                .build();
    }
}
