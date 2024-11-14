package com.api.home.presentation;

import com.api.global.support.response.TtoklipResponse;
import com.api.global.util.SecurityUtil;
import com.api.home.application.HomeFacade;
import com.api.home.presentation.response.HomeCategoryAndTopQuestionsResponse;
import com.api.home.presentation.response.HomeMainResponse;
import com.api.home.presentation.response.vo.HomeConstant;
import com.api.question.presentation.QuestionResponseConstant;
import com.domain.common.vo.CategoryPagingResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Home Api", description = "오늘의 투두리스트, 꿀팁공유해요, 뉴스레터, 소통해요 api, Question & HoneyTip Main")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class HomeController {

    private final HomeFacade homeFacade;

    private final static int PAGE_SIZE = 10; // 페이지 당 데이터 수

    @Operation(summary = "홈 화면", description = "뉴스레터, 꿀팁공유해요, 함께해요 최신 3개, 오늘의 ToDoList 조회")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "홈 화면 조회 성공",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TtoklipResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = HomeConstant.HOME_RESPONSE,
                                    description = "참여자 수를 확인하였습니다."
                            )))})
    @GetMapping("/home")
    public TtoklipResponse<HomeMainResponse> home() {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        HomeMainResponse homeMainResponse = homeFacade.home(currentMemberId);
        return new TtoklipResponse<>(homeMainResponse);
    }

    @Operation(summary = "3, 6번 인기 순위 TOP5 & 카테고리별 조회 API",
            description = "질문해요, 꿀팁공유해요 메인 진입시 꿀팁공유해요 인기 순위 TOP5와 질문해요, 꿀팁공유해요 각각 카테고리별로 10개씩 한번에 조회합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "질문해요 메인 페이지 인기 순위 TOP5와 카테고리별 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TtoklipResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = QuestionResponseConstant.categoryValue,
                                    description = "실제로는 인기순위 5개와 카테고리별로별로 10개씩 한번에 응답이 나갑니다."
                            )))})
    @GetMapping("/common/main")
    public TtoklipResponse<HomeCategoryAndTopQuestionsResponse> top5WithCategory() {
        HomeCategoryAndTopQuestionsResponse defaultCategoryRead = homeFacade.getDefaultCategoryRead();
        return new TtoklipResponse<>(defaultCategoryRead);
    }

    @Operation(summary = "질문해요 카테고리별 페이징 조회",
            description = "질문해요 카테고리별로 페이징 조회합니다. 카테고리가 무엇인지 필수로 보내주셔야합니다.")
    @GetMapping("/common/main/question/paging")
    public TtoklipResponse<CategoryPagingResponse> questionCategoryPaging(
            @Parameter(description = "카테고리. 유효한 값은 HOUSEWORK, RECIPE, SAFE_LIVING, WELFARE_POLICY 중 하나입니다.", required = true, example = "HOUSEWORK")
            @RequestParam final String category,

            @Parameter(description = "페이지 번호 (0부터 시작, 기본값 0)", example = "0")
            @RequestParam(required = false, defaultValue = "0") final int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        CategoryPagingResponse categoryPaging = homeFacade.questionCategoryPaging(category, pageable);
        return new TtoklipResponse<>(categoryPaging);
    }

    @Operation(summary = "꿀팁공유해요 카테고리별 페이징 조회",
            description = "꿀팁공유해요 카테고리별로 페이징 조회합니다. 카테고리가 무엇인지 필수로 보내주셔야합니다.")
    @GetMapping("/common/main/honey-tip/paging")
    public TtoklipResponse<CategoryPagingResponse> honeyTipCategoryPaging(
            @Parameter(description = "카테고리. 유효한 값은 HOUSEWORK, RECIPE, SAFE_LIVING, WELFARE_POLICY 중 하나입니다.", required = true, example = "HOUSEWORK")
            @RequestParam final String category,

            @Parameter(description = "페이지 번호 (0부터 시작, 기본값 0)", example = "0")
            @RequestParam(required = false, defaultValue = "0") final int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        CategoryPagingResponse categoryPaging = homeFacade.honeyTipCategoryPaging(category, pageable);
        return new TtoklipResponse<>(categoryPaging);
    }
}
