package com.api.profile.presentation;

import com.common.base.Filterable;
import io.swagger.v3.oas.annotations.media.Schema;

public record Nickname(String value) implements Filterable {

    @Override
    @Schema(hidden = true)
    public String getFilterContent() {
        return value;
    }
}
