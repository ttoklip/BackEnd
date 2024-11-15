package com.api.term.presentation;


import com.api.global.support.response.Message;
import com.api.global.support.response.TtoklipResponse;
import com.api.global.util.SecurityUtil;
import com.api.term.application.TermFacade;
import com.domain.term.domain.TermCreate;
import com.domain.term.response.TermResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Term", description = "이용약관 api입니다")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/admin/term")
public class TermController {
    private final static int PAGE_SIZE = 10;
    private final TermFacade termFacade;

    @Operation(summary = "이용약관 불러오기", description = "이용약관을 조회합니다")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "이용약관 조회 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TtoklipResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = TermConstant.termsAndPolicyListResponse,
                                    description = "이용약관을 조회했습니다"
                            )))})
    @GetMapping
    public TtoklipResponse<TermResponses> getTermList() {
        TermResponses termResponses = termFacade.getTermList();
        return new TtoklipResponse<>(termResponses);
    }

    @Operation(summary = "이용약관 하나 불러오기", description = "이용약관을 하나 조회합니다")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "이용약관 한개 조회 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TtoklipResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = TermConstant.termsAndPolicyResponse,
                                    description = "이용약관을 한개 조회했습니다"
                            )))})
    @GetMapping("/{termId}")
    public TtoklipResponse<TermAdminResponse> getSingleTerm(final @PathVariable Long termId) {
        return new TtoklipResponse<>(termFacade.getSingleTerm(termId));
    }

    @Operation(summary = "이용약관 수정하기", description = "이용약관을 수정합니다")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "이용약관 수정 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TtoklipResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = TermConstant.updateTermsAndPolicy,
                                    description = "이용약관을 수정했습니다"
                            )))})
    @PatchMapping("/{termId}")
    public TtoklipResponse<Message> edit(final @PathVariable Long termId, final @RequestBody TermCreate request) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = termFacade.edit(termId, request, currentMemberId);
        return new TtoklipResponse<>(message);
    }

    @Operation(summary = "이용약관 생성하기", description = "이용약관을 생성합니다")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "이용약관 생성 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TtoklipResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = TermConstant.createTermAndPolicy,
                                    description = "이용약관을 생성했습니다"
                            )))})
    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public TtoklipResponse<Message> register(final @Validated @RequestBody TermCreate request) {
        Message message = termFacade.register(request);
        return new TtoklipResponse<>(message);
    }

}
