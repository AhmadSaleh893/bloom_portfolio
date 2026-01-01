package com.portfolio.bloom.controller;

import com.portfolio.bloom.common.Constants;
import com.portfolio.bloom.common.UserUtil;
import com.portfolio.bloom.domain.dto.VenueDto;
import com.portfolio.bloom.domain.service.VenueService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Venue controller for venue management.
 */
@RestController
@RequestMapping("/api/v1/venues")
@RequiredArgsConstructor
public class VenueController {

    private final VenueService venueService;
    private final UserUtil userUtil;

    @GetMapping
    public ResponseEntity<Page<VenueDto>> getAllVenues(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "venueName") String sortBy,
            @RequestParam(defaultValue = "ASC") String direction,
            @RequestHeader(name = Constants.LANGUAGE_HEADER, defaultValue = Constants.DEFAULT_LANGUAGE) String lang) {

        String effectiveLang = userUtil.getLanguagePreference(lang);
        Sort.Direction sortDirection = Sort.Direction.fromString(direction);
        Pageable pageable = PageRequest.of(page, size, sortDirection, sortBy);
        Page<VenueDto> venuesPage = venueService.getAllVenues(pageable, effectiveLang);

        return ResponseEntity.ok(venuesPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VenueDto> getVenueById(
            @PathVariable String id,
            @RequestHeader(name = Constants.LANGUAGE_HEADER, defaultValue = Constants.DEFAULT_LANGUAGE) String lang) {
        
        String effectiveLang = userUtil.getLanguagePreference(lang);
        Optional<VenueDto> venue = venueService.getVenueById(id, effectiveLang);
        
        return venue.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/my-venues")
    public ResponseEntity<Page<VenueDto>> getMyVenues(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "venueName") String sortBy,
            @RequestParam(defaultValue = "ASC") String direction,
            @RequestHeader(name = Constants.LANGUAGE_HEADER, defaultValue = Constants.DEFAULT_LANGUAGE) String lang) {

        var currentUser = userUtil.getCurrentUser();
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String effectiveLang = userUtil.getLanguagePreference(lang);
        Sort.Direction sortDirection = Sort.Direction.fromString(direction);
        Pageable pageable = PageRequest.of(page, size, sortDirection, sortBy);
        Page<VenueDto> venuesPage = venueService.getVenuesByUserId(currentUser.getId(), pageable, effectiveLang);

        return ResponseEntity.ok(venuesPage);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<VenueDto> createVenue(
            @RequestBody @Valid VenueDto venueDto) {
        
        var currentUser = userUtil.getCurrentUser();
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Optional<VenueDto> createdVenue = venueService.createVenue(venueDto, currentUser.getId());
        
        return createdVenue.map(dto -> ResponseEntity.status(HttpStatus.CREATED).body(dto))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }

    @PreAuthorize("hasRole('ADMIN') or @ownableSecurity.isOwner(#id, 'venue')")
    @PutMapping("/{id}")
    public ResponseEntity<VenueDto> updateVenue(
            @PathVariable String id,
            @RequestBody @Valid VenueDto venueDto) {
        
        Optional<VenueDto> updatedVenue = venueService.updateVenue(id, venueDto);
        
        return updatedVenue.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN') or @ownableSecurity.isOwner(#id, 'venue')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteVenue(@PathVariable String id) {
        boolean deleted = venueService.deleteVenue(id);
        
        if (deleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
