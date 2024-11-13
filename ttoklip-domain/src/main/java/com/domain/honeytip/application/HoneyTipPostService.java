package com.domain.honeytip.application;

import com.common.exception.ApiException;
import com.common.exception.ErrorType;
import com.domain.common.vo.Category;
import com.domain.common.vo.CategoryPagingResponse;
import com.domain.common.vo.CategoryResponses;
import com.domain.common.vo.TitleResponse;
import com.domain.honeytip.domain.HoneyTip;
import com.domain.honeytip.domain.HoneyTipRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HoneyTipPostService {

    private final HoneyTipRepository honeytipRepository;

    public HoneyTip getHoneytip(final Long postId) {
        return honeytipRepository.findByIdActivated(postId);
    }

    public void checkEditPermission(final HoneyTip honeyTip, final Long currentMemberId) {
        Long writerId = honeyTip.getMember().getId();

        if (!writerId.equals(currentMemberId)) {
            throw new ApiException(ErrorType.UNAUTHORIZED_EDIT_POST);
        }
    }

    @Transactional
    public HoneyTip save(final HoneyTip honeyTip) {
        return honeytipRepository.save(honeyTip);
    }

    public HoneyTip findHoneyTipWithDetails(final Long postId) {
        return honeytipRepository.findHoneyTipWithDetails(postId);
    }

    public List<HoneyTip> findRecent3() {
        return honeytipRepository.findRecent3();
    }

    public CategoryPagingResponse matchCategoryPaging(final Category category, final Pageable pageable) {
        Page<HoneyTip> honeyTips = honeytipRepository.matchCategoryPaging(category, pageable);
        List<TitleResponse> data = honeyTips.stream()
                .map(TitleResponse::honeyTipFrom)
                .toList();

        return CategoryPagingResponse.builder()
                .data(data)
                .category(category)
                .totalPage(honeyTips.getTotalPages())
                .totalElements(honeyTips.getTotalElements())
                .isLast(honeyTips.isLast())
                .isFirst(honeyTips.isFirst())
                .build();
    }


    public CategoryResponses getDefaultCategoryRead() {
        List<HoneyTip> houseWorkQuestions = honeytipRepository.findHouseworkTips();
        List<HoneyTip> recipeQuestions = honeytipRepository.findRecipeTips();
        List<HoneyTip> safeLivingQuestions = honeytipRepository.findSafeLivingTips();
        List<HoneyTip> welfarePolicyQuestions = honeytipRepository.findWelfarePolicyTips();

        return CategoryResponses.builder()
                .housework(convertToTitleResponses(houseWorkQuestions))
                .cooking(convertToTitleResponses(recipeQuestions))
                .safeLiving(convertToTitleResponses(safeLivingQuestions))
                .welfarePolicy(convertToTitleResponses(welfarePolicyQuestions))
                .build();
    }

    private List<TitleResponse> convertToTitleResponses(final List<HoneyTip> honeyTips) {
        return honeyTips.stream()
                .map(TitleResponse::honeyTipFrom)
                .toList();
    }

    public List<TitleResponse> getPopularityTop5() {
        List<HoneyTip> top5HoneyTips = honeytipRepository.getPopularityTop5();
        return top5HoneyTips.stream()
                .map(TitleResponse::honeyTipFrom)
                .toList();
    }

    public Page<HoneyTip> getContain(final String keyword, final Pageable pageable, final String sort) {
        return honeytipRepository.getContain(keyword, pageable, sort);
    }
}
