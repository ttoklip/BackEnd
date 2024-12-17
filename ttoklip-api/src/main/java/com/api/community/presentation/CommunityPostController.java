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
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/town/comms")
public class CommunityPostController implements CommunityPostControllerDocs {

    private final CommunityPostFacade communityPostFacade;

    @Override
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public TtoklipResponse<Message> register(@Validated @ModelAttribute CommunityWebCreate request) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = communityPostFacade.register(request, currentMemberId);
        return new TtoklipResponse<>(message);
    }

    @Override
    @GetMapping("/{postId}")
    public TtoklipResponse<CommunityResponse> getSinglePost(@PathVariable Long postId) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        CommunityResponse response = communityPostFacade.getSinglePost(postId, currentMemberId);
        return new TtoklipResponse<>(response);
    }

    @Override
    @PatchMapping("/{postId}")
    public TtoklipResponse<Message> edit(@PathVariable Long postId,
                                         @Validated @ModelAttribute CommunityWebEdit request) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = communityPostFacade.edit(postId, request, currentMemberId);
        return new TtoklipResponse<>(message);
    }

    @Override
    @DeleteMapping("/{postId}")
    public TtoklipResponse<Message> delete(@PathVariable Long postId) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return new TtoklipResponse<>(communityPostFacade.delete(postId, currentMemberId));
    }

    @Override
    @PostMapping("/report/{postId}")
    public TtoklipResponse<Message> report(@PathVariable Long postId,
                                           @RequestBody ReportWebCreate request) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = communityPostFacade.report(postId, request, currentMemberId);
        return new TtoklipResponse<>(message);
    }

    @Override
    @PostMapping("/like/{postId}")
    public TtoklipResponse<Message> registerLike(@PathVariable Long postId) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = communityPostFacade.registerLike(postId, currentMemberId);
        return new TtoklipResponse<>(message);
    }

    @Override
    @DeleteMapping("/like/{postId}")
    public TtoklipResponse<Message> cancelLike(@PathVariable Long postId) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = communityPostFacade.cancelLike(postId, currentMemberId);
        return new TtoklipResponse<>(message);
    }

    @Override
    @PostMapping("/scrap/{postId}")
    public TtoklipResponse<Message> registerScrap(@PathVariable Long postId) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = communityPostFacade.registerScrap(postId, currentMemberId);
        return new TtoklipResponse<>(message);
    }

    @Override
    @DeleteMapping("/scrap/{postId}")
    public TtoklipResponse<Message> cancelScrap(@PathVariable Long postId) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = communityPostFacade.cancelScrap(postId, currentMemberId);
        return new TtoklipResponse<>(message);
    }
}
