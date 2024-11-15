package com.domain.honeytip.application;

import com.common.NotiCategory;
import com.common.annotation.SendCommentNotification;
import com.domain.comment.domain.Comment;
import com.domain.comment.domain.CommentCreate;
import com.domain.comment.domain.CommentRepository;
import com.domain.honeytip.domain.HoneyTip;
import com.domain.honeytip.domain.HoneyTipComment;
import com.domain.member.domain.Member;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HoneyTipCommentService {

    private final CommentRepository commentRepository;

    public List<HoneyTipComment> findCommentsByHoneyTipId(final Long postId) {
        return commentRepository.findCommentsByHoneyTipId(postId);
    }

    // 대댓글 생성
    @SendCommentNotification(notiCategory = NotiCategory.NEWS_LETTER_CHILD_COMMENT)
    public Comment registerCommentWithParent(final CommentCreate request, final HoneyTip findHoneyTip,
                                             final Comment parentComment, final Member member) {
        HoneyTipComment honeyTipComment = HoneyTipComment.withParentOf(request, parentComment, findHoneyTip, member);
        return commentRepository.save(honeyTipComment);
    }

    // 최상위 댓글 생성
    @SendCommentNotification(notiCategory = NotiCategory.HONEY_TIP_COMMENT)
    public Comment registerCommentOrphanage(final CommentCreate request, final HoneyTip findHoneyTip,
                                            final Member member) {
        HoneyTipComment honeyTipComment = HoneyTipComment.orphanageOf(request, findHoneyTip, member);
        return commentRepository.save(honeyTipComment);
    }
}
