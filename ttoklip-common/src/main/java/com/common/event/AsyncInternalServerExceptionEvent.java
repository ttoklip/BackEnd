package com.common.event;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class AsyncInternalServerExceptionEvent extends ExceptionEvent {

    public AsyncInternalServerExceptionEvent(final LocalDateTime errorTime, final Throwable throwable,
                                             final Modules modules) {
        super(errorTime, throwable, modules);
    }
}
