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

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/search")
public class SearchController implements SearchControllerDocs {

    private static final int PAGE_SIZE = 10; // 페이지 당 데이터 수
    private final SearchFacade searchFacade;

    @Override
    @GetMapping("/honeytip")
    public TtoklipResponse<HoneyTipPaging> searchHoneyTip(@RequestParam String title,
                                                          @RequestParam String sort,
                                                          @RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        HoneyTipPaging honeyTipPaging = searchFacade.honeyTipSearch(title, pageable, sort);
        return new TtoklipResponse<>(honeyTipPaging);
    }

    @Override
    @GetMapping("/newsletter")
    public TtoklipResponse<NewsletterPaging> searchNewsletter(@RequestParam String title,
                                                              @RequestParam String sort,
                                                              @RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        NewsletterPaging newsletterPaging = searchFacade.newsletterPaging(title, pageable, sort);
        return new TtoklipResponse<>(newsletterPaging);
    }
    
    @Override
    @GetMapping("/community")
    public TtoklipResponse<CommunityPaging> searchCommunity(@RequestParam String title,
                                                            @RequestParam String sort,
                                                            @RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        CommunityPaging communityPaging = searchFacade.communityPaging(title, pageable, sort);
        return new TtoklipResponse<>(communityPaging);
    }

    @Override
    @GetMapping("/cart")
    public TtoklipResponse<CartPaging> searchCart(@RequestParam String title,
                                                  @RequestParam String sort,
                                                  @RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        CartPaging cartPaging = searchFacade.cartPaging(title, pageable, sort);
        return new TtoklipResponse<>(cartPaging);
    }
}
