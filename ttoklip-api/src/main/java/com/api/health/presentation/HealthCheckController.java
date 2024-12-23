package com.api.health.presentation;

import com.api.health.application.TestService;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Hidden
@RestController
@RequiredArgsConstructor
public class HealthCheckController {

    private final TestService testService;

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok()
                .body("ok");
    }

    @GetMapping("/error/async")
    public ResponseEntity<String> testAsyncError() {
        testService.testAsync();
        return ResponseEntity.ok()
                .body("비동기 오류 발생 테스트 완료");
    }

    @GetMapping("/error/sync")
    public ResponseEntity<String> testSyncError() {
        throw new RuntimeException("알림 테스트 전용 오류입니다.");
    }

}
