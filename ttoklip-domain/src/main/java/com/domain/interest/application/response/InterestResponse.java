package com.domain.interest.application.response;

import com.domain.interest.domain.Interest;

public record InterestResponse(String categoryName) {

    public static InterestResponse from(final Interest interest) {
        return new InterestResponse(interest.getCategory().getName());
    }
}
