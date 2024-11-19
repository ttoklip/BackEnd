package com.api.community.presentation;

import com.api.common.ReportWebCreate;
import com.api.community.application.CommunityCommentFacade;
import com.api.global.support.response.Message;
import com.api.global.support.response.TtoklipResponse;
import com.api.global.util.SecurityUtil;
import com.domain.comment.domain.CommentCreate;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class CommunityCommentController implements CommunityCommentControllerDocs {

    private final CommunityCommentFacade communityCommentFacade;

    @Override
    public TtoklipResponse<Message> register(Long postId, CommentCreate request) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = communityCommentFacade.register(postId, request, currentMemberId);
        return new TtoklipResponse<>(message);
    }

    @Override
    public TtoklipResponse<Message> report(Long commentId, ReportWebCreate request) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = communityCommentFacade.report(commentId, request, currentMemberId);
        return new TtoklipResponse<>(message);
    }

    @Override
    public TtoklipResponse<Message> delete(Long commentId) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = communityCommentFacade.delete(commentId, currentMemberId);
        return new TtoklipResponse<>(message);
    }
}
