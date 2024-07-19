package com.api.ttoklip.domain.term.dto.response;

import com.api.ttoklip.domain.term.domain.Term;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TermSingleResponse {
    private Long termId;
    private String title;
    private String content;

    public static TermSingleResponse termFrom(final Term term) {
        return TermSingleResponse.builder()
                .termId(term.getId())
                .title(term.getTitle())
                .content(term.getContent())
                .build();
    }
}
