package com.api.ttoklip.domain.manager.inquiry.dto.response;

import com.api.ttoklip.domain.manager.inquiry.domain.Inquiry;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class InquirySingleResponse {
    private Long inquiryId;
    private String content;

    public static InquirySingleResponse inquiryFrom(final Inquiry inquiry){
        return InquirySingleResponse.builder()
                .inquiryId(inquiry.getId())
                .content(inquiry.getContent())
                .build();
    }
}
