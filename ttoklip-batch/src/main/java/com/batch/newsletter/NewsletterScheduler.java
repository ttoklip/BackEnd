package com.batch.newsletter;

import com.domain.newsletter.application.NewsletterPostService;
import com.domain.newsletter.application.TodayNewsletterService;
import com.domain.newsletter.domain.Newsletter;
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

    private final static int PAGE_SIZE = 4;
    private final NewsletterPostService newsletterPostService;
    private final TodayNewsletterService todayNewsletterService;

    @Scheduled(cron = "11 0 0 * * *", zone = "Asia/Seoul")
    @Transactional
    public void selectRandomNewsletter() {
        long newsletterCount = newsletterPostService.getEntityCount();
        if (newsletterCount == 0) {
            log.info("뉴스레터가 존재하지 않습니다. ");
            return;
        }

        List<Newsletter> randomNewsletters = newsletterPostService.findRandomActiveNewsletters(PAGE_SIZE);
        randomNewsletters.forEach(todayNewsletterService::save);
    }

}
