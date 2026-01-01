package com.portfolio.bloom.domain.repository;

import com.portfolio.bloom.domain.model.venue.Venue;
import com.portfolio.bloom.domain.model.venue.VenueType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

/**
 * Venue repository interface for MongoDB operations.
 */
public interface VenueRepository extends MongoRepository<Venue, String> {
    
    @Query("{ '_id': ?0, 'deleted': false }")
    Optional<Venue> findByIdAndDeletedIsFalse(String id);
    
    @Query("{ 'deleted': false }")
    Page<Venue> findAllByDeletedIsFalse(Pageable pageable);
    
    @Query("{ 'userId': ?0, 'deleted': false }")
    Page<Venue> findByUserIdAndDeletedIsFalse(String userId, Pageable pageable);
    
    @Query("{ 'venueType': ?0, 'deleted': false }")
    Page<Venue> findByVenueTypeAndDeletedIsFalse(VenueType venueType, Pageable pageable);
    
    boolean existsByVenueNameAndDeletedIsFalse(String venueName);
}
