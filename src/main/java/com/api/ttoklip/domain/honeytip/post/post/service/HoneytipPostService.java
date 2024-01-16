package com.api.ttoklip.domain.honeytip.post.post.service;

import com.api.ttoklip.domain.honeytip.post.post.dto.request.HoneytipCreateReq;
import com.api.ttoklip.domain.honeytip.post.post.dto.request.HoneytipEditReq;
import com.api.ttoklip.domain.honeytip.post.post.dto.response.HoneytipWithCommentRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HoneytipPostService {
    public Long register(HoneytipCreateReq request) {
        return null;
    }

    public HoneytipWithCommentRes getSinglePost(Long postId) {
        return null;
    }

    public Long edit(Long postId, HoneytipEditReq request) {
        return null;
    }

    public Long delete(Long postId) {
        return null;
    }
}
