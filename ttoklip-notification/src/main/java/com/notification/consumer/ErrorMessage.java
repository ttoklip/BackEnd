package com.notification.consumer;

import com.common.event.Modules;
import java.time.LocalDateTime;

public record ErrorMessage(
        LocalDateTime errorTime,
        Modules modules,
        String throwableMessage,
        String stackTrace,
        boolean isSyncError
) { }