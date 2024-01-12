package com.api.ttoklip.domain.town.service;

import com.api.ttoklip.domain.town.dto.request.CommCreateRequest;
import com.api.ttoklip.domain.town.dto.request.CommSearchCondition;
import com.api.ttoklip.domain.town.dto.request.CommUpdateRequest;
import com.api.ttoklip.domain.town.dto.response.CommListResponse;
import com.api.ttoklip.domain.town.dto.response.CommResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;

@Service
@RequiredArgsConstructor
public class CommService {
    public CommListResponse searchCommPaging(CommSearchCondition condition, Pageable pageable) {
        return null;
    }

    public CommResponse getComm(Long commId) {
        return null;
    }

    public CommResponse createCommPost(CommCreateRequest request) {
        return null;
    }

    public void updateCommPost(Long commId, CommUpdateRequest request) {
        return;
    }

    public void deleteCommPost(Long commId) {
        return;
    }
}
