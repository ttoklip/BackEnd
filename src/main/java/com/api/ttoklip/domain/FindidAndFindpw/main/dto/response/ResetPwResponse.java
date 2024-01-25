package com.api.ttoklip.domain.FindidAndFindpw.main.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@AllArgsConstructor
@Jacksonized
public class ResetPwResponse {
    private String message;
}
