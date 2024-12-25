package com.api.honeytip.presentation;

import static com.api.global.util.SecurityUtil.getCurrentMember;

import com.api.common.ReportWebCreate;
import com.api.global.support.response.Message;
import com.api.global.support.response.TtoklipResponse;
import com.api.honeytip.application.HoneyTipCommentFacade;
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
@RequestMapping("/api/v1/honeytip/comment")
public class HoneyTipCommentController implements HoneyTipCommentControllerDocs {

    private final HoneyTipCommentFacade honeyTipCommentFacade;

    @Override
    @PostMapping("/{postId}")
    public TtoklipResponse<Message> register(
            final @PathVariable Long postId,
            final @RequestBody CommentCreate request
    ) {
        Long currentMemberId = getCurrentMember().getId();
        return TtoklipResponse.ok(
                honeyTipCommentFacade.register(postId, request, currentMemberId)
        );
    }

    @Override
    @PostMapping("/report/{commentId}")
    public TtoklipResponse<Message> report(
            final @PathVariable Long commentId,
            final @RequestBody ReportWebCreate request
    ) {
        Long currentMemberId = getCurrentMember().getId();
        return TtoklipResponse.created(
                honeyTipCommentFacade.report(commentId, request, currentMemberId)
        );
    }

    @Override
    @DeleteMapping("/{commentId}")
    public TtoklipResponse<Message> delete(
            final @PathVariable Long commentId
    ) {
        Long currentMemberId = getCurrentMember().getId();
        return TtoklipResponse.ok(
                honeyTipCommentFacade.delete(commentId, currentMemberId)
        );
    }
}
