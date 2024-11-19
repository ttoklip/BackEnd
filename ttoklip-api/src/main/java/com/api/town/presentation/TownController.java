package com.api.town.presentation;

import com.api.cart.presentation.dto.response.CartPaging;
import com.api.community.presentation.dto.response.CommunityPaging;
import com.api.global.support.response.TtoklipResponse;
import com.api.global.util.SecurityUtil;
import com.api.town.application.TownFacade;
import com.api.town.application.TownMainResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TownController implements TownControllerDocs {

    private static final int PAGE_SIZE = 10;
    private final TownFacade townFacade;

    @Override
    public TtoklipResponse<TownMainResponse> getCarts(String criteria) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        TownMainResponse cartMainResponse = townFacade.getRecent3(criteria, currentMemberId);
        return new TtoklipResponse<>(cartMainResponse);
    }

    @Override
    public TtoklipResponse<CommunityPaging> getCommunities(String criteria, int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return new TtoklipResponse<>(townFacade.getCommunities(criteria, pageable, currentMemberId));
    }

    @Override
    public TtoklipResponse<CartPaging> getCarts(int page, Long startMoney, Long lastMoney, Long startParty, Long lastParty, String criteria) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return new TtoklipResponse<>(townFacade.getCarts(pageable, startMoney, lastMoney, startParty, lastParty, criteria, currentMemberId));
    }
}
