package com.api.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/test")
public class SampleController {

    // 정상 요청 테스트
    @GetMapping("/logging")
    public ResponseEntity<String> testLogging() {
        return ResponseEntity.ok("Logging Aspect Test Successful!");
    }

    // 오류 발생 테스트
    @GetMapping("/error")
    public ResponseEntity<String> testError() {
        // 예외를 강제로 발생
        throw new IllegalArgumentException("강제로 발생시킨 오류입니다!");
    }
}
