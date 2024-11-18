package com.batch.newsletter;

import com.batch.annotation.BatchService;
import com.batch.newsletter.event.PickRandomNewsletterEvent;
import com.domain.newsletter.application.NewsletterPostService;
import com.domain.newsletter.application.TodayNewsletterService;
import com.domain.newsletter.domain.Newsletter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@BatchService
@RequiredArgsConstructor
public class NewsletterBatchService {

    private final static int SIZE = 4;
    private final NewsletterPostService newsletterPostService;
    private final TodayNewsletterService todayNewsletterService;

    @Transactional
    @EventListener(PickRandomNewsletterEvent.class)
    public void selectRandomNewsletter() {
        long newsletterCount = newsletterPostService.getEntityCount();
        if (newsletterCount == 0) {
            log.info("뉴스레터가 존재하지 않습니다. ");
            return;
        }

        List<Newsletter> randomNewsletters = newsletterPostService.findRandomActiveNewsletters(SIZE);
        randomNewsletters.forEach(todayNewsletterService::save);
    }
}
