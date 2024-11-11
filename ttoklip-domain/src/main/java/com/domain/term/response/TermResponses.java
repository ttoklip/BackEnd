package com.domain.term.response;

import java.util.List;

public record TermResponses(List<TermResponse> terms) {

    public static TermResponses from(List<TermResponse> termResponses) {
        return new TermResponses(termResponses);
    }
}
