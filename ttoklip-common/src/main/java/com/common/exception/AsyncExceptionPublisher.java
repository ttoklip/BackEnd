package com.common.exception;

import com.common.config.event.Events;
import com.common.event.AsyncInternalServerExceptionEvent;
import com.common.event.Modules;
import java.time.LocalDateTime;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AsyncExceptionPublisher {

    public void send(Throwable ex, String methodName, Object... params) {
        Modules module = ErrorRootFinder.determineModuleFromException(ex);

        log.error("Unexpected exception occurred in async method: {}", methodName, ex);

        Arrays.stream(params)
                .forEach(param -> log.error("Parameter value - {}", param));

        Events.raise(new AsyncInternalServerExceptionEvent(LocalDateTime.now(), ex, module));
    }
}