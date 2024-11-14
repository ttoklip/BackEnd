package com.domain.honeytip.application;

import com.domain.comment.domain.CommentRepository;
import com.domain.honeytip.domain.HoneyTipComment;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HoneyTipCommentService {

    private final CommentRepository commentRepository;

    public List<HoneyTipComment> findCommentsByHoneyTipId(final Long postId) {
        return commentRepository.findCommentsByHoneyTipId(postId);
    }
}
