package com.api.ttoklip.domain.mypage.dto.response;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
public class TermsResponse {
    private String termsType; // 이용약관 종류
    private String termsContent; // 이용약관 내용
}
