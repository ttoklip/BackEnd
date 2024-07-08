package com.api.ttoklip.domain.notification.service;

import com.api.ttoklip.domain.common.comment.Comment;
import com.api.ttoklip.domain.honeytip.comment.domain.HoneyTipComment;
import com.api.ttoklip.domain.notification.dto.response.NotificationServerResponse;
import com.api.ttoklip.domain.notification.repository.NotificationCommentRepository;
import com.api.ttoklip.domain.question.comment.domain.QuestionComment;
import com.api.ttoklip.domain.town.cart.comment.CartComment;
import com.api.ttoklip.domain.town.community.comment.CommunityComment;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotificationCommentTargetFinder {

    private final NotificationCommentRepository notiCommentRepository;

    // --------------------------   생성된 답글에 대해 댓글(답글의 부모)의 작성자의 ID를 반환   --------------------------
    public NotificationServerResponse getParentCommentWriterId(final Comment findComment) {
        // 댓글 작성자가 맞는지 필터링
        log.info("[Method Name] = NotificationTargetFilter.determineCommentNoticeTarget");
        Comment comment = notiCommentRepository.findParentCommentFetchJoin(findComment.getId());
        log.info("[parent comment id] start ------------");
        Long parentCommentWriterId = comment.getParent().getMember().getId();
        log.info("[parent comment id] -end- ------------");

        return NotificationServerResponse.of(parentCommentWriterId, comment.getId());
    }

    // --------------------------   생성된 댓글(답글 + 댓글)의 작성자의 ID를 반환   --------------------------
    public Optional<NotificationServerResponse> getPostWriterId(final Comment comment) {
        // 꿀팁공유해요 작성자가 맞는지 필터링
        if (comment instanceof HoneyTipComment findHoneyTipComment) {

            HoneyTipComment honeyTipComment = notiCommentRepository.findHoneyTipCommentFetchJoin(
                    findHoneyTipComment.getId());
            Long honeyTipWriterId = honeyTipComment.getHoneyTip().getMember().getId();

            NotificationServerResponse response = NotificationServerResponse.of(honeyTipWriterId,
                    honeyTipComment.getId());
            return Optional.of(response);
        }

        // 질문해요 작성자가 맞는지 필터링
        if (comment instanceof QuestionComment findQuestionComment) {
            QuestionComment questionComment = notiCommentRepository.findQuestionCommentFetchJoin(
                    findQuestionComment.getId());
            Long questionWriterId = questionComment.getQuestion().getMember().getId();

            NotificationServerResponse response = NotificationServerResponse.of(questionWriterId,
                    questionComment.getId());
            return Optional.of(response);
        }

        // 함께해요 작성자가 맞는지 필터링
        if (comment instanceof CartComment findCartComment) {
            CartComment cartComment = notiCommentRepository.findCartCommentFetchJoin(
                    findCartComment.getId());
            Long cartWriterId = cartComment.getCart().getMember().getId();

            NotificationServerResponse response = NotificationServerResponse.of(cartWriterId, cartComment.getId());
            return Optional.of(response);
        }

        // 소통해요 작성자가 맞는지 필터링
        if (comment instanceof CommunityComment findCommunityComment) {
            CommunityComment communityComment = notiCommentRepository.findCommunityCommentFetchJoin(
                    findCommunityComment.getId());
            Long communityWriterId = communityComment.getCommunity().getMember().getId();
            NotificationServerResponse response = NotificationServerResponse.of(communityWriterId,
                    communityComment.getId());
            return Optional.of(response);
        }

        return Optional.empty();
    }
    // --------------------------   현재 사용자가 생성된 댓글의 작성자의 ID가 맞는지 확인 끝  --------------------------
}
