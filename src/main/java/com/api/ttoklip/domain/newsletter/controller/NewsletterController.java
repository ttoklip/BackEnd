package com.api.ttoklip.domain.newsletter.controller;

import com.api.ttoklip.domain.common.report.dto.ReportCreateRequest;
import com.api.ttoklip.domain.newsletter.constant.NewsletterResponseConstant;
import com.api.ttoklip.domain.newsletter.controller.dto.response.NewsCategoryPagingResponse;
import com.api.ttoklip.domain.newsletter.controller.dto.request.NewsletterCreateRequest;
import com.api.ttoklip.domain.newsletter.controller.dto.response.NewsletterSingleResponse;
import com.api.ttoklip.domain.newsletter.facade.NewsletterLikeFacade;
import com.api.ttoklip.domain.newsletter.facade.NewsletterPostFacade;
import com.api.ttoklip.domain.newsletter.facade.NewsletterScrapFacade;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Newsletter Post", description = "뉴스레터 게시판 API입니다.")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/newsletter/posts")
public class NewsletterController {

    private final static int PAGE_SIZE = 10; // 페이지 당 데이터 수
    private final NewsletterPostFacade newsletterPostFacade;
    private final NewsletterScrapFacade newsletterScrapFacade;
    private final NewsletterLikeFacade newsletterLikeFacade;

    /* CREATE */
    @Operation(summary = "새로운 뉴스레터 생성", description = "form/data로 새로운 뉴스레터 게시글을 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "뉴스레터 게시글 생성 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = NewsletterResponseConstant.CREATE_NEWSLETTER,
                                    description = "뉴스레터가 생성되었습니다."
                            )))})
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public SuccessResponse<Message> register(final @Validated @ModelAttribute NewsletterCreateRequest request) {
        return new SuccessResponse<>(newsletterPostFacade.register(request));
    }

    /* READ */
    @Operation(summary = "뉴스레터 게시글 조회", description = "뉴스레터 개별 게시글을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "뉴스레터 게시글 조회 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = NewsletterResponseConstant.readSingleNewsletter,
                                    description = "뉴스레터가 조회되었습니다."
                            )))})
    @GetMapping("/{postId}")
    public SuccessResponse<NewsletterSingleResponse> getSinglePost(final @PathVariable Long postId) {
        return new SuccessResponse<>(newsletterPostFacade.getSinglePost(postId));
    }

    // 카테고리별 페이징 조회
    @Operation(summary = "뉴스레터 카테고리별 페이징 조회", description = "뉴스레터 카테고리별 페이징 조회합니다.")
    @GetMapping
    public SuccessResponse<NewsCategoryPagingResponse> getPagingCategory(
            @Parameter(description = "카테고리. 유효한 값은 HOUSEWORK, RECIPE, SAFE_LIVING, WELFARE_POLICY 중 하나입니다.", required = true, example = "HOUSEWORK")
            @RequestParam final String category,

            @Parameter(description = "페이지 번호 (0부터 시작, 기본값 0)", example = "0")
            @RequestParam(required = false, defaultValue = "0") final int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        return new SuccessResponse<>(newsletterPostFacade.getPagingCategory(category, pageable));
    }

    /* REPORT */
    @Operation(summary = "뉴스레터 게시글 신고", description = "뉴스레터 ID에 해당하는 게시글을 신고합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "뉴스레터 신고 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = NewsletterResponseConstant.REPORT_NEWSLETTER,
                                    description = "뉴스레터를 신고했습니다."
                            )))})
    @PostMapping("/report/{postId}")
    public SuccessResponse<Message> report(final @PathVariable Long postId,
                                           final @RequestBody ReportCreateRequest request) {
        Message message = newsletterPostFacade.report(postId, request);
        return new SuccessResponse<>(message);
    }

    /* LIKE */
    @Operation(summary = "뉴스레터 좋아요 추가", description = "뉴스레터 ID에 해당하는 게시글에 좋아요를 추가합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "좋아요 추가 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = NewsletterResponseConstant.REGISTER_LIKE,
                                    description = "뉴스레터에 좋아요를 추가했습니다."
                            )))})
    @PostMapping("/like/{postId}")
    public SuccessResponse<Message> registerLike(final @PathVariable Long postId) {
        Message message = newsletterLikeFacade.register(postId);
        return new SuccessResponse<>(message);
    }

    @Operation(summary = "뉴스레터 좋아요 취소", description = "뉴스레터 ID에 해당하는 게시글에 좋아요를 취소합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "좋아요 취소 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = NewsletterResponseConstant.CANCEL_LIKE,
                                    description = "뉴스레터에 좋아요를 취소했습니다."
                            )))})
    @DeleteMapping("/like/{postId}")
    public SuccessResponse<Message> cancelLike(final @PathVariable Long postId) {
        Message message = newsletterLikeFacade.cancel(postId);
        return new SuccessResponse<>(message);
    }

    /* SCRAP */
    @Operation(summary = "뉴스레터 스크랩 추가", description = "뉴스레터 ID에 해당하는 게시글에 스크랩을 추가합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "스크랩 추가 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = NewsletterResponseConstant.REGISTER_SCRAP,
                                    description = "뉴스레터에 스크랩을 추가했습니다."
                            )))})
    @PostMapping("/scrap/{postId}")
    public SuccessResponse<Message> registerScrap(final @PathVariable Long postId) {
        Message message = newsletterScrapFacade.registerScrap(postId);
        return new SuccessResponse<>(message);
    }

    @Operation(summary = "뉴스레터 스크랩 취소", description = "뉴스레터 ID에 해당하는 게시글에 스크랩을 취소합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "스크랩 취소 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = NewsletterResponseConstant.CANCEL_SCRAP,
                                    description = "뉴스레터에 스크랩을 취소했습니다."
                            )))})
    @DeleteMapping("/scrap/{postId}")
    public SuccessResponse<Message> cancelScrap(final @PathVariable Long postId) {
        Message message = newsletterScrapFacade.cancelScrap(postId);
        return new SuccessResponse<>(message);
    }


    /* DELETE */
    @Operation(summary = "뉴스레터 게시글 삭제", description = "뉴스레터 ID에 해당하는 게시글을 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "뉴스레터 게시글 삭제 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = NewsletterResponseConstant.DELETE_NEWSLETTER,
                                    description = "뉴스레터가 삭제되었습니다."
                            )))})
    @DeleteMapping("/{postId}")
    public SuccessResponse<Message> delete(final @PathVariable Long postId) {
        return new SuccessResponse<>(newsletterPostFacade.delete(postId));
    }
}
