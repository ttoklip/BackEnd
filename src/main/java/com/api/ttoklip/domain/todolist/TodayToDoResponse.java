package com.api.ttoklip.domain.todolist;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
@AllArgsConstructor
public class TodayToDoResponse {
    private String content;
}
