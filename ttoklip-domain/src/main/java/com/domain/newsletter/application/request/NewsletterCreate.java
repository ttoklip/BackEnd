package com.domain.newsletter.application.request;

import com.domain.common.vo.Category;
import com.domain.member.domain.Member;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class NewsletterCreate {

    @NotEmpty
    @Size(max = 500)
    private String title;

    @NotEmpty
    @Size(max = 5000)
    private String content;

    @NotNull
    private Category category;

    private String mainImageUrl;

    private Member member;

}
