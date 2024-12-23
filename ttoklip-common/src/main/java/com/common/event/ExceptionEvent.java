package com.common.event;

import java.time.LocalDateTime;
import java.time.ZoneId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public abstract class ExceptionEvent {

    private final LocalDateTime errorTime;
    private final Throwable throwable;
    private final Modules modules;

    private static final String SEOUL = "Asia/Seoul";

    public LocalDateTime getErrorTimeInKST() {
        return errorTime
                .atZone(ZoneId.systemDefault())
                .withZoneSameInstant(ZoneId.of(SEOUL))
                .toLocalDateTime();
    }
}
