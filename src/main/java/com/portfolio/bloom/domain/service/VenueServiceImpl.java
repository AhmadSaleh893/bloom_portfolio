package com.portfolio.bloom.domain.service;

import com.portfolio.bloom.common.Constants;
import com.portfolio.bloom.common.translation.LangList;
import com.portfolio.bloom.domain.dto.VenueDto;
import com.portfolio.bloom.domain.model.venue.Venue;
import com.portfolio.bloom.domain.repository.VenueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

/**
 * Venue service implementation.
 */
@Service
@RequiredArgsConstructor
public class VenueServiceImpl implements VenueService {

    private final VenueRepository venueRepository;

    @Override
    public Optional<VenueDto> getVenueById(String id, String lang) {
        String resolvedLang = LangList.resolveLanguage(lang);
        Optional<VenueDto> venueDto = venueRepository.findByIdAndDeletedIsFalse(id)
                .map(VenueDto::fromEntity);

        if (venueDto.isPresent() && !resolvedLang.equals(Constants.DEFAULT_LANGUAGE)) {
            VenueDto.translateVenue(venueDto.get(), resolvedLang);
        }
        
        return venueDto;
    }

    @Override
    public Page<VenueDto> getAllVenues(Pageable pageable, String lang) {
        String resolvedLang = LangList.resolveLanguage(lang);
        Page<VenueDto> venueDtos = venueRepository.findAllByDeletedIsFalse(pageable)
                .map(VenueDto::fromEntity);

        if (!venueDtos.isEmpty() && !resolvedLang.equals(Constants.DEFAULT_LANGUAGE)) {
            venueDtos.forEach(v -> VenueDto.translateVenue(v, resolvedLang));
        }
        
        return venueDtos;
    }

    @Override
    public Page<VenueDto> getVenuesByUserId(String userId, Pageable pageable, String lang) {
        String resolvedLang = LangList.resolveLanguage(lang);
        Page<VenueDto> venueDtos = venueRepository.findByUserIdAndDeletedIsFalse(userId, pageable)
                .map(VenueDto::fromEntity);

        if (!venueDtos.isEmpty() && !resolvedLang.equals(Constants.DEFAULT_LANGUAGE)) {
            venueDtos.forEach(v -> VenueDto.translateVenue(v, resolvedLang));
        }
        
        return venueDtos;
    }

    @Override
    @Transactional
    public Optional<VenueDto> createVenue(VenueDto dto, String userId) {
        // Check if venue name already exists
        if (venueRepository.existsByVenueNameAndDeletedIsFalse(dto.getVenueName())) {
            return Optional.empty();
        }

        Venue venue = VenueDto.toEntity(dto);
        venue.setUserId(userId);
        venue.setDeleted(false);
        venue.setCreated(new Date().getTime());
        venue = venueRepository.save(venue);
        
        return Optional.of(VenueDto.fromEntity(venue));
    }

    @Override
    @Transactional
    public Optional<VenueDto> updateVenue(String id, VenueDto dto) {
        Optional<Venue> venueOpt = venueRepository.findByIdAndDeletedIsFalse(id);
        
        if (venueOpt.isEmpty()) {
            return Optional.empty();
        }

        Venue venue = venueOpt.get();
        
        if (dto.getVenueName() != null) {
            venue.setVenueName(dto.getVenueName());
        }
        if (dto.getAddress() != null) {
            venue.setAddress(dto.getAddress());
        }
        if (dto.getCapacity() != null) {
            venue.setCapacity(dto.getCapacity());
        }
        if (dto.getDescription() != null) {
            venue.setDescription(dto.getDescription());
        }
        if (dto.getHighPrice() != null) {
            venue.setHighPrice(dto.getHighPrice());
        }
        if (dto.getLowPrice() != null) {
            venue.setLowPrice(dto.getLowPrice());
        }
        if (dto.getVenueTypeEnum() != null) {
            venue.setVenueType(dto.getVenueTypeEnum());
        }
        if (dto.getPeopleTypeEnum() != null) {
            venue.setPeopleType(dto.getPeopleTypeEnum());
        }
        if (dto.getRating() != null) {
            venue.setRating(dto.getRating());
        }
        if (dto.getPriceTypeEnum() != null) {
            venue.setPriceType(dto.getPriceTypeEnum());
        }
        if (dto.getBaseCurrency() != null) {
            venue.setBaseCurrency(dto.getBaseCurrency());
        }
        if (dto.getBaseImage() != null) {
            venue.setBaseImage(dto.getBaseImage());
        }
        if (dto.getOffers() != null) {
            venue.setOffers(dto.getOffers().stream()
                    .map(com.portfolio.bloom.domain.dto.OfferDto::toEntity)
                    .collect(java.util.stream.Collectors.toList()));
        }

        venue.setUpdated(new Date().getTime());
        venue = venueRepository.save(venue);
        
        return Optional.of(VenueDto.fromEntity(venue));
    }

    @Override
    @Transactional
    public boolean deleteVenue(String id) {
        Optional<Venue> venueOpt = venueRepository.findByIdAndDeletedIsFalse(id);
        
        if (venueOpt.isEmpty()) {
            return false;
        }

        Venue venue = venueOpt.get();
        venue.setDeleted(true);
        venue.setUpdated(new Date().getTime());
        venueRepository.save(venue);
        
        return true;
    }
}
