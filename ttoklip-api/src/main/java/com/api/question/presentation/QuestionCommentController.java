package com.api.question.presentation;

import com.api.common.ReportWebCreate;
import com.api.global.support.response.Message;
import com.api.global.support.response.TtoklipResponse;
import com.api.global.util.SecurityUtil;
import com.api.question.application.QuestionCommentFacade;
import com.api.question.application.QuestionCommentLikeFacade;
import com.domain.comment.domain.CommentCreate;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class QuestionCommentController implements QuestionCommentControllerDocs {

    private final QuestionCommentFacade questionCommentFacade;
    private final QuestionCommentLikeFacade questionCommentLikeFacade;

    @Override
    public TtoklipResponse<Message> register(Long postId, CommentCreate request) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = questionCommentFacade.register(postId, request, currentMemberId);
        return new TtoklipResponse<>(message);
    }

    @Override
    public TtoklipResponse<Message> report(Long commentId, ReportWebCreate request) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = questionCommentFacade.report(commentId, request, currentMemberId);
        return new TtoklipResponse<>(message);
    }

    @Override
    public TtoklipResponse<Message> delete(Long commentId) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = questionCommentFacade.delete(commentId, currentMemberId);
        return new TtoklipResponse<>(message);
    }

    @Override
    public TtoklipResponse<Message> registerLike(Long commentId) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = questionCommentLikeFacade.registerLike(commentId, currentMemberId);
        return new TtoklipResponse<>(message);
    }

    @Override
    public TtoklipResponse<Message> cancelLike(Long commentId) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = questionCommentLikeFacade.cancelLike(commentId, currentMemberId);
        return new TtoklipResponse<>(message);
    }
}
