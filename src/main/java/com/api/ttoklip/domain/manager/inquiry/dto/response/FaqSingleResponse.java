package com.api.ttoklip.domain.manager.inquiry.dto.response;

import com.api.ttoklip.domain.manager.inquiry.domain.Faq;
import com.api.ttoklip.domain.manager.inquiry.domain.Inquiry;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FaqSingleResponse {
    private Long faqId;
    private String title;
    private String content;

    public static FaqSingleResponse faqFrom(final Faq faq){
        return FaqSingleResponse.builder()
                .faqId(faq.getId())
                .title(faq.getTitle())
                .content(faq.getContent())
                .build();
    }
}
