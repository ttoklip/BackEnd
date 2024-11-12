package com.domain.cart.domain;

import com.domain.member.domain.Member;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CartCreate(
        @NotEmpty
        @Size(max = 500)
        String title,

        @NotEmpty
        @Size(max = 5000)
        String content,

        @NotNull
        Long totalPrice,

        @NotEmpty
        @Size(max = 500)
        String location,

        @NotEmpty
        @Size(max = 500)
        String chatUrl,

        @NotNull
        Long partyMax,

        Member member
) {
        public static CartCreate of(String title, String content, Long totalPrice, String location, String chatUrl,
                                    Long partyMax, Member member) {
                return new CartCreate(title, content, totalPrice, location, chatUrl, partyMax, member);
        }
}