package com.api.question.presentation;

import com.api.common.ReportWebCreate;
import com.api.global.support.response.Message;
import com.api.global.support.response.TtoklipResponse;
import com.api.global.util.SecurityUtil;
import com.api.question.application.QuestionPostFacade;
import com.api.question.presentation.dto.request.QuestionWebCreate;
import com.api.question.presentation.dto.response.QuestionResponse;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Question Post", description = "꿀팁공유해요 게시판 API입니다.")
@RestController
@RequestMapping("/api/v1/question/post")
@RequiredArgsConstructor
public class QuestionPostController {

    private final QuestionPostFacade questionPostFacade;

    /* CREATE */
    @Operation(summary = "새로운 질문 생성", description = "form/data로 새로운 질문을 생성합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "질문 생성 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TtoklipResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = QuestionResponseConstant.CREATE_QUESTION,
                                    description = "질문이 생성되었습니다."
                            )))})
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public TtoklipResponse<Message> register(final @Validated @ModelAttribute QuestionWebCreate request) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = questionPostFacade.register(request, currentMemberId);
        return new TtoklipResponse<>(message);
    }


    /* READ */
    @Operation(summary = "질문 게시글 조회", description = "질문 ID에 해당하는 게시글을 조회합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "질문 조회 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TtoklipResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = QuestionResponseConstant.readSingleQuestion,
                                    description = "질문이 조회되었습니다."
                            )))})
    @GetMapping("/{postId}")
    public TtoklipResponse<QuestionResponse> getSinglePost(final @PathVariable Long postId) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        QuestionResponse response = questionPostFacade.getSinglePost(postId, currentMemberId);
        return new TtoklipResponse<>(response);
    }


    /* REPORT */
    @Operation(summary = "질문 게시글 신고", description = "질문 ID에 해당하는 게시글을 신고합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "질문 신고 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TtoklipResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = QuestionResponseConstant.REPORT_QUESTION,
                                    description = "질문을 신고했습니다."
                            )))})
    @PostMapping("/report/{postId}")
    public TtoklipResponse<Message> report(final @PathVariable Long postId,
                                           final @RequestBody ReportWebCreate request) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = questionPostFacade.report(postId, request, currentMemberId);
        return new TtoklipResponse<>(message);
    }
}
