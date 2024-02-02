package com.api.ttoklip.domain.mypage.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TermsResponse {
    private String termsType; // 이용약관 종류
    private String termsContent; // 이용약관 내용
}
