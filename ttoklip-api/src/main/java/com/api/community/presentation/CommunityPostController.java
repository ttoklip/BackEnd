package com.api.community.presentation;

import com.api.common.ReportWebCreate;
import com.api.community.application.CommunityPostFacade;
import com.api.community.presentation.dto.request.CommunityWebCreate;
import com.api.community.presentation.dto.request.CommunityWebEdit;
import com.api.community.presentation.dto.response.CommunityResponse;
import com.api.global.support.response.Message;
import com.api.global.support.response.TtoklipResponse;
import com.api.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class CommunityPostController implements CommunityPostControllerDocs {

    private final CommunityPostFacade communityPostFacade;

    @Override
    public TtoklipResponse<Message> register(CommunityWebCreate request) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = communityPostFacade.register(request, currentMemberId);
        return new TtoklipResponse<>(message);
    }

    @Override
    public TtoklipResponse<CommunityResponse> getSinglePost(Long postId) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        CommunityResponse response = communityPostFacade.getSinglePost(postId, currentMemberId);
        return new TtoklipResponse<>(response);
    }

    @Override
    public TtoklipResponse<Message> edit(Long postId, CommunityWebEdit request) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = communityPostFacade.edit(postId, request, currentMemberId);
        return new TtoklipResponse<>(message);
    }

    @Override
    public TtoklipResponse<Message> delete(Long postId) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return new TtoklipResponse<>(communityPostFacade.delete(postId, currentMemberId));
    }

    @Override
    public TtoklipResponse<Message> report(Long postId, ReportWebCreate request) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = communityPostFacade.report(postId, request, currentMemberId);
        return new TtoklipResponse<>(message);
    }

    @Override
    public TtoklipResponse<Message> registerLike(Long postId) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = communityPostFacade.registerLike(postId, currentMemberId);
        return new TtoklipResponse<>(message);
    }

    @Override
    public TtoklipResponse<Message> cancelLike(Long postId) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = communityPostFacade.cancelLike(postId, currentMemberId);
        return new TtoklipResponse<>(message);
    }

    @Override
    public TtoklipResponse<Message> registerScrap(Long postId) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = communityPostFacade.registerScrap(postId, currentMemberId);
        return new TtoklipResponse<>(message);
    }

    @Override
    public TtoklipResponse<Message> cancelScrap(Long postId) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = communityPostFacade.cancelScrap(postId, currentMemberId);
        return new TtoklipResponse<>(message);
    }
}
