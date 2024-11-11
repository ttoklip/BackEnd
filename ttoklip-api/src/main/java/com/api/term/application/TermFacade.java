package com.api.term.application;

import com.api.global.success.Message;
import com.common.exception.ApiException;
import com.common.exception.ErrorType;
import com.domain.member.application.MemberService;
import com.domain.member.domain.Member;
import com.domain.member.domain.vo.Role;
import com.domain.term.application.TermAgreementService;
import com.domain.term.application.TermService;
import com.domain.term.domain.Term;
import com.domain.term.domain.TermCreate;
import com.api.term.presentation.TermAdminResponse;
import com.domain.term.response.TermResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TermFacade {
    private final TermService termService;
    private final TermAgreementService termAgreementService;
    private final MemberService memberService;

    public Term getById(final Long termId) {
        return termService.findTermById(termId);
    }

    /* -------------------------------------------- CREATE -------------------------------------------- */
    @Transactional
    public Message register(final TermCreate create) {
        Term term = Term.from(create);
        termService.save(term);
        Long termId = term.getId();

        return Message.registerPostSuccess(Term.class, termId);
    }

    /* -------------------------------------------- CREATE 끝 -------------------------------------------- */

    public TermAdminResponse getSingleTerm(final Long termId) {
        Term term = getById(termId);
        return TermAdminResponse.of(term);
    }

    public TermResponses getTermList() {
        return termService.getAllTerms();
    }

    @Transactional
    public Message edit(final Long termId, final TermCreate request, final Long memberId) {
        Term term = getById(termId);

        Member member = memberService.getById(memberId);
        if (!member.getRole().equals(Role.MANAGER)) {
            throw new ApiException(ErrorType.UNAUTHORIZED_ADMIN_EDIT_POST);
        }

        termService.edit(term, request);
        return Message.editPostSuccess(Term.class, term.getId());
    }

    public TermSignUpResponse getTermWhenSignUp() {
        Term termsOfService = termService.getAgreeTermsOfService();
        Term locationService = termService.getAgreeLocationService();
        Term privacyPolicy = termService.getAgreePrivacyPolicy();

        TermClientResponse termsOfServiceResponse = transformTerm(termsOfService);
        TermClientResponse termLocationService = transformTerm(locationService);
        TermClientResponse termPrivacyPolicy = transformTerm(privacyPolicy);

        return TermSignUpResponse.of(termsOfServiceResponse, termLocationService, termPrivacyPolicy);
    }

    private TermClientResponse transformTerm(final Term term) {
        return TermClientResponse.from(term);
    }

}
