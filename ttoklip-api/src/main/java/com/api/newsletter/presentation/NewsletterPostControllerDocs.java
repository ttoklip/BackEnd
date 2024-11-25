package com.api.newsletter.presentation;

import com.api.common.ReportWebCreate;
import com.api.global.support.response.Message;
import com.api.global.support.response.TtoklipResponse;
import com.api.newsletter.presentation.response.NewsCategoryPagingResponse;
import com.api.newsletter.presentation.response.NewsletterSingleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Newsletter Post", description = "뉴스레터 게시판 API")
public interface NewsletterPostControllerDocs {

    @Operation(summary = "새로운 뉴스레터 생성", description = "form/data로 새로운 뉴스레터 게시글을 생성합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "뉴스레터 게시글 생성 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TtoklipResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = "NewsletterResponseConstant.CREATE_NEWSLETTER",
                                    description = "뉴스레터가 생성되었습니다."
                            )))})
    TtoklipResponse<Message> register(@Validated @ModelAttribute NewsletterWebCreate request);

    @Operation(summary = "뉴스레터 게시글 조회", description = "뉴스레터 개별 게시글을 조회합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "뉴스레터 게시글 조회 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TtoklipResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = "NewsletterResponseConstant.READ_SINGLE_NEWSLETTER",
                                    description = "뉴스레터가 조회되었습니다."
                            )))})
    TtoklipResponse<NewsletterSingleResponse> getSinglePost(@PathVariable Long postId);

    @Operation(summary = "뉴스레터 카테고리별 페이징 조회", description = "뉴스레터 카테고리별 페이징 조회합니다.")
    TtoklipResponse<NewsCategoryPagingResponse> getPagingCategory(
            @Parameter(description = "카테고리. 유효한 값은 HOUSEWORK, RECIPE, SAFE_LIVING, WELFARE_POLICY 중 하나입니다.", required = true, example = "HOUSEWORK")
            @RequestParam String category,
            @Parameter(description = "페이지 번호 (0부터 시작, 기본값 0)", example = "0")
            @RequestParam(required = false, defaultValue = "0") int page);

    @Operation(summary = "뉴스레터 게시글 신고", description = "뉴스레터 ID에 해당하는 게시글을 신고합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "뉴스레터 신고 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TtoklipResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = "NewsletterResponseConstant.REPORT_NEWSLETTER",
                                    description = "뉴스레터를 신고했습니다."
                            )))})
    TtoklipResponse<Message> report(@PathVariable Long postId, @RequestBody ReportWebCreate request);

    @Operation(summary = "뉴스레터 좋아요 추가", description = "뉴스레터 ID에 해당하는 게시글에 좋아요를 추가합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "좋아요 추가 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TtoklipResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = "NewsletterResponseConstant.REGISTER_LIKE",
                                    description = "뉴스레터에 좋아요를 추가했습니다."
                            )))})
    TtoklipResponse<Message> registerLike(@PathVariable Long postId);

    @Operation(summary = "뉴스레터 좋아요 취소", description = "뉴스레터 ID에 해당하는 게시글에 좋아요를 취소합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "좋아요 취소 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TtoklipResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = "NewsletterResponseConstant.CANCEL_LIKE",
                                    description = "뉴스레터에 좋아요를 취소했습니다."
                            )))})
    TtoklipResponse<Message> cancelLike(@PathVariable Long postId);

    @Operation(summary = "뉴스레터 스크랩 추가", description = "뉴스레터 ID에 해당하는 게시글에 스크랩을 추가합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "스크랩 추가 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TtoklipResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = "NewsletterResponseConstant.REGISTER_SCRAP",
                                    description = "뉴스레터에 스크랩을 추가했습니다."
                            )))})
    TtoklipResponse<Message> registerScrap(@PathVariable Long postId);

    @Operation(summary = "뉴스레터 스크랩 취소", description = "뉴스레터 ID에 해당하는 게시글에 스크랩을 취소합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "스크랩 취소 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TtoklipResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = "NewsletterResponseConstant.CANCEL_SCRAP",
                                    description = "뉴스레터에 스크랩을 취소했습니다."
                            )))})
    TtoklipResponse<Message> cancelScrap(@PathVariable Long postId);

    @Operation(summary = "뉴스레터 게시글 삭제", description = "뉴스레터 ID에 해당하는 게시글을 삭제합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "뉴스레터 게시글 삭제 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TtoklipResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = "NewsletterResponseConstant.DELETE_NEWSLETTER",
                                    description = "뉴스레터가 삭제되었습니다."
                            )))})
    TtoklipResponse<Message> delete(@PathVariable Long postId);
}
