package com.api.ttoklip.domain.notification.service;

import com.api.ttoklip.domain.honeytip.post.domain.HoneyTip;
import com.api.ttoklip.domain.honeytip.post.service.HoneyTipCommonService;
import com.api.ttoklip.domain.notification.entity.NotiCategory;
import com.api.ttoklip.domain.question.comment.domain.QuestionComment;
import com.api.ttoklip.domain.question.comment.service.QuestionCommentService;
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

    private final HoneyTipCommonService honeyTipCommonService;
    private final CommunityCommonService communityCommonService;
    private final CartPostService cartPostService;
    private final QuestionCommentService questionCommentService;

    public Optional<Long> getPostWriterId(final NotiCategory request, final Long targetIndex) {

        // 꿀팁공유해요 작성자 반환
        if (request.equals(NotiCategory.HONEY_TIP_SCRAP) || request.equals(NotiCategory.HONEY_TIP_LIKE)) {
            HoneyTip honeytip = honeyTipCommonService.getHoneytip(targetIndex);
            Long writerId = honeytip.getMember().getId();
            return Optional.of(writerId);
        }

        if (request.equals(NotiCategory.OUR_TOWN_SCRAP)) {
            Community community = communityCommonService.getCommunity(targetIndex);
            Long writerId = community.getMember().getId();
            return Optional.of(writerId);
        }

        if (request.equals(NotiCategory.OUR_TOWN_TOGETHER)) {
            Cart cart = cartPostService.findCartByIdActivated(targetIndex);
            Long writerId = cart.getMember().getId();
            return Optional.of(writerId);
        }

        return Optional.empty();
    }

    public Optional<Long> getCommentWriterId(final NotiCategory request, final Long targetIndex) {
        if (request.equals(NotiCategory.QUESTION_COMMENT_LIKE)) {
            QuestionComment questionComment = questionCommentService.findQuestionComment(targetIndex);
            Long writerId = questionComment.getMember().getId();
            return Optional.of(writerId);
        }

        return Optional.empty();
    }

}
