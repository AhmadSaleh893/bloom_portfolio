package com.portfolio.bloom.domain.service;

import com.portfolio.bloom.domain.dto.OfferDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Offer service interface.
 */
public interface OfferService {
    
    Optional<OfferDto> getOfferById(String id);
    
    Page<OfferDto> getAllOffers(Pageable pageable);
    
    List<OfferDto> getOffersByVenueId(String venueId);
    
    Optional<OfferDto> createOffer(OfferDto dto, String userId);
    
    Optional<OfferDto> updateOffer(String id, OfferDto dto);
    
    boolean deleteOffer(String id);
}
