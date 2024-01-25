package com.api.ttoklip.domain.honeytip.post.post.service;

import com.api.ttoklip.domain.honeytip.post.post.dto.request.HoneytipCreateReq;
import com.api.ttoklip.domain.honeytip.post.post.dto.request.HoneytipEditReq;
import com.api.ttoklip.domain.honeytip.post.post.dto.response.HoneytipWithCommentRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HoneytipPostService {
    public Long register(final HoneytipCreateReq request) {
        return null;
    }

    public HoneytipWithCommentRes getSinglePost(final Long postId) {
        return null;
    }

    public Long edit(final Long postId, final HoneytipEditReq request) {
        return null;
    }

    public Long delete(final Long postId) {
        return null;
    }
}
