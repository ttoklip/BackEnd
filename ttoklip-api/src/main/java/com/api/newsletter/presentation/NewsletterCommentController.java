package com.api.newsletter.presentation;

import com.api.common.ReportWebCreate;
import com.api.global.support.response.Message;
import com.api.global.support.response.TtoklipResponse;
import com.api.global.util.SecurityUtil;
import com.api.newsletter.application.NewsletterCommentFacade;
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
@RequestMapping("/api/v1/newsletter/comment")
public class NewsletterCommentController implements NewsletterCommentControllerDocs {

    private final NewsletterCommentFacade newsletterCommentFacade;

    @Override
    @PostMapping("/{postId}")
    public TtoklipResponse<Message> register(
            final @PathVariable Long postId,
            final @RequestBody CommentCreate request
    ) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return TtoklipResponse.created(
                newsletterCommentFacade.register(postId, request, currentMemberId)
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
                newsletterCommentFacade.report(commentId, request, currentMemberId)
        );
    }

    @Override
    @DeleteMapping("/{commentId}")
    public TtoklipResponse<Message> delete(
            final @PathVariable Long commentId
    ) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return TtoklipResponse.ok(
                newsletterCommentFacade.delete(commentId, currentMemberId)
        );
    }
}
