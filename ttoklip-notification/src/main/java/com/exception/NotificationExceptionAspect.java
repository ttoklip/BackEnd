package com.exception;

import com.common.config.event.Events;
import com.common.event.InternalServerExceptionEvent;
import com.common.event.Modules;
import com.common.exception.ErrorRootFinder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Aspect
@Component
public class NotificationExceptionAspect {

    @Pointcut("execution(* com.notification..*(..))")
    public void notificationExceptionScope() {
    }

    @AfterThrowing(pointcut = "notificationExceptionScope()", throwing = "exception")
    public void handleBatchException(Throwable exception) {
        Modules modules = ErrorRootFinder.determineModuleFromException(exception);
        alertErrorEvent(exception, modules);
    }

    private void alertErrorEvent(Throwable e, Modules module) {
        log.error("Notification Module Alert ErrorEvent", e);
        Events.raise(new InternalServerExceptionEvent(LocalDateTime.now(), e, module));
    }
}
