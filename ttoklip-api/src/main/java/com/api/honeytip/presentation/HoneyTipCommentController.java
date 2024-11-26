package com.api.honeytip.presentation;

import static com.api.global.util.SecurityUtil.getCurrentMember;

import com.api.common.ReportWebCreate;
import com.api.global.support.response.Message;
import com.api.global.support.response.TtoklipResponse;
import com.api.honeytip.application.HoneyTipCommentFacade;
import com.domain.comment.domain.CommentCreate;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/honeytip/comment")
public class HoneyTipCommentController implements HoneyTipCommentControllerDocs {

    private final HoneyTipCommentFacade honeyTipCommentFacade;

    @Override
    @PostMapping("/{postId}")
    public TtoklipResponse<Message> register(@PathVariable Long postId,
                                             @RequestBody CommentCreate request) {
        Long currentMemberId = getCurrentMember().getId();
        Message message = honeyTipCommentFacade.register(postId, request, currentMemberId);
        return new TtoklipResponse<>(message);
    }

    @Override
    @PostMapping("/report/{commentId}")
    public TtoklipResponse<Message> report(@PathVariable Long commentId,
                                           @RequestBody ReportWebCreate request) {
        Long currentMemberId = getCurrentMember().getId();
        Message message = honeyTipCommentFacade.report(commentId, request, currentMemberId);
        return new TtoklipResponse<>(message);
    }

    @Override
    @DeleteMapping("/{commentId}")
    public TtoklipResponse<Message> delete(@PathVariable Long commentId) {
        Long currentMemberId = getCurrentMember().getId();
        Message message = honeyTipCommentFacade.delete(commentId, currentMemberId);
        return new TtoklipResponse<>(message);
    }
}
