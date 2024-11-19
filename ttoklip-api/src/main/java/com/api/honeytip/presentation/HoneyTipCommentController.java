package com.api.honeytip.presentation;

import static com.api.global.util.SecurityUtil.getCurrentMember;

import com.api.common.ReportWebCreate;
import com.api.global.support.response.Message;
import com.api.global.support.response.TtoklipResponse;
import com.api.honeytip.application.HoneyTipCommentFacade;
import com.domain.comment.domain.CommentCreate;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HoneyTipCommentController implements HoneyTipCommentControllerDocs {

    private final HoneyTipCommentFacade honeyTipCommentFacade;

    @Override
    public TtoklipResponse<Message> register(Long postId, CommentCreate request) {
        Long currentMemberId = getCurrentMember().getId();
        Message message = honeyTipCommentFacade.register(postId, request, currentMemberId);
        return new TtoklipResponse<>(message);
    }

    @Override
    public TtoklipResponse<Message> report(Long commentId, ReportWebCreate request) {
        Long currentMemberId = getCurrentMember().getId();
        Message message = honeyTipCommentFacade.report(commentId, request, currentMemberId);
        return new TtoklipResponse<>(message);
    }

    @Override
    public TtoklipResponse<Message> delete(Long commentId) {
        Long currentMemberId = getCurrentMember().getId();
        Message message = honeyTipCommentFacade.delete(commentId, currentMemberId);
        return new TtoklipResponse<>(message);
    }
}
