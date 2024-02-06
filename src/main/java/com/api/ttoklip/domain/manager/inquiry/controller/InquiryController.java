package com.api.ttoklip.domain.manager.inquiry.controller;

import com.api.ttoklip.domain.manager.inquiry.constant.InquiryConstant;
import com.api.ttoklip.domain.manager.inquiry.domain.FaQ;
import com.api.ttoklip.domain.manager.inquiry.dto.request.InquiryCreateRequest;
import com.api.ttoklip.domain.manager.inquiry.service.InquiryService;
import com.api.ttoklip.global.success.Message;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "문의하기", description = "문의하기 api입니다")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/inquiry")
public class InquiryController {

    private final InquiryService inquiryService;
    @Operation(summary = "모든 FaQ 불러오기", description = "FaQ 목록을 가져옵니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "FaQ 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = InquiryConstant.faqResponse,
                                    description = "FaQ가 조회되었습니다"
                            )))})
    @GetMapping("/faq")
    public SuccessResponse<List<FaQ>> getFaQList() {
        return new SuccessResponse<>(inquiryService.getFaQList());
    }
    @Operation(summary = "문의하기", description = "문의한 내용을 전송합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "문의 전송 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = InquiryConstant.inquiryRegisterResponse,
                                    description = "문의에 성공하였습니다"
                            )))})
    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public SuccessResponse<Long> register(final @Validated @ModelAttribute InquiryCreateRequest request) {
        Long inquiryId=inquiryService.register(request);
        return new SuccessResponse<>(inquiryId);
    }
}
