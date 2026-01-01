package com.portfolio.bloom.domain.repository;

import com.portfolio.bloom.domain.model.offer.Offer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * Offer repository interface for MongoDB operations.
 */
public interface OfferRepository extends MongoRepository<Offer, String> {
    
    @Query("{ '_id': ?0, 'deleted': false }")
    Optional<Offer> findByIdAndDeletedIsFalse(String id);
    
    @Query("{ 'deleted': false }")
    Page<Offer> findAllByDeletedIsFalse(Pageable pageable);
    
    @Query("{ 'venueId': ?0, 'deleted': false }")
    List<Offer> findByVenueIdAndDeletedIsFalse(String venueId);
    
    @Query("{ 'venueId': ?0, 'deleted': false }")
    List<Offer> findByVenueIdAndDeletedIsFalseOrderByActiveDesc(String venueId);
}
