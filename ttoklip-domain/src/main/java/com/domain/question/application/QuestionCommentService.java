package com.domain.question.application;

import com.domain.common.comment.domain.CommentRepository;
import com.domain.question.domain.QuestionComment;
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
