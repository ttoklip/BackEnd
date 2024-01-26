package com.api.ttoklip.domain.town.community.service;

import com.api.ttoklip.domain.town.community.dto.request.CommunityCreateRequest;
import com.api.ttoklip.domain.town.community.dto.request.CommunitySearchCondition;
import com.api.ttoklip.domain.town.community.dto.request.CommunityUpdateRequest;
import com.api.ttoklip.domain.town.community.dto.response.CommunityListResponse;
import com.api.ttoklip.domain.town.community.dto.response.CommunityResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;

@Service
@RequiredArgsConstructor
public class CommunityService {
    public CommunityListResponse searchCommunityPaging(final CommunitySearchCondition condition, final Pageable pageable) {
        return null;
    }

    public CommunityResponse getCommunity(final Long commId) {
        return null;
    }

    public CommunityResponse createCommunityPost(final CommunityCreateRequest request) {
        return null;
    }

    public void updateCommunityPost(final Long commId, final CommunityUpdateRequest request) {
        return;
    }

    public void deleteCommunityPost(final Long commId) {
        return;
    }
}
