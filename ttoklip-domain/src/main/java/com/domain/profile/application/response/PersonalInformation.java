package com.domain.profile.application.response;

import com.domain.member.domain.Member;

public record PersonalInformation(
        Member member, int independentYear, int independentMonth,
        String nickname, String street
) {
    public static PersonalInformation from(Member member, int independentYear, int independentMonth, String nickname,
                                           String street) {
        return new PersonalInformation(member, independentYear, independentMonth, nickname, street);
    }
}
