package com.portfolio.bloom.controller;

import com.portfolio.bloom.common.UserUtil;
import com.portfolio.bloom.domain.dto.OfferDto;
import com.portfolio.bloom.domain.service.OfferService;
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

import java.util.List;
import java.util.Optional;

/**
 * Offer controller for offer management.
 */
@RestController
@RequestMapping("/api/v1/offers")
@RequiredArgsConstructor
public class OfferController {

    private final OfferService offerService;
    private final UserUtil userUtil;

    @GetMapping
    public ResponseEntity<Page<OfferDto>> getAllOffers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "startDate") String sortBy,
            @RequestParam(defaultValue = "ASC") String direction) {

        Sort.Direction sortDirection = Sort.Direction.fromString(direction);
        Pageable pageable = PageRequest.of(page, size, sortDirection, sortBy);
        Page<OfferDto> offersPage = offerService.getAllOffers(pageable);

        return ResponseEntity.ok(offersPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OfferDto> getOfferById(@PathVariable String id) {
        Optional<OfferDto> offer = offerService.getOfferById(id);
        
        return offer.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/venue/{venueId}")
    public ResponseEntity<List<OfferDto>> getOffersByVenueId(@PathVariable String venueId) {
        List<OfferDto> offers = offerService.getOffersByVenueId(venueId);
        return ResponseEntity.ok(offers);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<OfferDto> createOffer(@RequestBody @Valid OfferDto offerDto) {
        var currentUser = userUtil.getCurrentUser();
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Optional<OfferDto> createdOffer = offerService.createOffer(offerDto, currentUser.getId());
        
        return createdOffer.map(dto -> ResponseEntity.status(HttpStatus.CREATED).body(dto))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<OfferDto> updateOffer(
            @PathVariable String id,
            @RequestBody @Valid OfferDto offerDto) {
        Optional<OfferDto> updatedOffer = offerService.updateOffer(id, offerDto);
        
        return updatedOffer.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOffer(@PathVariable String id) {
        boolean deleted = offerService.deleteOffer(id);
        
        if (deleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
