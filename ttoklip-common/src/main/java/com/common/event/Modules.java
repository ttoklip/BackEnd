package com.common.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Modules {
    API("com.api."),
    BATCH("com.batch."),
    COMMON("com.common."),
    DOMAIN("com.domain"),
    INFRASTRUCTURE("com.infrastructure."),
    MONITORING("com.monitoring."),
    NOTIFICATION("com.notification."),
    UNKNOWN("ERROR"),

    ;

    private final String root;

}
