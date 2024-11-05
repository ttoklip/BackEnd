package com.api.ttoklip.domain.notification.service;

import com.api.ttoklip.domain.honeytip.domain.HoneyTip;
import com.api.ttoklip.domain.honeytip.service.HoneyTipPostService;
import com.api.ttoklip.domain.notification.dto.response.NotificationServerResponse;
import com.api.ttoklip.domain.notification.entity.NotiCategory;
import com.api.ttoklip.domain.question.domain.QuestionComment;
import com.api.ttoklip.domain.question.service.QuestionCommentService;
import com.api.ttoklip.domain.town.cart.post.entity.Cart;
import com.api.ttoklip.domain.town.cart.post.service.CartPostService;
import com.api.ttoklip.domain.town.community.post.entity.Community;
import com.api.ttoklip.domain.town.community.post.service.CommunityCommonService;
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
    private final CommunityCommonService communityCommonService;
    private final CartPostService cartPostService;
    private final QuestionCommentService questionCommentService;

    public Optional<NotificationServerResponse> getPostWriterId(final NotiCategory request, final Long targetIndex) {

        // 꿀팁공유해요 작성자 반환
        if (request.equals(NotiCategory.HONEY_TIP_SCRAP) || request.equals(NotiCategory.HONEY_TIP_LIKE)) {
            HoneyTip honeytip = honeyTipPostService.getHoneytip(targetIndex);
            Long writerId = honeytip.getMember().getId();

            NotificationServerResponse response = NotificationServerResponse.of(writerId, honeytip.getId());
            return Optional.of(response);
        }

        if (request.equals(NotiCategory.OUR_TOWN_SCRAP)) {
            Community community = communityCommonService.getCommunity(targetIndex);
            Long writerId = community.getMember().getId();

            NotificationServerResponse response = NotificationServerResponse.of(writerId, community.getId());
            return Optional.of(response);
        }

        if (request.equals(NotiCategory.OUR_TOWN_TOGETHER)) {
            Cart cart = cartPostService.findCartByIdActivated(targetIndex);
            Long writerId = cart.getMember().getId();

            NotificationServerResponse response = NotificationServerResponse.of(writerId, cart.getId());
            return Optional.of(response);
        }

        return Optional.empty();
    }

    public Optional<NotificationServerResponse> getCommentWriterId(final NotiCategory request, final Long targetIndex) {
        if (request.equals(NotiCategory.QUESTION_COMMENT_LIKE)) {
            QuestionComment questionComment = questionCommentService.findQuestionComment(targetIndex);
            Long writerId = questionComment.getMember().getId();

            NotificationServerResponse response = NotificationServerResponse.of(writerId, questionComment.getId());
            return Optional.of(response);
        }

        return Optional.empty();
    }

}
