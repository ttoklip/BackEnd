package com.api.home.presentation;

import com.api.global.support.response.TtoklipResponse;
import com.api.global.util.SecurityUtil;
import com.api.home.application.HomeFacade;
import com.api.home.presentation.response.HomeCategoryAndTopQuestionsResponse;
import com.api.home.presentation.response.HomeMainResponse;
import com.domain.common.vo.CategoryPagingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class HomeController implements HomeControllerDocs {

    private final HomeFacade homeFacade;
    private static final int PAGE_SIZE = 10;

    @Override
    @GetMapping("/home")
    public TtoklipResponse<HomeMainResponse> home() {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        HomeMainResponse homeMainResponse = homeFacade.home(currentMemberId);
        return new TtoklipResponse<>(homeMainResponse);
    }

    @Override
    @GetMapping("/common/main")
    public TtoklipResponse<HomeCategoryAndTopQuestionsResponse> top5WithCategory() {
        HomeCategoryAndTopQuestionsResponse defaultCategoryRead = homeFacade.getDefaultCategoryRead();
        return new TtoklipResponse<>(defaultCategoryRead);
    }

    @Override
    @GetMapping("/common/main/question/paging")
    public TtoklipResponse<CategoryPagingResponse> questionCategoryPaging(@RequestParam String category,
                                                                          @RequestParam(required = false, defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        CategoryPagingResponse categoryPaging = homeFacade.questionCategoryPaging(category, pageable);
        return new TtoklipResponse<>(categoryPaging);
    }

    @Override
    @GetMapping("/common/main/honey-tip/paging")
    public TtoklipResponse<CategoryPagingResponse> honeyTipCategoryPaging(@RequestParam String category,
                                                                          @RequestParam(required = false, defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        CategoryPagingResponse categoryPaging = homeFacade.honeyTipCategoryPaging(category, pageable);
        return new TtoklipResponse<>(categoryPaging);
    }
}
