package com.api.question.presentation;

import com.api.common.ReportWebCreate;
import com.api.global.support.response.Message;
import com.api.global.support.response.TtoklipResponse;
import com.api.global.util.SecurityUtil;
import com.api.question.application.QuestionCommentFacade;
import com.api.question.application.QuestionCommentLikeFacade;
import com.domain.comment.domain.CommentCreate;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/question/comment")
public class QuestionCommentController implements QuestionCommentControllerDocs {

    private final QuestionCommentFacade questionCommentFacade;
    private final QuestionCommentLikeFacade questionCommentLikeFacade;

    @Override
    @PostMapping("/{postId}")
    public TtoklipResponse<Message> register(@PathVariable Long postId,
                                             @RequestBody CommentCreate request) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = questionCommentFacade.register(postId, request, currentMemberId);
        return new TtoklipResponse<>(message);
    }

    @Override
    @PostMapping("/report/{commentId}")
    public TtoklipResponse<Message> report(@PathVariable Long commentId,
                                           @RequestBody ReportWebCreate request) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = questionCommentFacade.report(commentId, request, currentMemberId);
        return new TtoklipResponse<>(message);
    }

    @Override
    @DeleteMapping("/{commentId}")
    public TtoklipResponse<Message> delete(@PathVariable Long commentId) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = questionCommentFacade.delete(commentId, currentMemberId);
        return new TtoklipResponse<>(message);
    }

    @Override
    @PostMapping("/like/{commentId}")
    public TtoklipResponse<Message> registerLike(@PathVariable Long commentId) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = questionCommentLikeFacade.registerLike(commentId, currentMemberId);
        return new TtoklipResponse<>(message);
    }

    @Override
    @DeleteMapping("/like/{commentId}")
    public TtoklipResponse<Message> cancelLike(@PathVariable Long commentId) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = questionCommentLikeFacade.cancelLike(commentId, currentMemberId);
        return new TtoklipResponse<>(message);
    }
}
