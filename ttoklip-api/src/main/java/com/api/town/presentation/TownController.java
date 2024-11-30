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

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/town/main")
public class TownController implements TownControllerDocs {

    private static final int PAGE_SIZE = 10;
    private final TownFacade townFacade;

    @Override
    @GetMapping
    public TtoklipResponse<TownMainResponse> getCarts(@RequestParam(defaultValue = "CITY") String criteria) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        TownMainResponse cartMainResponse = townFacade.getRecent3(criteria, currentMemberId);
        return new TtoklipResponse<>(cartMainResponse);
    }

    @Override
    @GetMapping("/community")
    public TtoklipResponse<CommunityPaging> getCommunities(@RequestParam(defaultValue = "CITY") String criteria,
                                                           @RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "latest") String sort) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return new TtoklipResponse<>(townFacade.getCommunities(criteria, pageable, currentMemberId, sort));
    }

    @Override
    @GetMapping("/cart")
    public TtoklipResponse<CartPaging> getCarts(@RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "100") Long startMoney,
                                                @RequestParam(defaultValue = "100000000") Long lastMoney,
                                                @RequestParam(defaultValue = "1") Long startParty,
                                                @RequestParam(defaultValue = "500000") Long lastParty,
                                                @RequestParam(defaultValue = "CITY") String criteria) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return new TtoklipResponse<>(townFacade.getCarts(pageable, startMoney, lastMoney, startParty, lastParty, criteria, currentMemberId));
    }
}
