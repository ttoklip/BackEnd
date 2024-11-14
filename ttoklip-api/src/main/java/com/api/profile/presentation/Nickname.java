package com.api.profile.presentation;

import com.common.Filterable;

public record Nickname(String value) implements Filterable {
    @Override
    public String getFilterContent() {
        return value;
    }
}
