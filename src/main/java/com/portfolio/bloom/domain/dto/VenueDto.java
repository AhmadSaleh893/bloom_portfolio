package com.portfolio.bloom.domain.dto;

import com.portfolio.bloom.common.BaseEntity;
import com.portfolio.bloom.common.translation.LangList;
import com.portfolio.bloom.domain.dto.OfferDto;
import com.portfolio.bloom.domain.model.user.Address;
import com.portfolio.bloom.domain.model.venue.PeopleType;
import com.portfolio.bloom.domain.model.venue.PriceType;
import com.portfolio.bloom.domain.model.venue.Venue;
import com.portfolio.bloom.domain.model.venue.VenueType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Data Transfer Object for Venue requests and responses.
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class VenueDto extends BaseEntity<String> {

    @NotNull(message = "Venue name is required")
    @NotBlank(message = "Venue name is required")
    @Size(max = 100, message = "Venue name must not exceed 100 characters")
    private String venueName;

    @Valid
    @NotNull(message = "Address is required")
    private Address address;

    private String userId;

    @Min(value = 1, message = "Capacity must be at least 1")
    @Max(value = 50000, message = "Capacity cannot exceed 50,000")
    @NotNull(message = "Venue capacity is required")
    private Integer capacity;

    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;

    @NotNull(message = "Venue high price is required")
    @DecimalMax(value = "999999.99", message = "Venue high price must be less than 1,000,000")
    @DecimalMin(value = "0.0", inclusive = true, message = "Venue high price must be a positive number")
    private BigDecimal highPrice;

    @NotNull(message = "Venue low price is required")
    @DecimalMax(value = "999999.99", message = "Venue low price must be less than 1,000,000")
    @DecimalMin(value = "0.0", inclusive = true, message = "Venue low price must be a positive number")
    private BigDecimal lowPrice;

    @NotNull(message = "Venue type is required")
    private String venueType;
    
    private String peopleType;

    private Double rating;

    @Builder.Default
    private String priceType = PriceType.LOWPRICE.name();

    @Size(min = 3, max = 3, message = "Base currency must be 3 characters (ISO 4217)")
    @Builder.Default
    private String baseCurrency = "USD";

    private List<OfferDto> offers;

    private String baseImage;

    public VenueType getVenueTypeEnum() {
        try {
            return venueType != null ? VenueType.valueOf(venueType.toUpperCase()) : null;
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
    
    public PeopleType getPeopleTypeEnum() {
        try {
            return peopleType != null ? PeopleType.valueOf(peopleType.toUpperCase()) : null;
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public PriceType getPriceTypeEnum() {
        try {
            return priceType != null ? PriceType.valueOf(priceType.toUpperCase()) : null;
        } catch (IllegalArgumentException e) {
            return PriceType.LOWPRICE;
        }
    }

    public static VenueDto fromEntity(Venue entity) {
        if (entity == null) {
            return null;
        }
        
        VenueDto dto = VenueDto.builder()
                .id(entity.getId())
                .deleted(entity.isDeleted())
                .created(entity.getCreated())
                .updated(entity.getUpdated())
                .translations(entity.getTranslations())
                .venueName(entity.getVenueName())
                .userId(entity.getUserId())
                .address(entity.getAddress())
                .capacity(entity.getCapacity())
                .description(entity.getDescription())
                .highPrice(entity.getHighPrice())
                .lowPrice(entity.getLowPrice())
                .venueType(entity.getVenueType() != null ? entity.getVenueType().name() : null)
                .peopleType(entity.getPeopleType() != null ? entity.getPeopleType().name() : null)
                .rating(entity.getRating())
                .priceType(entity.getPriceType() != null ? entity.getPriceType().name() : null)
                .baseCurrency(entity.getBaseCurrency())
                .baseImage(entity.getBaseImage())
                .build();

        if (entity.getOffers() != null) {
            dto.setOffers(entity.getOffers().stream()
                    .map(OfferDto::fromEntity)
                    .collect(Collectors.toList()));
        }

        return dto;
    }

    public static Venue toEntity(VenueDto dto) {
        Venue entity = Venue.builder()
                .id(dto.getId())
                .deleted(dto.isDeleted())
                .created(dto.getCreated())
                .updated(dto.getUpdated())
                .translations(dto.getTranslations())
                .venueName(dto.getVenueName())
                .userId(dto.getUserId())
                .address(dto.getAddress())
                .capacity(dto.getCapacity())
                .description(dto.getDescription())
                .highPrice(dto.getHighPrice())
                .lowPrice(dto.getLowPrice())
                .venueType(dto.getVenueTypeEnum())
                .peopleType(dto.getPeopleTypeEnum())
                .rating(dto.getRating() != null ? dto.getRating() : 0.0)
                .priceType(dto.getPriceTypeEnum())
                .baseCurrency(dto.getBaseCurrency())
                .baseImage(dto.getBaseImage())
                .build();

        if (dto.getOffers() != null) {
            entity.setOffers(dto.getOffers().stream()
                    .map(OfferDto::toEntity)
                    .collect(Collectors.toList()));
        }

        return entity;
    }

    /**
     * Translates venue DTO fields based on the provided language.
     * 
     * @param venueDto Venue DTO to translate
     * @param lang Language code (e.g., "en", "ar", "he")
     */
    public static void translateVenue(VenueDto venueDto, String lang) {
        if (venueDto == null || venueDto.getTranslations() == null) {
            return;
        }
        
        com.portfolio.bloom.common.Translation translation = new com.portfolio.bloom.common.Translation();
        translation.setTranslations(venueDto.getTranslations());

        if (LangList.isValid(translation, "venueName", lang)) {
            venueDto.setVenueName(venueDto.getTranslations().get("venueName").get(lang));
        }

        if (LangList.isValid(translation, "description", lang)) {
            venueDto.setDescription(venueDto.getTranslations().get("description").get(lang));
        }

        // Translation support can be added for offers if needed
    }
}
