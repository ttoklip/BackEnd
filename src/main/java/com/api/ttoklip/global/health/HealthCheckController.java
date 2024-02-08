package com.api.ttoklip.global.health;


import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Hidden
@RestController
@RequestMapping("/health")
public class HealthCheckController {

    @GetMapping
    public String health() {
        return "OK";
    }
}
