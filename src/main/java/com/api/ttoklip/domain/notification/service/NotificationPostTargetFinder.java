package com.api.ttoklip.domain.notification.service;

import static com.api.ttoklip.global.util.SecurityUtil.getCurrentMember;

import com.api.ttoklip.domain.honeytip.post.domain.HoneyTip;
import com.api.ttoklip.domain.honeytip.post.service.HoneyTipCommonService;
import com.api.ttoklip.domain.notification.entity.NotiCategory;
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

    public Optional<Long> getPostWriterId(final NotiCategory request, final Long targetIndex) {

        // 꿀팁공유해요 작성자 반환
        if (request.equals(NotiCategory.HONEY_TIP_SCRAP) || request.equals(NotiCategory.HONEY_TIP_LIKE)) {
            HoneyTip honeytip = honeyTipCommonService.getHoneytip(targetIndex);
            return Optional.of(honeytip.getMember().getId());
        }

        if (request.equals(NotiCategory.OUR_TOWN_SCRAP)) {
            Community community = communityCommonService.getCommunity(targetIndex);
            return Optional.of(community.getMember().getId());
        }

        if (request.equals(NotiCategory.OUR_TOWN_TOGETHER)) {
            Cart cart = cartPostService.findCartByIdActivated(targetIndex);
            Long writerId = cart.getMember().getId();
            return Optional.of(writerId);
        }

        return Optional.empty();
    }

}
