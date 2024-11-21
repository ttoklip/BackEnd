package com.api.global.support.logging;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.UUID;

@Aspect
@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 1)
public class RequestResponseLoggingAspect {

    private static final Logger requestLogger = LoggerFactory.getLogger("HttpRequestLog");
    private static final Logger responseLogger = LoggerFactory.getLogger("HttpResponseLog");
    private static final String HEALTH_CHECK_URI = "/health";

    // API 모듈의 모든 컨트롤러 메서드에 적용
    @Pointcut("execution(* com.api..presentation..*Controller.*(..))")
    public void apiControllerMethods() {}

    // 요청 전 로깅
    @Before("apiControllerMethods()")
    public void logRequest(JoinPoint joinPoint) {
        if (isHealthCheck()) {
            return;
        }
        setMDC();
        requestLogger.info("Request received for method: {}", joinPoint.getSignature().getName());
    }

    private boolean isHealthCheck() {
        HttpServletRequest request = getCurrentHttpRequest();
        if (request != null && HEALTH_CHECK_URI.equals(request.getRequestURI())) {
            return true;
        }
        return false;
    }

    // 정상적인 응답 반환 후 로깅
    @AfterReturning(pointcut = "apiControllerMethods()")
    public void logResponse() {
        String startTimeStr = MDC.get("startTime");
        long startTime = startTimeStr != null ? Long.parseLong(startTimeStr) : 0L;
        double executionTime = (System.nanoTime() - startTime) / 1_000_000_000.0;
        MDC.put("responseTime", String.format("%.3f초", executionTime));
        responseLogger.info("Response sent successfully");
    }

    // MDC 정리
    @After("apiControllerMethods()")
    public void clearMDC() {
        MDC.clear();
    }

    // MDC 설정
    private void setMDC() {
        HttpServletRequest request = getCurrentHttpRequest();
        if (request != null) {
            MDC.put("method", request.getMethod());
            MDC.put("requestUri", request.getRequestURI());
            MDC.put("sourceIp", request.getHeader("X-Real-IP") != null ? request.getHeader("X-Real-IP") : request.getRemoteAddr());
            MDC.put("userAgent", request.getHeader("User-Agent"));
            MDC.put("xForwardedFor", request.getHeader("X-Forwarded-For"));
            MDC.put("xForwardedProto", request.getHeader("X-Forwarded-Proto"));
            MDC.put("requestId", UUID.randomUUID().toString());
            MDC.put("startTime", String.valueOf(System.nanoTime()));
        }
    }

    // 현재 HTTP 요청 가져오기
    private HttpServletRequest getCurrentHttpRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attributes != null ? attributes.getRequest() : null;
    }
}
