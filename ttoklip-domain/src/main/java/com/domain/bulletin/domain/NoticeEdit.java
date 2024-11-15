package com.domain.bulletin.domain;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record NoticeEdit(
        @NotEmpty
        @Size(max = 500)
        String title,

        @NotEmpty
        @Size(max = 5000)
        String content
) {
}