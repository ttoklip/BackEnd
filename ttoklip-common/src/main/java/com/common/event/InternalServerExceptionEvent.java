package com.common.event;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class InternalServerExceptionEvent extends ExceptionEvent {

    public InternalServerExceptionEvent(final LocalDateTime errorTime, final Throwable throwable, final Modules modules) {
        super(errorTime, throwable, modules);
    }
}
