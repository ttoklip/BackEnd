package com.domain.term.response;

public record TermSignUpResponse(TermResponse agreeTermsOfService,
                                 TermResponse agreeLocationService,
                                 TermResponse agreePrivacyPolicy) {

    public static TermSignUpResponse of(
            TermResponse agreeTermsOfService,
            TermResponse agreeLocationService,
            TermResponse agreePrivacyPolicy
    ) {
        return new TermSignUpResponse(agreeTermsOfService, agreeLocationService, agreePrivacyPolicy);
    }
}