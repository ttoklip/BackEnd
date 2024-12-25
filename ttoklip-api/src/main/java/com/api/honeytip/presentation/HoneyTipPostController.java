package com.api.honeytip.presentation;

import com.api.common.ReportWebCreate;
import com.api.global.support.response.Message;
import com.api.global.support.response.TtoklipResponse;
import com.api.global.util.SecurityUtil;
import com.api.honeytip.application.HoneyTipLikeFacade;
import com.api.honeytip.application.HoneyTipPostFacade;
import com.api.honeytip.application.HoneyTipScrapFacade;
import com.api.honeytip.presentation.request.HoneyTipWebCreate;
import com.api.honeytip.presentation.request.HoneyTipWebEdit;
import com.api.honeytip.presentation.response.HoneyTipSingleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/honeytips/posts")
public class HoneyTipPostController implements HoneyTipPostControllerDocs {

    private final HoneyTipPostFacade honeytipPostFacade;
    private final HoneyTipLikeFacade honeyTipLikeFacade;
    private final HoneyTipScrapFacade honeyTipScrapFacade;

    @Override
    @PostMapping
    public TtoklipResponse<Message> register(
            final @Validated @ModelAttribute HoneyTipWebCreate request
    ) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return TtoklipResponse.created(
                honeytipPostFacade.register(request, currentMemberId)
        );
    }

    @Override
    @PatchMapping("/{postId}")
    public TtoklipResponse<Message> edit(
            final @PathVariable Long postId,
            final @ModelAttribute HoneyTipWebEdit request
    ) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return TtoklipResponse.ok(
                honeytipPostFacade.edit(postId, request, currentMemberId)
        );
    }

    @Override
    @DeleteMapping("/{postId}")
    public TtoklipResponse<Message> delete(
            final @PathVariable Long postId
    ) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return TtoklipResponse.ok(
                honeytipPostFacade.delete(postId, currentMemberId)
        );
    }

    @Override
    @PostMapping("/report/{postId}")
    public TtoklipResponse<Message> report(
            final @PathVariable Long postId,
            final @RequestBody ReportWebCreate request
    ) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return TtoklipResponse.created(
                honeytipPostFacade.report(postId, request, currentMemberId)
        );
    }

    @Override
    @PostMapping("/like/{postId}")
    public TtoklipResponse<Message> registerLike(
            final @PathVariable Long postId
    ) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return TtoklipResponse.created(
                honeyTipLikeFacade.register(postId, currentMemberId)
        );
    }

    @Override
    @DeleteMapping("/like/{postId}")
    public TtoklipResponse<Message> cancelLike(
            final @PathVariable Long postId
    ) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return TtoklipResponse.ok(
                honeyTipLikeFacade.cancel(postId, currentMemberId)
        );
    }

    @Override
    @PostMapping("/scrap/{postId}")
    public TtoklipResponse<Message> registerScrap(
            final @PathVariable Long postId
    ) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return TtoklipResponse.ok(
                honeyTipScrapFacade.register(postId, currentMemberId)
        );
    }

    @Override
    @DeleteMapping("/scrap/{postId}")
    public TtoklipResponse<Message> cancelScrap(
            final @PathVariable Long postId
    ) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return TtoklipResponse.ok(
                honeyTipScrapFacade.cancel(postId, currentMemberId)
        );
    }

    @Override
    @GetMapping("/{postId}")
    public TtoklipResponse<HoneyTipSingleResponse> getSinglePost(
            final @PathVariable Long postId
    ) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return TtoklipResponse.ok(
                honeytipPostFacade.getSinglePost(postId, currentMemberId)
        );
    }
}
