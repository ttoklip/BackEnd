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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/term")
public class TermController implements TermControllerDocs {

    private final TermFacade termFacade;

    @Override
    @GetMapping
    public TtoklipResponse<TermResponses> getTermList() {
        return TtoklipResponse.ok(
                termFacade.getTermList()
        );
    }

    @Override
    @GetMapping("/{termId}")
    public TtoklipResponse<TermAdminResponse> getSingleTerm(
            final @PathVariable Long termId
    ) {
        return TtoklipResponse.ok(
                termFacade.getSingleTerm(termId)
        );
    }

    @Override
    @PatchMapping("/{termId}")
    public TtoklipResponse<Message> edit(
            final @PathVariable Long termId,
            final @RequestBody TermCreate request
    ) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return TtoklipResponse.ok(
                termFacade.edit(termId, request, currentMemberId)
        );
    }

    @Override
    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public TtoklipResponse<Message> register(
            final @Validated @RequestBody TermCreate request
    ) {
        return TtoklipResponse.created(
                termFacade.register(request)
        );
    }
}
