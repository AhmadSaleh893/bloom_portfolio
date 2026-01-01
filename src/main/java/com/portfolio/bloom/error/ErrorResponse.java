package com.portfolio.bloom.error;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Standard error response structure for API error handling.
 */
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@JsonIgnoreProperties({"message", "cause", "stackTrace", "suppressed", "localizedMessage"})
public class ErrorResponse extends RuntimeException {
    
    public ErrorResponse(int code, String title, String messageId) {
        super(messageId);
        this.code = code;
        this.title = title;
        this.messageId = messageId;
    }
    
    public ErrorResponse(String field, String defaultMessage) {
        super(defaultMessage);
        this.title = field;
        this.messageId = defaultMessage;
    }

    @Builder.Default
    private int code = 400;
    private String title;
    private String messageId;
}
