package com.api.term.presentation;

import com.api.global.support.response.Message;
import com.api.global.support.response.TtoklipResponse;
import com.api.global.util.SecurityUtil;
import com.api.term.application.TermFacade;
import com.domain.term.domain.TermCreate;
import com.domain.term.response.TermResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/admin/term")
public class TermController implements TermControllerDocs {

    private final TermFacade termFacade;

    @Override
    @GetMapping
    public TtoklipResponse<TermResponses> getTermList() {
        TermResponses termResponses = termFacade.getTermList();
        return new TtoklipResponse<>(termResponses);
    }

    @Override
    @GetMapping("/{termId}")
    public TtoklipResponse<TermAdminResponse> getSingleTerm(@PathVariable Long termId) {
        return new TtoklipResponse<>(termFacade.getSingleTerm(termId));
    }

    @Override
    @PatchMapping("/{termId}")
    public TtoklipResponse<Message> edit(@PathVariable Long termId,
                                         @RequestBody TermCreate request) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = termFacade.edit(termId, request, currentMemberId);
        return new TtoklipResponse<>(message);
    }

    @Override
    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public TtoklipResponse<Message> register(@Validated @RequestBody TermCreate request) {
        Message message = termFacade.register(request);
        return new TtoklipResponse<>(message);
    }
}
