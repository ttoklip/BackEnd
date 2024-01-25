package com.api.ttoklip.domain.FindidAndFindpw.main.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class FindIdRequest {
    private String userEmail;
}
