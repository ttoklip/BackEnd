package com.domain.notification.application;

import com.domain.cart.application.CartPostService;
import com.domain.cart.domain.Cart;
import com.domain.community.application.CommunityPostService;
import com.domain.community.domain.Community;
import com.domain.honeytip.application.HoneyTipPostService;
import com.domain.honeytip.domain.HoneyTip;
import com.domain.notification.domain.vo.NotiCategory;
import com.domain.notification.dto.response.NotificationInternalResponse;
import com.domain.question.application.QuestionCommentService;
import com.domain.question.domain.QuestionComment;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotificationPostTargetFinder {

    private final HoneyTipPostService honeyTipPostService;
    private final CommunityPostService communityPostService;
    private final CartPostService cartPostService;
    private final QuestionCommentService questionCommentService;

    public Optional<NotificationInternalResponse> getPostWriterId(final NotiCategory request, final Long targetIndex) {

        // 꿀팁공유해요 작성자 반환
        if (request.equals(NotiCategory.HONEY_TIP_SCRAP) || request.equals(NotiCategory.HONEY_TIP_LIKE)) {
            HoneyTip honeytip = honeyTipPostService.getHoneytip(targetIndex);
            Long writerId = honeytip.getMember().getId();

            NotificationInternalResponse response = NotificationInternalResponse.of(writerId, honeytip.getId());
            return Optional.of(response);
        }

        if (request.equals(NotiCategory.OUR_TOWN_SCRAP)) {
            Community community = communityPostService.getCommunity(targetIndex);
            Long writerId = community.getMember().getId();

            NotificationInternalResponse response = NotificationInternalResponse.of(writerId, community.getId());
            return Optional.of(response);
        }

        if (request.equals(NotiCategory.OUR_TOWN_TOGETHER)) {
            Cart cart = cartPostService.findByIdActivated(targetIndex);
            Long writerId = cart.getMember().getId();

            NotificationInternalResponse response = NotificationInternalResponse.of(writerId, cart.getId());
            return Optional.of(response);
        }

        return Optional.empty();
    }

    public Optional<NotificationInternalResponse> getCommentWriterId(final NotiCategory request, final Long targetIndex) {
        if (request.equals(NotiCategory.QUESTION_COMMENT_LIKE)) {
            QuestionComment questionComment = questionCommentService.findQuestionComment(targetIndex);
            Long writerId = questionComment.getMember().getId();

            NotificationInternalResponse response = NotificationInternalResponse.of(writerId, questionComment.getId());
            return Optional.of(response);
        }

        return Optional.empty();
    }

}
