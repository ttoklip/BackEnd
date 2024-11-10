package com.api.newsletter.application;

import com.api.newsletter.presentation.response.NewsletterMainResponse;
import com.domain.newsletter.application.NewsletterMainService;
import com.domain.newsletter.application.response.NewsletterCategoryResponses;
import com.domain.newsletter.application.response.RandomTitleResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NewsletterMainFacade {

    private final NewsletterMainService newsletterMainService;

    public NewsletterMainResponse getMainData() {
        List<RandomTitleResponse> randomNews = newsletterMainService.getRandomNews();
        NewsletterCategoryResponses categoryData = newsletterMainService.getCategoryData();
        return NewsletterMainResponse.of(randomNews, categoryData);
    }
}
