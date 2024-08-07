package com.api.ttoklip.domain.term.controller;


import com.api.ttoklip.domain.term.constant.TermConstant;
import com.api.ttoklip.domain.term.dto.request.TermCreateRequest;
import com.api.ttoklip.domain.term.dto.request.TermEditRequest;
import com.api.ttoklip.domain.term.dto.response.TermAdminResponse;
import com.api.ttoklip.domain.term.dto.response.TermPaging;
import com.api.ttoklip.domain.term.service.TermService;
import com.api.ttoklip.global.success.Message;
import com.api.ttoklip.global.success.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Term", description = "이용약관 api입니다")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/admin/term")
public class TermController {
    private final static int PAGE_SIZE = 10;
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
    @GetMapping
    public SuccessResponse<TermPaging> getTermList(
            @Parameter(description = "페이지 번호 (0부터 시작, 기본값 0)", example = "0")
            @RequestParam(required = false, defaultValue = "0") final int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        TermPaging termPaging = termService.getTermList(pageable);
        return new SuccessResponse<>(termPaging);
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
    public SuccessResponse<TermAdminResponse> getSingleTerm(final @PathVariable Long termId) {
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
    public SuccessResponse<Message> edit(final @PathVariable Long termId, final @RequestBody TermEditRequest request) {
        Message message = termService.edit(termId, request);
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
    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public SuccessResponse<Message> register(final @Validated @RequestBody TermCreateRequest request) {
        Message message = termService.register(request);
        return new SuccessResponse<>(message);
    }

}
