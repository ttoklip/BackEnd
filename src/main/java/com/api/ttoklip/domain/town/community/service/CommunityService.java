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
    public CommunityListResponse searchCommPaging(CommunitySearchCondition condition, Pageable pageable) {
        return null;
    }

    public CommunityResponse getComm(Long commId) {
        return null;
    }

    public CommunityResponse createCommPost(CommunityCreateRequest request) {
        return null;
    }

    public void updateCommPost(Long commId, CommunityUpdateRequest request) {
        return;
    }

    public void deleteCommPost(Long commId) {
        return;
    }
}
