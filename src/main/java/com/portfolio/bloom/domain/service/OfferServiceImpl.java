package com.portfolio.bloom.domain.service;

import com.portfolio.bloom.domain.dto.OfferDto;
import com.portfolio.bloom.domain.model.offer.Offer;
import com.portfolio.bloom.domain.repository.OfferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Offer service implementation.
 */
@Service
@RequiredArgsConstructor
public class OfferServiceImpl implements OfferService {

    private final OfferRepository offerRepository;

    @Override
    public Optional<OfferDto> getOfferById(String id) {
        return offerRepository.findByIdAndDeletedIsFalse(id)
                .map(OfferDto::fromEntity);
    }

    @Override
    public Page<OfferDto> getAllOffers(Pageable pageable) {
        return offerRepository.findAllByDeletedIsFalse(pageable)
                .map(OfferDto::fromEntity);
    }

    @Override
    public List<OfferDto> getOffersByVenueId(String venueId) {
        return offerRepository.findByVenueIdAndDeletedIsFalse(venueId).stream()
                .map(OfferDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Optional<OfferDto> createOffer(OfferDto dto, String userId) {
        Offer offer = OfferDto.toEntity(dto);
        offer.setUserId(userId);
        offer.setDeleted(false);
        offer.setCreated(new Date().getTime());
        offer = offerRepository.save(offer);
        
        return Optional.of(OfferDto.fromEntity(offer));
    }

    @Override
    @Transactional
    public Optional<OfferDto> updateOffer(String id, OfferDto dto) {
        Optional<Offer> offerOpt = offerRepository.findByIdAndDeletedIsFalse(id);
        
        if (offerOpt.isEmpty()) {
            return Optional.empty();
        }

        Offer offer = offerOpt.get();
        
        if (dto.getStartDate() != null) {
            offer.setStartDate(dto.getStartDate());
        }
        if (dto.getEndDate() != null) {
            offer.setEndDate(dto.getEndDate());
        }
        if (dto.getDiscountPercentage() != null) {
            offer.setDiscountPercentage(dto.getDiscountPercentage());
        }
        if (dto.getFinalPrice() != null) {
            offer.setFinalPrice(dto.getFinalPrice());
        }
        if (dto.getVenueId() != null) {
            offer.setVenueId(dto.getVenueId());
        }
        if (dto.getActive() != null) {
            offer.setActive(dto.getActive());
        }

        offer.setUpdated(new Date().getTime());
        offer = offerRepository.save(offer);
        
        return Optional.of(OfferDto.fromEntity(offer));
    }

    @Override
    @Transactional
    public boolean deleteOffer(String id) {
        Optional<Offer> offerOpt = offerRepository.findByIdAndDeletedIsFalse(id);
        
        if (offerOpt.isEmpty()) {
            return false;
        }

        Offer offer = offerOpt.get();
        offer.setDeleted(true);
        offer.setUpdated(new Date().getTime());
        offerRepository.save(offer);
        
        return true;
    }
}
