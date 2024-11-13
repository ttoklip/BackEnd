package com.domain.member.domain;

import com.domain.common.vo.Category;
import java.util.List;

public record LocalMemberCreate(
        String email, String password,
        String originName, String nickname,
        int independentYear, int independentMonth,
        String street, boolean agreeTermsOfService,
        boolean agreePrivacyPolicy, boolean agreeLocationService,
        List<Category> categories
) {
    public static LocalMemberCreate of(String email, String password,
                                       String originName, String nickname,
                                       int independentYear, int independentMonth,
                                       String street, boolean agreeTermsOfService,
                                       boolean agreePrivacyPolicy, boolean agreeLocationService,
                                       List<Category> categories) {
        return new LocalMemberCreate(email, password, originName, nickname, independentYear, independentMonth, street,
                agreeTermsOfService, agreePrivacyPolicy, agreeLocationService, categories);
    }
}
