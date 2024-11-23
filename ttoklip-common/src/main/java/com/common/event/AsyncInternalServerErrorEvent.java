package com.common.event;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class AsyncInternalServerErrorEvent extends ErrorEvent {

    private final Throwable throwable;

    public AsyncInternalServerErrorEvent(final LocalDateTime errorTime, final Throwable throwable) {
        super(errorTime);
        this.throwable = throwable;
    }
}
