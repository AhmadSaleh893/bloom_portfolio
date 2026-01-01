package com.portfolio.bloom.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.portfolio.bloom.common.BaseEntity;
import com.portfolio.bloom.domain.model.user.Address;
import com.portfolio.bloom.domain.model.user.Phone;
import com.portfolio.bloom.domain.model.user.Role;
import com.portfolio.bloom.domain.model.user.User;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

/**
 * Data Transfer Object for User responses.
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserResponseDto extends BaseEntity<String> {

    private String firstname;
    private String lastname;
    private String email;
    private Phone phone;
    private Role role;
    private Address address;
    private Boolean emailVerified;

    public static UserResponseDto fromUser(User user) {
        if (user == null) {
            return null;
        }

        return UserResponseDto.builder()
                .id(user.getId())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .email(user.getEmail())
                .phone(user.getPhone())
                .role(user.getRole())
                .address(user.getAddress())
                .emailVerified(user.isEmailVerified())
                .created(user.getCreated())
                .updated(user.getUpdated())
                .deleted(user.isDeleted())
                .build();
    }

    @Override
    @JsonIgnore
    public long getCreated() {
        return super.getCreated();
    }

    @Override
    @JsonIgnore
    public long getUpdated() {
        return super.getUpdated();
    }

    @JsonProperty("created")
    public Instant getCreatedInstant() {
        long createdMillis = super.getCreated();
        return createdMillis > 0 ? Instant.ofEpochMilli(createdMillis) : null;
    }

    @JsonProperty("updated")
    public Instant getUpdatedInstant() {
        long updatedMillis = super.getUpdated();
        return updatedMillis > 0 ? Instant.ofEpochMilli(updatedMillis) : null;
    }

    @Override
    @JsonIgnore
    public java.util.Map<String, java.util.Map<String, String>> getTranslations() {
        return super.getTranslations();
    }
}
