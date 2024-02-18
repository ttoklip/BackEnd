package com.api.ttoklip.domain.home.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Home Api", description = "날씨 api, 오늘의 투두리스트, 꿀팁공유해요, 뉴스레터, 소통해요 api")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/home")
public class HomeController {

    public void home() {

    }
}
