package com.portfolio.bloom.error;

/**
 * Error codes and messages enumeration for standardized error handling.
 */
public enum ErrorEnum {
    // User errors
    EMAIL_ALREADY_USED_BY_ANOTHER_USER(400001, "error.user.email.already-used", "Email already used by another user"),
    USER_NOT_FOUND(400002, "error.user.not-found", "User not found"),
    USER_ROLE_NOT_ALLOWED(400003, "error.user-role-not-allowed", "User role not allowed"),
    
    // Offer errors
    OFFER_NOT_FOUND(400010, "error.offer.not-found", "Offer %snot found"),
    OFFER_NOT_OWNER(400011, "error.offer.not-owner", "Offer not owned by the current user"),
    
    // Venue errors
    VENUE_NOT_FOUND(400020, "error.venue.not-found", "Venue %snot found"),
    VENUE_NAME_ALREADY_EXISTS(400021, "error.venue-name-already-exists", "Venue name %salready exists"),
    VENUE_NOT_OWNER(400022, "error.venue.not-owner", "Venue not owned by the current user"),
    LOW_PRICE_GREATER_THAN_HIGH_PRICE(400023, "error.low-price-greater-than-high-price", "Low price cannot be greater than high price"),
    
    // General errors
    INVALID_REQUEST(400100, "error.invalid-request", "Invalid request"),
    ENTITY_NOT_FOUND(400101, "error.entity.not-found", "Entity %snot found");

    private final int code;
    private final String messageId;
    private final String title;

    ErrorEnum(int code, String messageId, String title) {
        this.code = code;
        this.messageId = messageId;
        this.title = title;
    }

    public int getCode() {
        return code;
    }

    public String getMessageId() {
        return messageId;
    }

    public String getTitle() {
        return title;
    }
}
