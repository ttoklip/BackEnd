package com.api.ttoklip.domain.newsletter.controller;

import com.api.ttoklip.domain.newsletter.constant.NewsletterResponseConstant;
import com.api.ttoklip.domain.newsletter.controller.dto.response.NewsletterMainResponse;
import com.api.ttoklip.domain.newsletter.facade.NewsletterMainFacade;
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

@Tag(name = "Newsletter Main", description = "메인화면 - 오늘의 랜덤 뉴스레터 4개와 최신순 10개 API입니다.")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/newsletters/posts")
public class NewsletterMainController {

    private final NewsletterMainFacade newsletterMainFacade;

    // 메인 화면 조회
    @Operation(summary = "뉴스레터 메인화면 조회", description = "뉴스레터 메인 화면을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "뉴스레터 메인 화면 조회 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = NewsletterResponseConstant.listNewsletters,
                                    description = "뉴스레터 메인 화면이 조회되었습니다."
                            )))})
    @GetMapping
    public SuccessResponse<NewsletterMainResponse> category() {
        return new SuccessResponse<>(newsletterMainFacade.getMainData());
    }

}
