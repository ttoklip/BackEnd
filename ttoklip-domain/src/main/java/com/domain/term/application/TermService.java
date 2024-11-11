package com.domain.term.application;

import com.common.exception.ApiException;
import com.common.exception.ErrorType;
import com.domain.term.domain.Term;
import com.domain.term.domain.TermCreate;
import com.domain.term.domain.TermEditor;
import com.domain.term.domain.TermRepository;
import com.domain.term.response.TermResponse;
import com.domain.term.response.TermResponses;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TermService {

    private final TermRepository termRepository;

    public Term findTermById(final Long termId) {
        return termRepository.findById(termId)
                .orElseThrow(() -> new ApiException(ErrorType.TERM_NOT_FOUND));
    }

    @Transactional
    public void save(final Term term) {
        termRepository.saveAndFlush(term);
    }

    @Transactional
    public void edit(final Term term, final TermCreate request) {
        TermEditor termEditor = term.toEditor()
                .title(request.title())
                .content(request.content())
                .build();
        term.edit(termEditor);
    }

    public Term getAgreeTermsOfService() {
        return termRepository.getAgreeTermsOfService();
    }

    public Term getAgreePrivacyPolicy() {
        return termRepository.getAgreePrivacyPolicy();
    }

    public Term getAgreeLocationService() {
        return termRepository.getAgreeLocationService();
    }

    public TermResponses getAllTerms() {
        List<Term> terms = termRepository.getAllTerms();
        List<TermResponse> termResponses = terms.stream()
                .map(TermResponse::from)
                .toList();
        return TermResponses.from(termResponses);
    }
}
