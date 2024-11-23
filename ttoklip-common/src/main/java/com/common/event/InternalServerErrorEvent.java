package com.common.event;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class InternalServerErrorEvent extends ErrorEvent {

    private final Exception exception;

    public InternalServerErrorEvent(final LocalDateTime errorTime, final Exception exception) {
        super(errorTime);
        this.exception = exception;
    }
}
