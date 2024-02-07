package com.api.ttoklip.domain.manager.inquiry.dto.response;

import com.api.ttoklip.domain.manager.inquiry.domain.Inquiry;
import com.api.ttoklip.global.util.TimeUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
@Getter
@Builder
@AllArgsConstructor
public class InquiryResponse {
    @Schema(description = "문의하기 ID")
    private Long inquiryId;

    @Schema(description = "문의하기 내용")
    private String content;

    @Schema(description = "문의하기 작성일자")
    private String createdAt;

    @Schema(description = "문의하기 작성자")
    private String createdBy;

    public static InquiryResponse of(final Inquiry inquiry){
        if(inquiry==null){
            throw new NullPointerException("문의하기 객체가 비었습니다");
        }

        String formattedCreatedDate = getFormattedCreatedDate(inquiry);

        return InquiryResponse.builder()
                .inquiryId(inquiry.getId())
                .content(inquiry.getContent())
                .createdAt(formattedCreatedDate)
                //.createdBy() 로그인 구현시 구현
                .build();
    }

    private static String getFormattedCreatedDate(final Inquiry inquiry) {
        LocalDateTime createdDate = inquiry.getCreatedDate();
        return TimeUtil.formatCreatedDate(createdDate);
    }
}
