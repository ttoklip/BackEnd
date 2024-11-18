package com.batch.member;

import com.batch.annotation.BatchPublisher;
import com.batch.global.common.TimeZones;
import com.batch.member.event.UpdateIndependenceDateEvent;
import com.common.config.event.Events;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@BatchPublisher
public class MemberBatchPublisher {

    private static final String CRON_EVERY_MONTH_FIRST_DAY = "1 0 0 1 * *";

    @Scheduled(cron = CRON_EVERY_MONTH_FIRST_DAY, zone = TimeZones.SEOUL)
    public void updateMemberIndependenceDate() {
        Events.raise(new UpdateIndependenceDateEvent(LocalDateTime.now()));
        log.info("독립 경력 증가 배치 작업 완료");
    }

}
