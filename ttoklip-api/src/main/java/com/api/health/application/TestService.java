package com.api.health.application;

import com.common.config.event.Events;
import com.common.event.AsyncInternalServerExceptionEvent;
import com.common.event.Modules;
import java.time.LocalDateTime;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    @Async
    public void testAsync() {
        try {
            throw new RuntimeException("비동기 알림 테스트 전용 오류입니다.");
        } catch (RuntimeException e) {
            Events.raise(new AsyncInternalServerExceptionEvent(
                    LocalDateTime.now(),
                    e,
                    Modules.API
            ));
        }
    }
}
