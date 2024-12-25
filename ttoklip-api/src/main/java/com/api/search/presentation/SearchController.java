package com.api.search.presentation;

import com.api.cart.presentation.dto.response.CartPaging;
import com.api.community.presentation.dto.response.CommunityPaging;
import com.api.global.support.response.TtoklipResponse;
import com.api.search.application.SearchFacade;
import com.api.search.presentation.response.HoneyTipPaging;
import com.api.search.presentation.response.NewsletterPaging;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/search")
public class SearchController implements SearchControllerDocs {

    private static final int PAGE_SIZE = 10;
    private final SearchFacade searchFacade;

    @Override
    @GetMapping("/honeytip")
    public TtoklipResponse<HoneyTipPaging> searchHoneyTip(
            final @RequestParam String title,
            final @RequestParam String sort,
            final @RequestParam(defaultValue = "0") int page
    ) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        return TtoklipResponse.created(
                searchFacade.honeyTipSearch(title, pageable, sort)
        );
    }

    @Override
    @GetMapping("/newsletter")
    public TtoklipResponse<NewsletterPaging> searchNewsletter(
            final @RequestParam String title,
            final @RequestParam String sort,
            final @RequestParam(defaultValue = "0") int page
    ) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        return TtoklipResponse.created(
                searchFacade.newsletterPaging(title, pageable, sort)
        );
    }
    
    @Override
    @GetMapping("/community")
    public TtoklipResponse<CommunityPaging> searchCommunity(
            final @RequestParam String title,
            final @RequestParam String sort,
            final @RequestParam(defaultValue = "0") int page
    ) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        return TtoklipResponse.created(
                searchFacade.communityPaging(title, pageable, sort)
        );
    }

    @Override
    @GetMapping("/cart")
    public TtoklipResponse<CartPaging> searchCart(
            final @RequestParam String title,
            final @RequestParam String sort,
            final @RequestParam(defaultValue = "0") int page
    ) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        return TtoklipResponse.created(
                searchFacade.cartPaging(title, pageable, sort)
        );
    }
}
