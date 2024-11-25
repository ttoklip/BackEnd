package com.api.newsletter.presentation;

import com.api.common.ReportWebCreate;
import com.api.global.support.response.Message;
import com.api.global.support.response.TtoklipResponse;
import com.api.global.util.SecurityUtil;
import com.api.newsletter.application.NewsletterCommentFacade;
import com.domain.comment.domain.CommentCreate;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/newsletter/comment")
public class NewsletterCommentController implements NewsletterCommentControllerDocs {

    private final NewsletterCommentFacade newsletterCommentFacade;

    @Override
    @PostMapping("/{postId}")
    public TtoklipResponse<Message> register(@PathVariable Long postId,
                                             @RequestBody CommentCreate request) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = newsletterCommentFacade.register(postId, request, currentMemberId);
        return new TtoklipResponse<>(message);
    }

    @Override
    @PostMapping("/report/{commentId}")
    public TtoklipResponse<Message> report(@PathVariable Long commentId, @RequestBody ReportWebCreate request) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = newsletterCommentFacade.report(commentId, request, currentMemberId);
        return new TtoklipResponse<>(message);
    }

    @Override
    @DeleteMapping("/{commentId}")
    public TtoklipResponse<Message> delete(@PathVariable Long commentId) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = newsletterCommentFacade.delete(commentId, currentMemberId);
        return new TtoklipResponse<>(message);
    }
}
