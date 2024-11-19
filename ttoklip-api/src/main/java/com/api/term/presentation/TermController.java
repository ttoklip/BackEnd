package com.api.term.presentation;

import com.api.global.support.response.Message;
import com.api.global.support.response.TtoklipResponse;
import com.api.global.util.SecurityUtil;
import com.api.term.application.TermFacade;
import com.domain.term.domain.TermCreate;
import com.domain.term.response.TermResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TermController implements TermControllerDocs {

    private final TermFacade termFacade;

    @Override
    public TtoklipResponse<TermResponses> getTermList() {
        TermResponses termResponses = termFacade.getTermList();
        return new TtoklipResponse<>(termResponses);
    }

    @Override
    public TtoklipResponse<TermAdminResponse> getSingleTerm(Long termId) {
        return new TtoklipResponse<>(termFacade.getSingleTerm(termId));
    }

    @Override
    public TtoklipResponse<Message> edit(Long termId, TermCreate request) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = termFacade.edit(termId, request, currentMemberId);
        return new TtoklipResponse<>(message);
    }

    @Override
    public TtoklipResponse<Message> register(TermCreate request) {
        Message message = termFacade.register(request);
        return new TtoklipResponse<>(message);
    }
}
