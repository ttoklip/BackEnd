package com.api.question.presentation;

import com.api.common.ReportWebCreate;
import com.api.global.support.response.Message;
import com.api.global.support.response.TtoklipResponse;
import com.api.question.presentation.dto.request.QuestionWebCreate;
import com.api.question.presentation.dto.response.QuestionResponse;
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

@Tag(name = "Question Post", description = "질문 게시글 관리 API")
@RequestMapping("/api/v1/question/post")
public interface QuestionPostControllerDocs {

    @Operation(summary = "새로운 질문 생성", description = "form/data로 새로운 질문을 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "질문 생성 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TtoklipResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = "QuestionResponseConstant.CREATE_QUESTION",
                                    description = "질문이 생성되었습니다."
                            )))})

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    TtoklipResponse<Message> register(@Validated @ModelAttribute QuestionWebCreate request);

    @Operation(summary = "질문 게시글 조회", description = "질문 ID에 해당하는 게시글을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "질문 조회 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TtoklipResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = "QuestionResponseConstant.readSingleQuestion",
                                    description = "질문이 조회되었습니다."
                            )))})

    @GetMapping("/{postId}")
    TtoklipResponse<QuestionResponse> getSinglePost(@PathVariable Long postId);

    @Operation(summary = "질문 게시글 신고", description = "질문 ID에 해당하는 게시글을 신고합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "질문 신고 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TtoklipResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = "QuestionResponseConstant.REPORT_QUESTION",
                                    description = "질문을 신고했습니다."
                            )))})

    @PostMapping("/report/{postId}")
    TtoklipResponse<Message> report(@PathVariable Long postId, @RequestBody ReportWebCreate request);
}
