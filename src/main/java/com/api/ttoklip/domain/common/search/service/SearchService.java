package com.api.ttoklip.domain.common.search.service;

import com.api.ttoklip.domain.common.search.response.HoneyTipPaging;
import com.api.ttoklip.domain.common.search.response.HoneyTipSingleResponse;
import com.api.ttoklip.domain.honeytip.post.domain.HoneyTip;
import com.api.ttoklip.domain.honeytip.post.repository.HoneyTipRepository;
import com.api.ttoklip.domain.honeytip.post.repository.HoneyTipSearchRepository;
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

    private final HoneyTipRepository honeyTipRepository;
    private final HoneyTipSearchRepository honeyTipSearchRepository;

    public HoneyTipPaging honeyTipSearch(final String keyword, final Pageable pageable) {

//        Page<HoneyTip> contentPaging = honeyTipRepository.findByContentContaining(keyword, pageable);
        Page<HoneyTip> contentPaging = honeyTipSearchRepository.getContain(keyword, pageable);

        // List<Entity>
        List<HoneyTip> contents = contentPaging.getContent();

        // Entity -> SingleResponse 반복
        List<HoneyTipSingleResponse> honeyTipSingleData = contents.stream()
                .map(HoneyTipSingleResponse::from)
                .toList();

        return HoneyTipPaging.builder()
                .honeyTips(honeyTipSingleData)
                .isFirst(contentPaging.isFirst())
                .isLast(contentPaging.isLast())
                .totalElements(contentPaging.getTotalElements())
                .totalPage(contentPaging.getTotalPages())
                .build();
    }
}
