package com.api.ttoklip.domain.manager.inquiry.dto.response;

import com.api.ttoklip.domain.manager.inquiry.domain.Faq;
import com.api.ttoklip.global.util.TimeUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class FaqResponse {
    @Schema(description = "문의하기 ID")
    private Long faqId;

    @Schema(description = "문의하기 내용")
    private String content;

    public static FaqResponse of(final Faq faq){

        String formattedCreatedDate = getFormattedCreatedDate(faq);

        return FaqResponse.builder()
                .faqId(faq.getId())
                .content(faq.getContent())
                .build();
    }

    private static String getFormattedCreatedDate(final Faq faq) {
        LocalDateTime createdDate = faq.getCreatedDate();
        return TimeUtil.formatCreatedDate(createdDate);
    }
}
