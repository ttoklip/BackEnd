package com.api.ttoklip.domain.member.dto.response;

public record MemberStreetResponse(
        String street,
        boolean writerLiveInSeoul
) {
    public static MemberStreetResponse of(String street, boolean writerLiveInSeoul) {
        return new MemberStreetResponse(street, writerLiveInSeoul);
    }
}
