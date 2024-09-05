package com.api.ttoklip.domain.newsletter.main.service;

import com.api.ttoklip.domain.newsletter.main.dto.response.CategoryResponses;
import com.api.ttoklip.domain.newsletter.main.dto.response.NewsletterMainResponse;
import com.api.ttoklip.domain.newsletter.main.dto.response.NewsletterThumbnailResponse;
import com.api.ttoklip.domain.newsletter.main.dto.response.RandomTitleResponse;
import com.api.ttoklip.domain.newsletter.post.domain.Newsletter;
import com.api.ttoklip.domain.newsletter.post.domain.TodayNewsletter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import com.api.ttoklip.domain.newsletter.post.repository.NewsletterQueryDslRepository;
import com.api.ttoklip.domain.newsletter.post.repository.NewsletterRepository;
import com.api.ttoklip.domain.newsletter.post.repository.TodayNewsletterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NewsletterMainService {

    private final TodayNewsletterRepository todayNewsletterRepository;
    private final NewsletterRepository newsletterRepository;

    public NewsletterMainResponse getMainData() {
        List<RandomTitleResponse> randomNews = getRandomNews();
        CategoryResponses categoryData = getCategoryData();
        return NewsletterMainResponse.of(randomNews, categoryData);
    }

    private List<RandomTitleResponse> getRandomNews() {
        LocalDate today = getDayOfSeoul();
        LocalDateTime startOfDay = getSeoulStartOfDay(today);
        LocalDateTime endOfDay = getSeoulEndOfDay(today);
        List<TodayNewsletter> todayNewsletters =
                todayNewsletterRepository.findByCreatedDateBetween(startOfDay, endOfDay);
        return todayNewsletters.stream()
                .map(RandomTitleResponse::of)
                .toList();
    }

    private LocalDate getDayOfSeoul() {
        return LocalDate.now(ZoneId.of("Asia/Seoul"));
    }

    private LocalDateTime getSeoulStartOfDay(final LocalDate today) {
        return today.atStartOfDay(ZoneId.of("Asia/Seoul")).toLocalDateTime();
    }

    private LocalDateTime getSeoulEndOfDay(final LocalDate today) {
        return today.atTime(23, 59, 59).atZone(ZoneId.of("Asia/Seoul")).toLocalDateTime();
    }

    public CategoryResponses getCategoryData() {
        List<Newsletter> houseWork = newsletterRepository.getHouseWorkNewsletter10Desc();
        List<Newsletter> recipe = newsletterRepository.getRecipeNewsletter10Desc();
        List<Newsletter> safeLiving = newsletterRepository.getSafeLivingNewsletter10Desc();
        List<Newsletter> welfarePolicy = newsletterRepository.getWelfarePolicyNewsletter10Desc();

        List<NewsletterThumbnailResponse> houseWorkResponse = convertToCategoryResponse(houseWork);
        List<NewsletterThumbnailResponse> recipeResponse = convertToCategoryResponse(recipe);
        List<NewsletterThumbnailResponse> safeLivingResponse = convertToCategoryResponse(safeLiving);
        List<NewsletterThumbnailResponse> welfarePolicyResponse = convertToCategoryResponse(welfarePolicy);

        return CategoryResponses.of(houseWorkResponse, recipeResponse, safeLivingResponse, welfarePolicyResponse);
    }

    public List<NewsletterThumbnailResponse> convertToCategoryResponse(List<Newsletter> newsletters) {
        return newsletters.stream()
                .map(NewsletterThumbnailResponse::from)
                .toList();
    }


}
