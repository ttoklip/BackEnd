package com.api.ttoklip.global.security.auth.dto.response;

import com.api.ttoklip.domain.term.domain.Term;
public record TermClientResponse(
        Long termId,
        String termTitle,
        String termContent
) {

    public static TermClientResponse from(Term term) {
        return new TermClientResponse(term.getId(), term.getTitle(), term.getContent());
    }
}
