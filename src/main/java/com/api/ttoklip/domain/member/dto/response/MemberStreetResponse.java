package com.api.ttoklip.domain.member.dto.response;

public record MemberStreetResponse(
        String street
) {
    public static MemberStreetResponse of(String street) {
        String filterStreet = street.replace("경기도", "").trim();

        return new MemberStreetResponse(filterStreet);
    }
}
