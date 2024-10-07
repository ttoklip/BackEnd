package com.api.ttoklip.domain.newsletter.service;

import com.api.ttoklip.domain.newsletter.controller.dto.response.NewsletterCategoryResponses;
import com.api.ttoklip.domain.newsletter.controller.dto.response.NewsletterThumbnailResponse;
import com.api.ttoklip.domain.newsletter.controller.dto.response.RandomTitleResponse;
import com.api.ttoklip.domain.newsletter.domain.Newsletter;
import com.api.ttoklip.domain.newsletter.domain.TodayNewsletter;
import com.api.ttoklip.domain.newsletter.repository.NewsletterRepository;
import com.api.ttoklip.domain.newsletter.repository.TodayNewsletterRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NewsletterMainService {

    private final TodayNewsletterRepository todayNewsletterRepository;
    private final NewsletterRepository newsletterRepository;
    private static final String ZONE_ID_OF_SEOUL = "Asia/Seoul";

    public List<RandomTitleResponse> getRandomNews() {
        LocalDate today = getDayOfSeoul();
        LocalDateTime startOfDay = getSeoulStartOfDay(today);
        LocalDateTime endOfDay = getSeoulEndOfDay(today);
        List<TodayNewsletter> todayNewsletters = todayNewsletterRepository.findByCreatedDateBetween(startOfDay,
                endOfDay);
        return todayNewsletters.stream()
                .map(RandomTitleResponse::from)
                .toList();
    }

    private LocalDate getDayOfSeoul() {
        return LocalDate.now(ZoneId.of(ZONE_ID_OF_SEOUL));
    }

    private LocalDateTime getSeoulStartOfDay(final LocalDate today) {
        return today.atStartOfDay(ZoneId.of(ZONE_ID_OF_SEOUL)).toLocalDateTime();
    }

    private LocalDateTime getSeoulEndOfDay(final LocalDate today) {
        return today.atTime(23, 59, 59).atZone(ZoneId.of("Asia/Seoul")).toLocalDateTime();
    }

    public NewsletterCategoryResponses getCategoryData() {
        List<Newsletter> houseWork = newsletterRepository.getHouseWorkNewsletter10Desc();
        List<Newsletter> recipe = newsletterRepository.getRecipeNewsletter10Desc();
        List<Newsletter> safeLiving = newsletterRepository.getSafeLivingNewsletter10Desc();
        List<Newsletter> welfarePolicy = newsletterRepository.getWelfarePolicyNewsletter10Desc();

        List<NewsletterThumbnailResponse> houseWorkResponse = convertToCategoryResponse(houseWork);
        List<NewsletterThumbnailResponse> recipeResponse = convertToCategoryResponse(recipe);
        List<NewsletterThumbnailResponse> safeLivingResponse = convertToCategoryResponse(safeLiving);
        List<NewsletterThumbnailResponse> welfarePolicyResponse = convertToCategoryResponse(welfarePolicy);

        return NewsletterCategoryResponses.of(houseWorkResponse, recipeResponse, safeLivingResponse, welfarePolicyResponse);
    }

    public List<NewsletterThumbnailResponse> convertToCategoryResponse(List<Newsletter> newsletters) {
        return newsletters.stream()
                .map(NewsletterThumbnailResponse::from)
                .toList();
    }


}
