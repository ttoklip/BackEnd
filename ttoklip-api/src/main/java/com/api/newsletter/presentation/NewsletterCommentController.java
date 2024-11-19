package com.api.newsletter.presentation;

import com.api.common.ReportWebCreate;
import com.api.global.support.response.Message;
import com.api.global.support.response.TtoklipResponse;
import com.api.global.util.SecurityUtil;
import com.api.newsletter.application.NewsletterCommentFacade;
import com.domain.comment.domain.CommentCreate;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class NewsletterCommentController implements NewsletterCommentControllerDocs {

    private final NewsletterCommentFacade newsletterCommentFacade;

    @Override
    public TtoklipResponse<Message> register(Long postId, CommentCreate request) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = newsletterCommentFacade.register(postId, request, currentMemberId);
        return new TtoklipResponse<>(message);
    }

    @Override
    public TtoklipResponse<Message> report(Long commentId, ReportWebCreate request) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = newsletterCommentFacade.report(commentId, request, currentMemberId);
        return new TtoklipResponse<>(message);
    }

    @Override
    public TtoklipResponse<Message> delete(Long commentId) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = newsletterCommentFacade.delete(commentId, currentMemberId);
        return new TtoklipResponse<>(message);
    }
}
