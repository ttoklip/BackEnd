package com.api.ttoklip.domain.mypage.term.controller;


import com.api.ttoklip.domain.mypage.term.constant.TermConstant;
import com.api.ttoklip.domain.mypage.term.domain.Term;
import com.api.ttoklip.domain.mypage.term.dto.request.TermCreateRequest;
import com.api.ttoklip.domain.mypage.term.dto.response.TermResponse;
import com.api.ttoklip.domain.mypage.term.service.TermService;
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
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = TermConstant.termsAndPolicyListResponse,
                                    description = "이용약관을 조회했습니다"
                            )))})
    @GetMapping()
    public SuccessResponse<List<Term>> getTermList() {
        return new SuccessResponse<>(termService.getTermList());
    }
    @Operation(summary = "이용약관 하나 불러오기", description = "이용약관을 하나 조회합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "이용약관 한개 조회 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = TermConstant.termsAndPolicyResponse,
                                    description = "이용약관을 한개 조회했습니다"
                            )))})
    @GetMapping("/{termId}")
    public SuccessResponse<TermResponse> getSingleTerm(final @PathVariable Long termId) {
        return new SuccessResponse<>(termService.getSingleTerm(termId));
    }
    @Operation(summary = "이용약관 수정하기", description = "이용약관을 수정합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "이용약관 수정 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = TermConstant.updateTermsAndPolicy,
                                    description = "이용약관을 수정했습니다"
                            )))})
    @PatchMapping("/{termId}")
    public SuccessResponse<Message> edit (final @PathVariable Long termId, final TermCreateRequest request) {
        Message message = termService.edit(termId,request);
        return new SuccessResponse<>(message);
    }
    @Operation(summary = "이용약관 생성하기", description = "이용약관을 생성합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "이용약관 생성 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = TermConstant.createTermAndPolicy,
                                    description = "이용약관을 생성했습니다"
                            )))})
    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public SuccessResponse<Long> register(final @Validated @ModelAttribute TermCreateRequest request) {
        Long termId = termService.register(request);
        return new SuccessResponse<>(termId);
    }
}
