package com.portfolio.bloom.domain.service;

import com.portfolio.bloom.domain.dto.VenueDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Venue service interface.
 */
public interface VenueService {
    
    Optional<VenueDto> getVenueById(String id, String lang);
    
    Page<VenueDto> getAllVenues(Pageable pageable, String lang);
    
    Page<VenueDto> getVenuesByUserId(String userId, Pageable pageable, String lang);
    
    Optional<VenueDto> createVenue(VenueDto dto, String userId);
    
    Optional<VenueDto> updateVenue(String id, VenueDto dto);
    
    boolean deleteVenue(String id);
}
