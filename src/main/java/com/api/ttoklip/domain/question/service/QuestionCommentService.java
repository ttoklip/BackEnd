package com.api.ttoklip.domain.question.service;

import com.api.ttoklip.domain.common.comment.repository.CommentRepository;
import com.api.ttoklip.domain.question.domain.QuestionComment;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuestionCommentService {

    private final CommentRepository commentRepository;

    /* -------------------------------------------- READ -------------------------------------------- */

    public QuestionComment findQuestionComment(final Long commentId) {
        return commentRepository.findQuestionCommentWithWriterByCommentId(commentId);
    }

    /* -------------------------------------------- READ 끝 -------------------------------------------- */

    public List<QuestionComment> findQuestionCommentsByQuestionId(final Long questionId) {
        return commentRepository.findQuestionCommentsByQuestionId(questionId);
    }

}
