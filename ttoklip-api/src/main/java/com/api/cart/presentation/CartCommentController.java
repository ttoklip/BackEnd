package com.api.cart.presentation;

import com.api.cart.application.CartCommentFacade;
import com.api.common.ReportWebCreate;
import com.api.global.support.response.Message;
import com.api.global.support.response.TtoklipResponse;
import com.api.global.util.SecurityUtil;
import com.domain.comment.domain.CommentCreate;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/town/carts/comment")
public class CartCommentController implements CartCommentControllerDocs {

    private final CartCommentFacade cartCommentFacade;

    @Override
    @PostMapping("/{postId}")
    public TtoklipResponse<Message> register(@PathVariable Long postId,
                                             @RequestBody CommentCreate request) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = cartCommentFacade.register(postId, request, currentMemberId);
        return new TtoklipResponse<>(message);
    }

    @Override
    @PostMapping("/report/{commentId}")
    public TtoklipResponse<Message> report(@PathVariable Long commentId,
                                           @RequestBody ReportWebCreate request) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = cartCommentFacade.report(commentId, request, currentMemberId);
        return new TtoklipResponse<>(message);
    }

    @Override
    @DeleteMapping("/{commentId}")
    public TtoklipResponse<Message> delete(@PathVariable Long commentId) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = cartCommentFacade.delete(commentId, currentMemberId);
        return new TtoklipResponse<>(message);
    }
}
