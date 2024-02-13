package com.api.ttoklip.domain.mypage.alam.controller;

import com.api.ttoklip.domain.mypage.alam.constant.AlamConstant;
import com.api.ttoklip.domain.mypage.alam.dto.request.AlamRequest;
import com.api.ttoklip.domain.mypage.alam.service.AlamService;
import com.api.ttoklip.global.success.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "알림", description = "알림 api입니다")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/allim")
public class AlamController {
    private final AlamService alamService;
    @Operation(summary = "알림 변경", description = "알림을 변경")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "알림 변경 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = AlamConstant.allimSettingResponse,
                                    description = "알림을 변경하였습니다"
                            )))})
    @PostMapping("/allim-settings")
    public SuccessResponse<String> changeAlam(@RequestBody AlamRequest alamRequest) {
        return new SuccessResponse<>(alamService.changeAlam(alamRequest));
    }
}
