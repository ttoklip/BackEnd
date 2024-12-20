package com.common.event;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public abstract class ExceptionEvent {

    private final LocalDateTime errorTime;
    private final Throwable throwable;
    private final Modules modules;
}
