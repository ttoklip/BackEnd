package com.api.home.presentation;

import com.api.global.support.response.TtoklipResponse;
import com.api.home.presentation.response.HomeCategoryAndTopQuestionsResponse;
import com.api.home.presentation.response.HomeMainResponse;
import com.domain.common.vo.CategoryPagingResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Home", description = "홈 관련 API")
@RequestMapping("/api/v1")
public interface HomeControllerDocs {

    @Operation(summary = "홈 화면", description = "뉴스레터, 꿀팁공유해요, 함께해요 최신 3개, 오늘의 ToDoList 조회")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "홈 화면 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TtoklipResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = "HomeConstant.HOME_RESPONSE",
                                    description = "홈 화면 조회 성공"
                            )))} )
    @GetMapping("/home")
    TtoklipResponse<HomeMainResponse> home();

    @Operation(summary = "3, 6번 인기 순위 TOP5 & 카테고리별 조회", description = "질문해요, 꿀팁공유해요 메인 진입시 인기 순위 TOP5와 카테고리별 조회")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TtoklipResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = "QuestionResponseConstant.categoryValue",
                                    description = "인기 순위 5개와 카테고리별 10개씩 조회 성공"
                            )))} )
    @GetMapping("/common/main")
    TtoklipResponse<HomeCategoryAndTopQuestionsResponse> top5WithCategory();

    @Operation(summary = "질문해요 카테고리별 페이징 조회", description = "질문해요 카테고리별로 페이징 조회")
    @GetMapping("/common/main/question/paging")
    TtoklipResponse<CategoryPagingResponse> questionCategoryPaging(
            @Parameter(description = "카테고리 이름", required = true, example = "HOUSEWORK")
            @RequestParam String category,
            @Parameter(description = "페이지 번호 (0부터 시작)", example = "0")
            @RequestParam(required = false, defaultValue = "0") int page);

    @Operation(summary = "꿀팁공유해요 카테고리별 페이징 조회", description = "꿀팁공유해요 카테고리별로 페이징 조회")
    @GetMapping("/common/main/honey-tip/paging")
    TtoklipResponse<CategoryPagingResponse> honeyTipCategoryPaging(
            @Parameter(description = "카테고리 이름", required = true, example = "HOUSEWORK")
            @RequestParam String category,
            @Parameter(description = "페이지 번호 (0부터 시작)", example = "0")
            @RequestParam(required = false, defaultValue = "0") int page);
}
