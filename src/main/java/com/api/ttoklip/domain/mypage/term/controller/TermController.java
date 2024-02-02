package com.api.ttoklip.domain.mypage.term.controller;

import com.api.ttoklip.domain.mypage.main.constant.MyPageConstant;
import com.api.ttoklip.domain.mypage.term.constant.TermConstant;
import com.api.ttoklip.domain.mypage.term.dto.response.TermsResponse;
import com.api.ttoklip.domain.mypage.term.service.TermService;
import com.api.ttoklip.global.success.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "이용약관", description = "이용약관 api입니다")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/terms")
public class TermController {
    private final TermService termService;
    @Operation(summary = "이용약관 불러오기", description = "이용약관을 조회합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "이용약관 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = TermConstant.termsAndPolicyResponse,
                                    description = "이용약관을 조회했습니다"
                            )))})
    @GetMapping("/term")
    public SuccessResponse<TermsResponse> term(final String termType) {
        return new SuccessResponse<>(termService.term(termType));
    }
}
