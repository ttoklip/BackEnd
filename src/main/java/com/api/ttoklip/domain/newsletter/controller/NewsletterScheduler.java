package com.api.ttoklip.domain.newsletter.controller;

import com.api.ttoklip.domain.newsletter.domain.Newsletter;
import com.api.ttoklip.domain.newsletter.domain.TodayNewsletter;
import com.api.ttoklip.domain.newsletter.repository.TodayNewsletterRepository;
import com.api.ttoklip.domain.newsletter.service.NewsletterPostService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class NewsletterScheduler {

    private final static int PAGE_SIZE = 4; // 한 번에 가져올 뉴스레터의 개수
    private final NewsletterPostService newsletterPostService;
    private final TodayNewsletterRepository todayNewsletterRepository;

    // 매일 자정에 실행되는 스케줄링된 작업 (아시아/서울 시간대 기준)
    @Scheduled(cron = "0 1 0 * * *", zone = "Asia/Seoul") // 초, 분, 시, 일, 월, 요일
    @Transactional
    public void selectRandomNewsletter() {
        long newsletterCount = newsletterPostService.getEntityCount();
        if (newsletterCount == 0) {
            log.info("뉴스레터가 존재하지 않습니다. ");
            return;
        }

        List<Newsletter> randomNewsletters = fetchRandomNewsletters();

        randomNewsletters.forEach(this::saveTodayNewsletter);
    }

    private List<Newsletter> fetchRandomNewsletters() {
        return newsletterPostService.findRandom4ActiveNewsletters();
    }

    private void saveTodayNewsletter(final Newsletter newsletter) {
        TodayNewsletter todayNewsletter = TodayNewsletter.of(newsletter);
        todayNewsletterRepository.save(todayNewsletter);
    }

}
