package com.domain.community.domain;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record CommunityEdit(
        @NotEmpty
        @Size(max = 500)
        String title,

        @NotEmpty
        @Size(max = 5000)
        String content
) {
    public static CommunityEdit of(String title, String content) {
        return new CommunityEdit(title, content);
    }
}
