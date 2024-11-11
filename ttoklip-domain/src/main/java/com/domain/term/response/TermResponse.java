package com.domain.term.response;

import com.domain.term.domain.Term;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record TermResponse(Long termId, String title, String content) {

    public static TermResponse from(final Term term) {
        return TermResponse.builder()
                .termId(term.getId())
                .title(term.getTitle())
                .content(term.getContent())
                .build();
    }

}
