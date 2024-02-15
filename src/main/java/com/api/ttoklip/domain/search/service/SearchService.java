package com.api.ttoklip.domain.search.service;

import com.api.ttoklip.domain.search.response.CommunityPaging;
import com.api.ttoklip.domain.search.response.CommunitySingleResponse;
import com.api.ttoklip.domain.search.response.HoneyTipPaging;
import com.api.ttoklip.domain.search.response.NewsletterPaging;
import com.api.ttoklip.domain.search.response.SingleResponse;
import com.api.ttoklip.domain.honeytip.post.domain.HoneyTip;
import com.api.ttoklip.domain.honeytip.post.repository.HoneyTipSearchRepository;
import com.api.ttoklip.domain.newsletter.post.domain.Newsletter;
import com.api.ttoklip.domain.newsletter.post.repository.NewsletterSearchRepository;
import com.api.ttoklip.domain.town.community.post.entity.Community;
import com.api.ttoklip.domain.town.community.post.repository.CommunitySearchRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SearchService {

    private final HoneyTipSearchRepository honeyTipSearchRepository;
    private final NewsletterSearchRepository newsletterSearchRepository;
    private final CommunitySearchRepository communitySearchRepository;

    public HoneyTipPaging honeyTipSearch(final String keyword, final Pageable pageable) {
        Page<HoneyTip> contentPaging = honeyTipSearchRepository.getContain(keyword, pageable);

        // List<Entity>
        List<HoneyTip> contents = contentPaging.getContent();

        // Entity -> SingleResponse 반복
        List<SingleResponse> honeyTipSingleData = contents.stream()
                .map(SingleResponse::honeyTipFrom)
                .toList();

        return HoneyTipPaging.builder()
                .honeyTips(honeyTipSingleData)
                .isFirst(contentPaging.isFirst())
                .isLast(contentPaging.isLast())
                .totalElements(contentPaging.getTotalElements())
                .totalPage(contentPaging.getTotalPages())
                .build();
    }

    public NewsletterPaging newsletterPaging(final String keyword, final Pageable pageable) {
        Page<Newsletter> contentPaging = newsletterSearchRepository.getContain(keyword, pageable);

        // List<Entity>
        List<Newsletter> contents = contentPaging.getContent();

        // Entity -> SingleResponse 반복
        List<SingleResponse> newsletterSingleData = contents.stream()
                .map(SingleResponse::newsletterFrom)
                .toList();

        return NewsletterPaging.builder()
                .newsletters(newsletterSingleData)
                .isFirst(contentPaging.isFirst())
                .isLast(contentPaging.isLast())
                .totalElements(contentPaging.getTotalElements())
                .totalPage(contentPaging.getTotalPages())
                .build();
    }

    public CommunityPaging communityPaging(final String keyword, final Pageable pageable) {
        Page<Community> contentPaging = communitySearchRepository.getContain(keyword, pageable);

        // List<Entity>
        List<Community> contents = contentPaging.getContent();

        // Entity -> SingleResponse 반복
        List<CommunitySingleResponse> communitySingleData = contents.stream()
                .map(CommunitySingleResponse::from)
                .toList();

        return CommunityPaging.builder()
                .communities(communitySingleData)
                .isFirst(contentPaging.isFirst())
                .isLast(contentPaging.isLast())
                .totalElements(contentPaging.getTotalElements())
                .totalPage(contentPaging.getTotalPages())
                .build();
    }
}
