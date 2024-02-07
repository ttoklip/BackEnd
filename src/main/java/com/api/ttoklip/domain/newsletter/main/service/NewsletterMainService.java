package com.api.ttoklip.domain.newsletter.main.service;

import com.api.ttoklip.domain.common.Category;
import com.api.ttoklip.domain.newsletter.main.dto.response.LatestCategoryNewsletterRes;
import com.api.ttoklip.domain.newsletter.main.dto.response.NewsletterCategoryRes;
import com.api.ttoklip.domain.newsletter.main.dto.response.NewsletterMainRes;
import com.api.ttoklip.domain.newsletter.post.domain.Newsletter;
import com.api.ttoklip.domain.newsletter.post.domain.repository.NewsletterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsletterMainService {

    private final NewsletterRepository newsletterRepository;
    public NewsletterMainRes main() {

        // 3번. 카테고리 별 랜덤 뉴스레터 4개씩 리스트로 응답 객체 생성
        List<NewsletterCategoryRes> newsletterCategoryList = getRandomNewslettersByCategory();

        // 5번. 카테고리 별 최신 게시물 3개씩 리스트로 응답 객체 생성
        List<LatestCategoryNewsletterRes> topThreeNewsletters = getLatestNewsletters();

        // 전체 응답 객체 생성
        NewsletterMainRes newsletterMainRes = NewsletterMainRes.builder()
                .newsletterCategoryRes(newsletterCategoryList)
                .topThreeNewsletters(topThreeNewsletters)
                .build();


        // 응답 객체 리턴
        return newsletterMainRes;
    }

    private List<NewsletterCategoryRes> getRandomNewslettersByCategory() {
        // 각 카테고리별로 랜덤 뉴스레터 조회
        List<NewsletterCategoryRes> randomNewsletters = new ArrayList<>();

        // 모든 카테고리를 조회
        List<Category> categories = Arrays.asList(Category.values());
        for (Category category : categories) {
            // 카테고리별 랜덤 뉴스레터 4개 조회
            List<Newsletter> newsletters = newsletterRepository.findRandomNewslettersByCategory(category, 4);
            for (Newsletter newsletter : newsletters) {
                randomNewsletters.add(NewsletterCategoryRes.toDto(newsletter));
            }
        }
        return randomNewsletters;
    }

    private List<LatestCategoryNewsletterRes> getLatestNewsletters() {
        // 각 카테고리별 최신 뉴스레터 조회
        List<LatestCategoryNewsletterRes> latestNewsletters = new ArrayList<>();

        // 모든 카테고리를 조회
        List<Category> categories = Arrays.asList(Category.values());
        for (Category category : categories) {
            // 카테고리별 최신 뉴스레터 3개 조회
            List<Newsletter> newsletters = newsletterRepository.findLatestNewslettersByCategory(category, 3);
            for (Newsletter newsletter : newsletters) {
                latestNewsletters.add(LatestCategoryNewsletterRes.toDto(newsletter));
            }
        }

        return latestNewsletters;
    }


}
