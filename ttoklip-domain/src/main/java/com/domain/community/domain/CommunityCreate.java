package com.domain.community.domain;

import com.domain.member.domain.Member;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record CommunityCreate(
        @NotEmpty
        @Size(max = 500)
        String title,

        @NotEmpty
        @Size(max = 5000)
        String content,

        Member member
) {
    public static CommunityCreate of(String title, String content, Member member) {
        return new CommunityCreate(title, content, member);
    }
}
