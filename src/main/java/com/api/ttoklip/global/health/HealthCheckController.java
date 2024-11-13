package com.api.ttoklip.global.health;


@Hidden
@RestController
@RequestMapping("/health")
public class HealthCheckController {

    @GetMapping
    public ResponseEntity<String> health() {
        return ResponseEntity.ok()
                .body("ok");
    }
}
