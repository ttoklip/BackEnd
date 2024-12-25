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

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class HomeController implements HomeControllerDocs {

    private final HomeFacade homeFacade;
    private static final int PAGE_SIZE = 10;

    @Override
    @GetMapping("/home")
    public TtoklipResponse<HomeMainResponse> home() {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return TtoklipResponse.ok(
                homeFacade.home(currentMemberId)
        );
    }

    @Override
    @GetMapping("/common/main")
    public TtoklipResponse<HomeCategoryAndTopQuestionsResponse> top5WithCategory() {
        return TtoklipResponse.ok(
                homeFacade.getDefaultCategoryRead()
        );
    }

    @Override
    @GetMapping("/common/main/question/paging")
    public TtoklipResponse<CategoryPagingResponse> questionCategoryPaging(
            final @RequestParam String category,
            final @RequestParam(required = false, defaultValue = "0") int page
    ) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        return TtoklipResponse.ok(
                homeFacade.questionCategoryPaging(category, pageable)
        );
    }

    @Override
    @GetMapping("/common/main/honey-tip/paging")
    public TtoklipResponse<CategoryPagingResponse> honeyTipCategoryPaging(
            final @RequestParam String category,
            final @RequestParam(required = false, defaultValue = "0") int page
    ) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        return TtoklipResponse.ok(
                homeFacade.honeyTipCategoryPaging(category, pageable)
        );
    }
}
