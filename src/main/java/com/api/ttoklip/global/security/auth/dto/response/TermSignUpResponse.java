package com.api.ttoklip.global.security.auth.dto.response;

public record TermSignUpResponse(TermClientResponse agreeTermsOfService,
                                 TermClientResponse agreeLocationService,
                                 TermClientResponse agreePrivacyPolicy) {

    public static TermSignUpResponse of(
            TermClientResponse agreeTermsOfService,
            TermClientResponse agreeLocationService,
            TermClientResponse agreePrivacyPolicy
    ) {
        return new TermSignUpResponse(agreeTermsOfService, agreeLocationService, agreePrivacyPolicy);
    }
}
