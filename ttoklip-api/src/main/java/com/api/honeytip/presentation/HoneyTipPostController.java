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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HoneyTipPostController implements HoneyTipPostControllerDocs {

    private final HoneyTipPostFacade honeytipPostFacade;
    private final HoneyTipLikeFacade honeyTipLikeFacade;
    private final HoneyTipScrapFacade honeyTipScrapFacade;

    @Override
    public TtoklipResponse<Message> register(HoneyTipWebCreate request) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = honeytipPostFacade.register(request, currentMemberId);
        return new TtoklipResponse<>(message);
    }

    @Override
    public TtoklipResponse<Message> edit(Long postId, HoneyTipWebEdit request) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = honeytipPostFacade.edit(postId, request, currentMemberId);
        return new TtoklipResponse<>(message);
    }

    @Override
    public TtoklipResponse<Message> delete(Long postId) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = honeytipPostFacade.delete(postId, currentMemberId);
        return new TtoklipResponse<>(message);
    }

    @Override
    public TtoklipResponse<Message> report(Long postId, ReportWebCreate request) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = honeytipPostFacade.report(postId, request, currentMemberId);
        return new TtoklipResponse<>(message);
    }

    @Override
    public TtoklipResponse<Message> registerLike(Long postId) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = honeyTipLikeFacade.register(postId, currentMemberId);
        return new TtoklipResponse<>(message);
    }

    @Override
    public TtoklipResponse<Message> cancelLike(Long postId) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = honeyTipLikeFacade.cancel(postId, currentMemberId);
        return new TtoklipResponse<>(message);
    }

    @Override
    public TtoklipResponse<Message> registerScrap(Long postId) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = honeyTipScrapFacade.register(postId, currentMemberId);
        return new TtoklipResponse<>(message);
    }

    @Override
    public TtoklipResponse<Message> cancelScrap(Long postId) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = honeyTipScrapFacade.cancel(postId, currentMemberId);
        return new TtoklipResponse<>(message);
    }

    @Override
    public TtoklipResponse<HoneyTipSingleResponse> getSinglePost(Long postId) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        HoneyTipSingleResponse response = honeytipPostFacade.getSinglePost(postId, currentMemberId);
        return new TtoklipResponse<>(response);
    }
}
