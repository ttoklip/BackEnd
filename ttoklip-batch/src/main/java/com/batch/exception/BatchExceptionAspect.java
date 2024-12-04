package com.batch.exception;

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
public class BatchExceptionAspect {

    @Pointcut("execution(* com.batch..*(..))")
    public void batchExceptionScope() {
    }

    @AfterThrowing(pointcut = "batchExceptionScope()", throwing = "exception")
    public void handleBatchException(Throwable exception) {
        Modules modules = ErrorRootFinder.determineModuleFromException(exception);
        alertErrorEvent(exception, modules);
    }

    private void alertErrorEvent(Throwable e, Modules module) {
        log.error("Batch Module Alert ErrorEvent", e);
        Events.raise(new InternalServerExceptionEvent(LocalDateTime.now(), e, module));
    }
}
