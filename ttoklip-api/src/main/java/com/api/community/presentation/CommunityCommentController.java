package com.api.community.presentation;

import com.api.common.ReportWebCreate;
import com.api.community.application.CommunityCommentFacade;
import com.api.global.support.response.Message;
import com.api.global.support.response.TtoklipResponse;
import com.api.global.util.SecurityUtil;
import com.domain.comment.domain.CommentCreate;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/town/comms/comment")
public class CommunityCommentController implements CommunityCommentControllerDocs {

    private final CommunityCommentFacade communityCommentFacade;

    @Override
    @PostMapping("/{postId}")
    public TtoklipResponse<Message> register(@PathVariable Long postId,
                                             @RequestBody CommentCreate request) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = communityCommentFacade.register(postId, request, currentMemberId);
        return new TtoklipResponse<>(message);
    }

    @Override
    @PostMapping("/report/{commentId}")
    public TtoklipResponse<Message> report(@PathVariable Long commentId,
                                           @RequestBody ReportWebCreate request) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = communityCommentFacade.report(commentId, request, currentMemberId);
        return new TtoklipResponse<>(message);
    }

    @Override
    @DeleteMapping("/{commentId}")
    public TtoklipResponse<Message> delete(@PathVariable Long commentId) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = communityCommentFacade.delete(commentId, currentMemberId);
        return new TtoklipResponse<>(message);
    }
}
