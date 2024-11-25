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
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/honeytips/posts")
public class HoneyTipPostController implements HoneyTipPostControllerDocs {

    private final HoneyTipPostFacade honeytipPostFacade;
    private final HoneyTipLikeFacade honeyTipLikeFacade;
    private final HoneyTipScrapFacade honeyTipScrapFacade;

    @Override
    @PostMapping
    public TtoklipResponse<Message> register(@Validated @ModelAttribute HoneyTipWebCreate request) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = honeytipPostFacade.register(request, currentMemberId);
        return new TtoklipResponse<>(message);
    }

    @Override
    @PatchMapping("/{postId}")
    public TtoklipResponse<Message> edit(@PathVariable Long postId,
                                         @ModelAttribute HoneyTipWebEdit request) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = honeytipPostFacade.edit(postId, request, currentMemberId);
        return new TtoklipResponse<>(message);
    }

    @Override
    @DeleteMapping("/{postId}")
    public TtoklipResponse<Message> delete(@PathVariable Long postId) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = honeytipPostFacade.delete(postId, currentMemberId);
        return new TtoklipResponse<>(message);
    }

    @Override
    @PostMapping("/report/{postId}")
    public TtoklipResponse<Message> report(@PathVariable Long postId,
                                           @RequestBody ReportWebCreate request) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = honeytipPostFacade.report(postId, request, currentMemberId);
        return new TtoklipResponse<>(message);
    }

    @Override
    @PostMapping("/like/{postId}")
    public TtoklipResponse<Message> registerLike(@PathVariable Long postId) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = honeyTipLikeFacade.register(postId, currentMemberId);
        return new TtoklipResponse<>(message);
    }

    @Override
    @DeleteMapping("/like/{postId}")
    public TtoklipResponse<Message> cancelLike(@PathVariable Long postId) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = honeyTipLikeFacade.cancel(postId, currentMemberId);
        return new TtoklipResponse<>(message);
    }

    @Override
    @PostMapping("/scrap/{postId}")
    public TtoklipResponse<Message> registerScrap(@PathVariable Long postId) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = honeyTipScrapFacade.register(postId, currentMemberId);
        return new TtoklipResponse<>(message);
    }

    @Override
    @DeleteMapping("/scrap/{postId}")
    public TtoklipResponse<Message> cancelScrap(@PathVariable Long postId) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = honeyTipScrapFacade.cancel(postId, currentMemberId);
        return new TtoklipResponse<>(message);
    }

    @Override
    @GetMapping("/{postId}")
    public TtoklipResponse<HoneyTipSingleResponse> getSinglePost(@PathVariable Long postId) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        HoneyTipSingleResponse response = honeytipPostFacade.getSinglePost(postId, currentMemberId);
        return new TtoklipResponse<>(response);
    }
}
