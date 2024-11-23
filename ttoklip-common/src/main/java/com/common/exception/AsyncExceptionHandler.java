package com.common.exception;

import java.lang.reflect.Method;
import lombok.RequiredArgsConstructor;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AsyncExceptionHandler implements AsyncUncaughtExceptionHandler {

    private final AsyncExceptionPublisher asyncExceptionPublisher;

    @Override
    public void handleUncaughtException(Throwable ex, Method method, Object... params) {
        asyncExceptionPublisher.send(ex, method.getName(), params);
    }
}