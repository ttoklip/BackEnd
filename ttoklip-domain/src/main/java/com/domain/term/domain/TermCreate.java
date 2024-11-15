package com.domain.term.domain;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record TermCreate(@NotEmpty @Size(max = 500) String title, @NotEmpty String content) {
}
