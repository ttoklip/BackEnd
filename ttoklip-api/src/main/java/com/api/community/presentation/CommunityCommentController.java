package com.api.community.presentation;

import com.api.common.ReportWebCreate;
import com.api.community.application.CommunityCommentFacade;
import com.api.global.support.response.Message;
import com.api.global.support.response.TtoklipResponse;
import com.api.global.util.SecurityUtil;
import com.domain.comment.domain.CommentCreate;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/town/comms/comment")
public class CommunityCommentController implements CommunityCommentControllerDocs {

    private final CommunityCommentFacade communityCommentFacade;

    @Override
    @PostMapping("/{postId}")
    public TtoklipResponse<Message> register(
            final @PathVariable Long postId,
            final @RequestBody CommentCreate request
    ) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return TtoklipResponse.created(
                communityCommentFacade.register(postId, request, currentMemberId)
        );
    }

    @Override
    @PostMapping("/report/{commentId}")
    public TtoklipResponse<Message> report(
            final @PathVariable Long commentId,
            final @RequestBody ReportWebCreate request
    ) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return TtoklipResponse.created(
                communityCommentFacade.report(commentId, request, currentMemberId)
        );
    }

    @Override
    @DeleteMapping("/{commentId}")
    public TtoklipResponse<Message> delete(@PathVariable Long commentId) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return TtoklipResponse.ok(
                communityCommentFacade.delete(commentId, currentMemberId)
        );
    }
}
