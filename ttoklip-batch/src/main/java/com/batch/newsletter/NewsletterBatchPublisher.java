package com.batch.newsletter;

import com.batch.annotation.BatchPublisher;
import com.batch.global.common.TimeZones;
import com.batch.newsletter.event.PickRandomNewsletterEvent;
import com.common.config.event.Events;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@BatchPublisher
public class NewsletterBatchPublisher {

    private static final String CRON_DAILY_AT_00_00_11 = "11 0 0 * * *";

    @Scheduled(cron = CRON_DAILY_AT_00_00_11, zone = TimeZones.SEOUL)
    public void selectRandomNewsletter() {
        Events.raise(new PickRandomNewsletterEvent(LocalDateTime.now()));
        log.info("오늘의 뉴스레터 추출 배치 작업 완료");
    }

}
