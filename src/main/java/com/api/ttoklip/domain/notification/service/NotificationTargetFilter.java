package com.api.ttoklip.domain.notification.service;

import static com.api.ttoklip.global.util.SecurityUtil.getCurrentMember;

import com.api.ttoklip.domain.common.comment.Comment;
import com.api.ttoklip.domain.honeytip.comment.domain.HoneyTipComment;
import com.api.ttoklip.domain.notification.repository.NotificationCommentRepository;
import com.api.ttoklip.domain.question.comment.domain.QuestionComment;
import com.api.ttoklip.domain.town.cart.comment.CartComment;
import com.api.ttoklip.domain.town.community.comment.CommunityComment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationTargetFilter {

    private final NotificationCommentRepository notiCommentRepository;

    public boolean determineCommentNoticeTarget(final Comment findComment) {
        // 댓글 작성자가 맞는지 필터링
        Comment comment = notiCommentRepository.findParentCommentFetchJoin(findComment.getId());
        Long parentCommentWriterId = comment.getParent().getMember().getId();
        if (getCurrentMember().getId().equals(parentCommentWriterId)) {
            return true;
        }
        return false;
    }

    // ToDo FetchJoin
    public boolean determinePostNoticeTarget(final Comment comment) {
        // 꿀팁공유해요 작성자가 맞는지 필터링
        if (comment instanceof HoneyTipComment honeyTipComment) {
            Long honeyTipWriterId = honeyTipComment.getHoneyTip().getMember().getId();
            if (getCurrentMember().getId().equals(honeyTipWriterId)) {
                return true;
            }
        }

        // 질문해요 작성자가 맞는지 필터링
        if (comment instanceof QuestionComment questionComment) {
            Long questionWriterId = questionComment.getQuestion().getMember().getId();
            if (getCurrentMember().getId().equals(questionWriterId)) {
                return true;
            }
        }

        // 함께해요 작성자가 맞는지 필터링
        if (comment instanceof CartComment cartComment) {
            Long cartWriterId = cartComment.getCart().getMember().getId();
            if (getCurrentMember().getId().equals(cartWriterId)) {
                return true;
            }
        }

        // 소통해요 작성자가 맞는지 필터링
        if (comment instanceof CommunityComment communityComment) {
            Long communityWriterId = communityComment.getCommunity().getMember().getId();
            if (getCurrentMember().getId().equals(communityWriterId)) {
                return true;
            }
        }

        return false;
    }
}
