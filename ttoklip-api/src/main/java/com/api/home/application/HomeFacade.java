package com.api.home.application;

import com.api.home.presentation.response.HomeCategoryAndTopQuestionsResponse;
import com.api.home.presentation.response.HomeMainResponse;
import com.domain.cart.application.CartPostService;
import com.domain.cart.application.CartThumbnailResponse;
import com.domain.common.vo.Category;
import com.domain.common.vo.CategoryPagingResponse;
import com.domain.common.vo.CategoryResponses;
import com.domain.common.vo.TitleResponse;
import com.domain.common.vo.TownCriteria;
import com.domain.honeytip.application.HoneyTipPostService;
import com.domain.honeytip.domain.HoneyTip;
import com.domain.member.application.MemberService;
import com.domain.member.domain.Member;
import com.domain.newsletter.application.NewsletterPostService;
import com.domain.newsletter.application.response.NewsletterThumbnailResponse;
import com.domain.newsletter.domain.Newsletter;
import com.domain.question.application.QuestionPostService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HomeFacade {

    private final CartPostService cartPostService;
    private final HoneyTipPostService honeyTipPostService;
    private final NewsletterPostService newsletterPostService;
    private final MemberService memberService;
    private final QuestionPostService questionPostService;

    public HomeMainResponse home(final Long currentMemberId) {
        List<TitleResponse> honeyTipRecent3 = getHoneyTipRecent3();
        List<NewsletterThumbnailResponse> newsletterRecent3 = getNewsletterThumbnailRecent3();
        List<CartThumbnailResponse> cartRecent3 = cartPostService.getRecent3(TownCriteria.CITY);

        Member member = memberService.getById(currentMemberId);
        return HomeMainResponse.builder()
                .currentMemberNickname(member.getNickname())
                .street(member.getStreet())
                .honeyTips(honeyTipRecent3)
                .newsLetters(newsletterRecent3)
                .carts(cartRecent3)
                .build();
    }

    public List<TitleResponse> getHoneyTipRecent3() {
        List<HoneyTip> recent3HoneyTip = honeyTipPostService.findRecent3();
        return recent3HoneyTip.stream()
                .map(TitleResponse::honeyTipFrom)
                .toList();
    }

    public List<NewsletterThumbnailResponse> getNewsletterThumbnailRecent3() {
        List<Newsletter> newsletters = newsletterPostService.getRecent3();

        return newsletters.stream()
                .map(NewsletterThumbnailResponse::from)
                .toList();
    }


    /* -------------------------------------------- 카토고리별 MAIN READ -------------------------------------------- */

    public HomeCategoryAndTopQuestionsResponse getDefaultCategoryRead() {
        CategoryResponses questionCategoryResponse = questionPostService.getDefaultCategoryRead();
        CategoryResponses honeyTipCategoryResponse = honeyTipPostService.getDefaultCategoryRead();
        List<TitleResponse> top5Responses = honeyTipPostService.getPopularityTop5();

        return HomeCategoryAndTopQuestionsResponse.of(questionCategoryResponse, honeyTipCategoryResponse,
                top5Responses);
    }

    /* -------------------------------------------- 카토고리별 MAIN READ 끝 -------------------------------------------- */


    /* -------------------------------------------- 카토고리별 MAIN READ - 카테고리별 페이징 -------------------------------------------- */
    public CategoryPagingResponse questionCategoryPaging(final String categoryInput, final Pageable pageable) {
        Category category = Category.findCategoryByValue(categoryInput);
        return questionPostService.matchCategoryPaging(category, pageable);
    }

    public CategoryPagingResponse honeyTipCategoryPaging(final String categoryInput, final Pageable pageable) {
        Category category = Category.findCategoryByValue(categoryInput);
        return honeyTipPostService.matchCategoryPaging(category, pageable);
    }

    /* -------------------------------------------- 카토고리별 MAIN READ - 카테고리별 페이징 끝 -------------------------------------------- */
}
