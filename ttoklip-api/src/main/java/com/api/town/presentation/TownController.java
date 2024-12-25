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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/town/main")
public class TownController implements TownControllerDocs {

    private static final int PAGE_SIZE = 10;
    private final TownFacade townFacade;

    @Override
    @GetMapping
    public TtoklipResponse<TownMainResponse> getCarts(
            final @RequestParam(defaultValue = "CITY") String criteria
    ) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return TtoklipResponse.ok(
                townFacade.getRecent3(criteria, currentMemberId)
        );
    }

    @Override
    @GetMapping("/community")
    public TtoklipResponse<CommunityPaging> getCommunities(
            final @RequestParam(defaultValue = "CITY") String criteria,
            final @RequestParam(defaultValue = "0") int page,
            final @RequestParam(defaultValue = "latest") String sort
    ) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return TtoklipResponse.ok(
                townFacade.getCommunities(criteria, pageable, currentMemberId, sort)
        );
    }

    @Override
    @GetMapping("/cart")
    public TtoklipResponse<CartPaging> getCarts(
            final @RequestParam(defaultValue = "0") int page,
            final @RequestParam(defaultValue = "100") Long startMoney,
            final @RequestParam(defaultValue = "100000000") Long lastMoney,
            final @RequestParam(defaultValue = "1") Long startParty,
            final @RequestParam(defaultValue = "500000") Long lastParty,
            final @RequestParam(defaultValue = "CITY") String criteria
    ) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return TtoklipResponse.ok(
                townFacade.getCarts(pageable, startMoney, lastMoney, startParty, lastParty, criteria, currentMemberId)
        );
    }

}
