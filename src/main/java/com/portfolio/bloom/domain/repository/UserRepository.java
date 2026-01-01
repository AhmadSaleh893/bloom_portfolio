package com.portfolio.bloom.domain.repository;

import com.portfolio.bloom.domain.model.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

/**
 * User repository interface for MongoDB operations.
 */
public interface UserRepository extends MongoRepository<User, String> {
    
    @Query("{ 'email': ?0, 'deleted': false }")
    Optional<User> findByEmailAndDeletedIsFalse(String email);

    @Query("{ $or: [ { 'email': ?0 }, { 'phone.phoneNumber': ?0 } ], 'deleted': false }")
    Optional<User> findByEmailOrPhoneNumberAndDeletedIsFalse(String identifier);
    
    boolean existsByEmailAndDeletedIsFalse(String email);
    
    @Query(value = "{ 'tokens.token': ?0, 'deleted': false }", 
           fields = "{ '_id': 1, 'tokens': 1 }")
    Optional<User> findTokenFieldsByTokenValueAndDeletedIsFalse(String tokenValue);
}
