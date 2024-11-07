package com.api.ttoklip.global.config;

import jakarta.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.concurrent.Executor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.task.DelegatingSecurityContextAsyncTaskExecutor;

@Slf4j
@EnableAsync
@Configuration
public class AsyncConfig implements AsyncConfigurer {

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);    // 스레드 풀에 항상 살아있는 최소 스레드 수
        executor.setMaxPoolSize(5);
        /*
        스레드 풀이 확장할 수 있는 최대 스레드 수
        [스레드 풀 크기 + 초과 요청을 담는 큐의 크기]가 넘는 요청이 들어온 경우, 스레드풀이 얼만큼 확장할 수 있는지.
         */
        executor.setQueueCapacity(5);   //스레드 풀에서 사용할 최대 큐의 크기
        executor.setKeepAliveSeconds(30);
        executor.setThreadNamePrefix("async-executor-");
        executor.initialize();
        return executor;
    }

    @PostConstruct
    public void initSecurityContextHolderStrategy() {
        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new CustomAsyncExceptionHandler();
    }

    @Bean
    public TaskExecutor taskExecutor() {
        return new DelegatingSecurityContextAsyncTaskExecutor(new ThreadPoolTaskExecutor());
    }

    public static class CustomAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {

        @Override
        public void handleUncaughtException(Throwable ex, Method method, Object... params) {
            // 비동기 메소드에서 발생한 예외를 로그에 남깁니다.
            log.error("Unexpected exception occurred in async method: " + method.getName(), ex);
            for (Object param : params) {
                log.error("Parameter value - " + param);
            }
        }
    }
}
