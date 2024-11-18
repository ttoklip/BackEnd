package com.batch.todolist;

import com.batch.annotation.BatchPublisher;
import com.batch.global.common.TimeZones;
import com.batch.todolist.event.PickPersonalToDoListEvent;
import com.common.config.event.Events;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@BatchPublisher
public class ToDoListBatchPublisher {

    private static final String CRON_DAILY_AT_00_00_21 = "21 0 0 * * *";

    @Scheduled(cron = CRON_DAILY_AT_00_00_21, zone = TimeZones.SEOUL)
    public void generateTodayToDoList() {
        Events.raise(new PickPersonalToDoListEvent(LocalDateTime.now()));
        log.info("Today ToDoList 배치 작업 완료");
    }

}
