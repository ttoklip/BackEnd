package com.api.ttoklip.domain.privacy.dto;

import com.api.ttoklip.domain.privacy.domain.Interest;
public record InterestResponse(Long id, String categoryName) {

    public static InterestResponse from(final Interest interest) {
        return new InterestResponse(interest.getId(), interest.getCategory().getName());
    }
}
