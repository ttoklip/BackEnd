package com.api.question.presentation;

import com.api.common.ReportWebCreate;
import com.api.global.support.response.Message;
import com.api.global.support.response.TtoklipResponse;
import com.api.global.util.SecurityUtil;
import com.api.question.application.QuestionCommentFacade;
import com.api.question.application.QuestionCommentLikeFacade;
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
@RequestMapping("/api/v1/question/comment")
public class QuestionCommentController implements QuestionCommentControllerDocs {

    private final QuestionCommentFacade questionCommentFacade;
    private final QuestionCommentLikeFacade questionCommentLikeFacade;

    @Override
    @PostMapping("/{postId}")
    public TtoklipResponse<Message> register(
            final @PathVariable Long postId,
            final @RequestBody CommentCreate request
    ) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return TtoklipResponse.created(
                questionCommentFacade.register(postId, request, currentMemberId)
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
                questionCommentFacade.report(commentId, request, currentMemberId)
        );
    }

    @Override
    @DeleteMapping("/{commentId}")
    public TtoklipResponse<Message> delete(
            final @PathVariable Long commentId
    ) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return TtoklipResponse.ok(
                questionCommentFacade.delete(commentId, currentMemberId)
        );
    }

    @Override
    @PostMapping("/like/{commentId}")
    public TtoklipResponse<Message> registerLike(
            final @PathVariable Long commentId
    ) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return TtoklipResponse.created(
                questionCommentLikeFacade.registerLike(commentId, currentMemberId)
        );
    }

    @Override
    @DeleteMapping("/like/{commentId}")
    public TtoklipResponse<Message> cancelLike(
            final @PathVariable Long commentId
    ) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return TtoklipResponse.ok(
                questionCommentLikeFacade.cancelLike(commentId, currentMemberId)
        );
    }
}
