package com.api.ttoklip.domain.newsletter.facade;

import com.api.ttoklip.domain.newsletter.controller.dto.response.NewsletterCategoryResponses;
import com.api.ttoklip.domain.newsletter.controller.dto.response.NewsletterMainResponse;
import com.api.ttoklip.domain.newsletter.controller.dto.response.RandomTitleResponse;
import com.api.ttoklip.domain.newsletter.service.NewsletterMainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NewsletterMainFacade {

    private final NewsletterMainService newsletterMainService;

    public NewsletterMainResponse getMainData() {
        List<RandomTitleResponse> randomNews = newsletterMainService.getRandomNews();
        NewsletterCategoryResponses categoryData = newsletterMainService.getCategoryData();
        return NewsletterMainResponse.of(randomNews, categoryData);
    }
}
