package com.domain.question.application;

import com.common.NotiCategory;
import com.common.annotation.SendCommentNotification;
import com.domain.comment.domain.Comment;
import com.domain.comment.domain.CommentCreate;
import com.domain.comment.domain.CommentRepository;
import com.domain.member.domain.Member;
import com.domain.question.domain.Question;
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

    // 대댓글 생성
    @SendCommentNotification(notiCategory = NotiCategory.QUESTION_CHILD_COMMENT)
    public Comment registerCommentWithParent(final CommentCreate create, final Question question,
                                             final Comment parentComment, final Member member) {
        QuestionComment newQuestionComment = QuestionComment.withParentOf(create, parentComment, question, member);
        return commentRepository.save(newQuestionComment);
    }

    // 최상위 댓글 생성
    @SendCommentNotification(notiCategory = NotiCategory.QUESTION_COMMENT)
    public Comment registerCommentOrphanage(final CommentCreate create, final Question findQuestion,
                                            final Member member) {
        QuestionComment newQuestionComment = QuestionComment.orphanageOf(create, findQuestion, member);
        return commentRepository.save(newQuestionComment);
    }

}
