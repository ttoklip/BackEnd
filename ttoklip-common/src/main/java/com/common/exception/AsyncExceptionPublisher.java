package com.common.exception;

import com.common.config.event.Events;
import com.common.event.AsyncInternalServerErrorEvent;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AsyncExceptionPublisher {

    public void send(Throwable ex, String methodName, Object... params) {
        log.error("Unexpected exception occurred in async method: {}", methodName, ex);
        for (Object param : params) {
            log.error("Parameter value - {}", param);
        }
        Events.raise(new AsyncInternalServerErrorEvent(LocalDateTime.now(), ex));
    }
}