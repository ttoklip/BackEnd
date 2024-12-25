package com.domain.bulletin.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record NoticeEdit(
        @NotBlank(message = "제목은 필수입니다.")
        @Size(max = 500)
        String title,

        @NotBlank(message = "내용은 필수입니다.")
        @Size(max = 5000)
        String content
) {
}