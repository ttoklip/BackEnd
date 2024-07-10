package com.api.ttoklip.global.config;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.concurrent.Executor;

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
}
