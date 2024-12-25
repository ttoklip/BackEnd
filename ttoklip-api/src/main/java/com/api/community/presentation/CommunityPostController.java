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
@RequestMapping("/api/v1/town/comms")
public class CommunityPostController implements CommunityPostControllerDocs {

    private final CommunityPostFacade communityPostFacade;

    @Override
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public TtoklipResponse<Message> register(
            final @Validated @ModelAttribute CommunityWebCreate request
    ) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return TtoklipResponse.created(
                communityPostFacade.register(request, currentMemberId)
        );
    }

    @Override
    @GetMapping("/{postId}")
    public TtoklipResponse<CommunityResponse> getSinglePost(
            final @PathVariable Long postId
    ) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return TtoklipResponse.ok(
                communityPostFacade.getSinglePost(postId, currentMemberId)
        );
    }

    @Override
    @PatchMapping("/{postId}")
    public TtoklipResponse<Message> edit(
            final @PathVariable Long postId,
            final @Validated @ModelAttribute CommunityWebEdit request
    ) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return TtoklipResponse.ok(
                communityPostFacade.edit(postId, request, currentMemberId)
        );
    }

    @Override
    @DeleteMapping("/{postId}")
    public TtoklipResponse<Message> delete(
            final @PathVariable Long postId
    ) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return TtoklipResponse.ok(
                communityPostFacade.delete(postId, currentMemberId)
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
                communityPostFacade.report(postId, request, currentMemberId)
        );
    }

    @Override
    @PostMapping("/like/{postId}")
    public TtoklipResponse<Message> registerLike(
            final @PathVariable Long postId
    ) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return TtoklipResponse.created(
                communityPostFacade.registerLike(postId, currentMemberId)
        );
    }

    @Override
    @DeleteMapping("/like/{postId}")
    public TtoklipResponse<Message> cancelLike(
            final @PathVariable Long postId
    ) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return TtoklipResponse.ok(
                communityPostFacade.cancelLike(postId, currentMemberId)
        );
    }

    @Override
    @PostMapping("/scrap/{postId}")
    public TtoklipResponse<Message> registerScrap(
            final @PathVariable Long postId
    ) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return TtoklipResponse.created(
                communityPostFacade.registerScrap(postId, currentMemberId)
        );
    }

    @Override
    @DeleteMapping("/scrap/{postId}")
    public TtoklipResponse<Message> cancelScrap(
            final @PathVariable Long postId
    ) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return TtoklipResponse.ok(
                communityPostFacade.cancelScrap(postId, currentMemberId)
        );
    }
}
