package com.api.ttoklip.domain.notification.service;

import static com.api.ttoklip.global.util.SecurityUtil.getCurrentMember;

import com.api.ttoklip.domain.common.comment.Comment;
import com.api.ttoklip.domain.honeytip.comment.domain.HoneyTipComment;
import com.api.ttoklip.domain.notification.repository.NotificationCommentRepository;
import com.api.ttoklip.domain.question.comment.domain.QuestionComment;
import com.api.ttoklip.domain.town.cart.comment.CartComment;
import com.api.ttoklip.domain.town.community.comment.CommunityComment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotificationTargetFilter {

    private final NotificationCommentRepository notiCommentRepository;

    // 현재 사용자가 생성된 답글의 댓글 작성자의 ID가 맞는지 확인
    public boolean determineCommentNoticeTarget(final Comment findComment) {
        // 댓글 작성자가 맞는지 필터링
        log.info("[Method Name] = NotificationTargetFilter.determineCommentNoticeTarget");
        Comment comment = notiCommentRepository.findParentCommentFetchJoin(findComment.getId());
        log.info("[parent comment id] start ------------");
        Long parentCommentWriterId = comment.getParent().getMember().getId();
        log.info("[parent comment id] -end- ------------");
        if (getCurrentMember().getId().equals(parentCommentWriterId)) {
            return true;
        }
        return false;
    }

    // --------------------------   현재 사용자가 생성된 댓글의 작성자의 ID가 맞는지 확인   --------------------------
    public boolean determinePostNoticeTarget(final Comment comment) {
        // 꿀팁공유해요 작성자가 맞는지 필터링
        if (comment instanceof HoneyTipComment findHoneyTipComment) {
            HoneyTipComment honeyTipComment = notiCommentRepository.findHoneyTipCommentFetchJoin(
                    findHoneyTipComment.getId());
            Long honeyTipWriterId = honeyTipComment.getHoneyTip().getMember().getId();
            if (getCurrentMember().getId().equals(honeyTipWriterId)) {
                return true;
            }
        }

        // 질문해요 작성자가 맞는지 필터링
        if (comment instanceof QuestionComment findQuestionComment) {
            QuestionComment questionComment = notiCommentRepository.findQuestionCommentFetchJoin(
                    findQuestionComment.getId());
            Long questionWriterId = questionComment.getQuestion().getMember().getId();
            if (getCurrentMember().getId().equals(questionWriterId)) {
                return true;
            }
        }

        // 함께해요 작성자가 맞는지 필터링
        if (comment instanceof CartComment findCartComment) {
            CartComment cartComment = notiCommentRepository.findCartCommentFetchJoin(
                    findCartComment.getId());
            Long cartWriterId = cartComment.getCart().getMember().getId();
            if (getCurrentMember().getId().equals(cartWriterId)) {
                return true;
            }
        }

        // 소통해요 작성자가 맞는지 필터링
        if (comment instanceof CommunityComment findCommunityComment) {
            CommunityComment communityComment = notiCommentRepository.findCommunityCommentFetchJoin(
                    findCommunityComment.getId());
            Long communityWriterId = communityComment.getCommunity().getMember().getId();
            if (getCurrentMember().getId().equals(communityWriterId)) {
                return true;
            }
        }

        return false;
    }
    // --------------------------   현재 사용자가 생성된 댓글의 작성자의 ID가 맞는지 확인 끝  --------------------------
}
