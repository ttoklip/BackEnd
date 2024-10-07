package com.api.ttoklip.domain.honeytip.comment.service;

import com.api.ttoklip.domain.common.comment.repository.HoneyTipCommentRepository;
import com.api.ttoklip.domain.honeytip.comment.domain.HoneyTipComment;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HoneyTipCommentService {

    private final HoneyTipCommentRepository honeyTipCommentRepository;

    public List<HoneyTipComment> findCommentsByHoneyTipId(final Long honeyTipPostId) {
        return honeyTipCommentRepository.findCommentsByHoneyTipId(honeyTipPostId);
    }
}
