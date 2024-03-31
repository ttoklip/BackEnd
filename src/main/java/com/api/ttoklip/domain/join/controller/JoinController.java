package com.api.ttoklip.domain.join.controller;

import com.api.ttoklip.domain.join.dto.JoinRequest;
import com.api.ttoklip.domain.join.service.JoinService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/join")
public class JoinController {

    private final JoinService joinService;

    public JoinController(JoinService joinService) {

        this.joinService = joinService;
    }

    @PostMapping
    public String joinProcess(JoinRequest joinRequest) {

        System.out.println(joinRequest.getJoinId());
        joinService.joinProcess(joinRequest);

        return "ok";
    }
}
