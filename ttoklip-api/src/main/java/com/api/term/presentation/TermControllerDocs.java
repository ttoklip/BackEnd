package com.api.term.presentation;

import com.api.global.support.response.Message;
import com.api.global.support.response.TtoklipResponse;
import com.domain.term.response.TermResponses;
import com.domain.term.domain.TermCreate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Term", description = "이용약관 관리 API")
@RequestMapping("/api/v1/admin/term")
public interface TermControllerDocs {

    @Operation(summary = "이용약관 조회", description = "이용약관 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "이용약관 조회 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TtoklipResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = "TermConstant.termsAndPolicyListResponse",
                                    description = "이용약관을 조회했습니다."
                            )))})
    @GetMapping
    TtoklipResponse<TermResponses> getTermList();

    @Operation(summary = "이용약관 단건 조회", description = "이용약관을 하나 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "이용약관 단건 조회 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TtoklipResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = "TermConstant.termsAndPolicyResponse",
                                    description = "이용약관을 단건 조회했습니다."
                            )))})
    @GetMapping("/{termId}")
    TtoklipResponse<TermAdminResponse> getSingleTerm(@PathVariable Long termId);

    @Operation(summary = "이용약관 수정", description = "이용약관을 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "이용약관 수정 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TtoklipResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = "TermConstant.updateTermsAndPolicy",
                                    description = "이용약관을 수정했습니다."
                            )))})
    @PatchMapping("/{termId}")
    TtoklipResponse<Message> edit(@PathVariable Long termId, @RequestBody TermCreate request);

    @Operation(summary = "이용약관 생성", description = "이용약관을 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "이용약관 생성 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TtoklipResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = "TermConstant.createTermAndPolicy",
                                    description = "이용약관을 생성했습니다."
                            )))})
    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    TtoklipResponse<Message> register(@Validated @RequestBody TermCreate request);
}
