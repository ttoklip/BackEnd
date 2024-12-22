package com.api.health.presentation;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Hidden
@RestController
public class HealthCheckController {

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok()
                .body("ok");
    }

    @GetMapping("/error")
    public ResponseEntity<String> testError() {
        throw new RuntimeException("알림 테스트 전용 오류입니다.");
    }
}
