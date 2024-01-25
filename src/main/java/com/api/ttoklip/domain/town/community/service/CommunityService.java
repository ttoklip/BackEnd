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
    public CommunityListResponse searchCommPaging(final CommunitySearchCondition condition, final Pageable pageable) {
        return null;
    }

    public CommunityResponse getComm(final Long commId) {
        return null;
    }

    public CommunityResponse createCommPost(final CommunityCreateRequest request) {
        return null;
    }

    public void updateCommPost(final Long commId, final CommunityUpdateRequest request) {
        return;
    }

    public void deleteCommPost(final Long commId) {
        return;
    }
}
