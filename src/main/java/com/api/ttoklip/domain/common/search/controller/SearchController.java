package com.api.ttoklip.domain.common.search.controller;

import com.api.ttoklip.domain.common.search.response.HoneyTipPaging;
import com.api.ttoklip.domain.common.search.response.NewsletterPaging;
import com.api.ttoklip.domain.common.search.service.SearchService;
import com.api.ttoklip.global.success.SuccessResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Search", description = "Search API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/search")
public class SearchController {

    private final SearchService searchService;
    private final static int PAGE_SIZE = 10; // 페이지 당 데이터 수

    @GetMapping("/honeytip")
    public SuccessResponse<HoneyTipPaging> searchHoneyTip(@RequestParam String title, @RequestParam int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        HoneyTipPaging honeyTipPaging = searchService.honeyTipSearch(title, pageable);
        return new SuccessResponse<>(honeyTipPaging);
    }

    @GetMapping("/newsletter")
    public SuccessResponse<NewsletterPaging> searchNewsletter(@RequestParam String title, @RequestParam int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        NewsletterPaging newsletterPaging = searchService.newsletterPaging(title, pageable);
        return new SuccessResponse<>(newsletterPaging);
    }
}
