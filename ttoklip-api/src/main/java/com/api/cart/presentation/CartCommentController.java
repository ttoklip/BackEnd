package com.api.cart.presentation;

import com.api.cart.application.CartCommentFacade;
import com.api.common.ReportWebCreate;
import com.api.global.support.response.Message;
import com.api.global.support.response.TtoklipResponse;
import com.api.global.util.SecurityUtil;
import com.domain.comment.domain.CommentCreate;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class CartCommentController implements CartCommentControllerDocs {

    private final CartCommentFacade cartCommentFacade;

    @Override
    public TtoklipResponse<Message> register(Long postId, CommentCreate request) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = cartCommentFacade.register(postId, request, currentMemberId);
        return new TtoklipResponse<>(message);
    }

    @Override
    public TtoklipResponse<Message> report(Long commentId, ReportWebCreate request) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = cartCommentFacade.report(commentId, request, currentMemberId);
        return new TtoklipResponse<>(message);
    }

    @Override
    public TtoklipResponse<Message> delete(Long commentId) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = cartCommentFacade.delete(commentId, currentMemberId);
        return new TtoklipResponse<>(message);
    }
}
