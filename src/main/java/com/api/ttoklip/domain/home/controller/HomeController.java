package com.api.ttoklip.domain.home.controller;

import com.api.ttoklip.domain.home.constant.HomeConstant;
import com.api.ttoklip.domain.home.response.HomeResponse;
import com.api.ttoklip.domain.home.service.HomeService;
import com.api.ttoklip.global.success.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Home Api", description = "날씨 api, 오늘의 투두리스트, 꿀팁공유해요, 뉴스레터, 소통해요 api")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/home")
public class HomeController {

    private final HomeService homeService;

    @Operation(summary = "홈 화면", description = "뉴스레터, 꿀팁공유해요, 함께해요 최신 3개, 오늘의 ToDoList 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "홈 화면 조회 성공",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = HomeConstant.HOME_RESPONSE,
                                    description = "참여자 수를 확인하였습니다."
                            )))})
    @GetMapping
    public SuccessResponse<HomeResponse> home() {
        HomeResponse homeResponse = homeService.home();
        return new SuccessResponse<>(homeResponse);
    }
}
