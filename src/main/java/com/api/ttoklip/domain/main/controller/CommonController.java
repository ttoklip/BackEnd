package com.api.ttoklip.domain.main.controller;

import com.api.ttoklip.domain.question.controller.QuestionResponseConstant;
import com.api.ttoklip.domain.main.dto.response.CategoryPagingResponse;
import com.api.ttoklip.domain.main.dto.response.CommonDefaultResponse;
import com.api.ttoklip.domain.main.service.CommonService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Question & HoneyTip Main", description = "Question & HoneyTip Main API")
@RestController
@RequestMapping("/api/v1/common/main")
@RequiredArgsConstructor
public class CommonController {

    private final CommonService commonService;
    private final static int PAGE_SIZE = 10; // 페이지 당 데이터 수

    @Operation(summary = "3, 6번 인기 순위 TOP5 & 카테고리별 조회 API",
            description = "질문해요, 꿀팁공유해요 메인 진입시 꿀팁공유해요 인기 순위 TOP5와 질문해요, 꿀팁공유해요 각각 카테고리별로 10개씩 한번에 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "질문해요 메인 페이지 인기 순위 TOP5와 카테고리별 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = QuestionResponseConstant.categoryValue,
                                    description = "실제로는 인기순위 5개와 카테고리별로별로 10개씩 한번에 응답이 나갑니다."
                            )))})
    @GetMapping
    public SuccessResponse<CommonDefaultResponse> top5WithCategory() {
        CommonDefaultResponse defaultCategoryRead = commonService.getDefaultCategoryRead();
        return new SuccessResponse<>(defaultCategoryRead);
    }

    @Operation(summary = "질문해요 카테고리별 페이징 조회",
            description = "질문해요 카테고리별로 페이징 조회합니다. 카테고리가 무엇인지 필수로 보내주셔야합니다.")
    @GetMapping("/question/paging")
    public SuccessResponse<CategoryPagingResponse> questionCategoryPaging(
            @Parameter(description = "카테고리. 유효한 값은 HOUSEWORK, RECIPE, SAFE_LIVING, WELFARE_POLICY 중 하나입니다.", required = true, example = "HOUSEWORK")
            @RequestParam final String category,

            @Parameter(description = "페이지 번호 (0부터 시작, 기본값 0)", example = "0")
            @RequestParam(required = false, defaultValue = "0") final int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        CategoryPagingResponse categoryPaging = commonService.questionCategoryPaging(category, pageable);
        return new SuccessResponse<>(categoryPaging);
    }

    @Operation(summary = "꿀팁공유해요 카테고리별 페이징 조회",
            description = "꿀팁공유해요 카테고리별로 페이징 조회합니다. 카테고리가 무엇인지 필수로 보내주셔야합니다.")
    @GetMapping("/honey-tip/paging")
    public SuccessResponse<CategoryPagingResponse> honeyTipCategoryPaging(
            @Parameter(description = "카테고리. 유효한 값은 HOUSEWORK, RECIPE, SAFE_LIVING, WELFARE_POLICY 중 하나입니다.", required = true, example = "HOUSEWORK")
            @RequestParam final String category,

            @Parameter(description = "페이지 번호 (0부터 시작, 기본값 0)", example = "0")
            @RequestParam(required = false, defaultValue = "0") final int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        CategoryPagingResponse categoryPaging = commonService.honeyTipCategoryPaging(category, pageable);
        return new SuccessResponse<>(categoryPaging);
    }
}
