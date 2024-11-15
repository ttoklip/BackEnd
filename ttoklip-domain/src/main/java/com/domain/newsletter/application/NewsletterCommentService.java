package com.domain.newsletter.application;

import com.common.NotiCategory;
import com.common.annotation.SendCommentNotification;
import com.domain.comment.domain.Comment;
import com.domain.comment.domain.CommentCreate;
import com.domain.comment.domain.CommentRepository;
import com.domain.member.domain.Member;
import com.domain.newsletter.domain.Newsletter;
import com.domain.newsletter.domain.NewsletterComment;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NewsletterCommentService {

    private final CommentRepository commentRepository;

    public List<NewsletterComment> findCommentsByNewsletterId(final Long postId) {
        return commentRepository.findCommentsByNewsletterId(postId);
    }

    // 대댓글 생성
    @SendCommentNotification(notiCategory = NotiCategory.NEWS_LETTER_CHILD_COMMENT)
    public Comment registerCommentWithParent(final CommentCreate request, final Newsletter newsletter,
                                             final Comment parentComment, final Member currentMember) {
        NewsletterComment newsletterComment = NewsletterComment.withParentOf(request, parentComment, newsletter, currentMember);
        return commentRepository.save(newsletterComment);
    }

    // 최상위 댓글 생성
    public Comment registerCommentOrphanage(final CommentCreate request, final Newsletter newsletter,
                                            final Member currentMember) {
        NewsletterComment newsletterComment = NewsletterComment.orphanageOf(request, newsletter, currentMember);
        return commentRepository.save(newsletterComment);
    }
}
