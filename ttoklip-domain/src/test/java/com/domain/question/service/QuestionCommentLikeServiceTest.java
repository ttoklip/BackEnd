package com.domain.question.service;

import com.common.exception.ApiException;
import com.common.exception.ErrorType;
import com.domain.question.application.QuestionCommentLikeService;
import com.domain.question.domain.Question;
import com.domain.question.domain.QuestionComment;
import com.domain.question.domain.QuestionCommentLikeRepository;
import com.domain.question.repository.FakeQuestionCommentLikeRepository;
import comment.fixture.CommentFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import question.fixture.QuestionFixture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class QuestionCommentLikeServiceTest {

    private QuestionCommentLikeService questionCommentLikeService;
    private QuestionCommentLikeRepository questionCommentLikeRepository;

    @BeforeEach
    void setBefore() {
        questionCommentLikeRepository = new FakeQuestionCommentLikeRepository();
        questionCommentLikeService = new QuestionCommentLikeService(questionCommentLikeRepository);
    }

    // Todo 댓글 좋아요 등록, 취소 오류 해결
//    @Test
//    void 댓글에_좋아요를_등록할_수_있다() {
//        // Given
//        Long questionCommentId = 100L;
//
//        Member member = MemberFixture.일반_회원_생성1();
//        Question question = QuestionFixture.타인_질문_생성();
//        QuestionComment questionComment = CommentFixture.질문_최상위_댓글_생성(question);
//
//        // When
//        questionCommentLikeService.registerLike(questionComment, member);
//
//        // Then
//        boolean exists = questionCommentLikeRepository.existsByQuestionCommentIdAndMemberId(questionCommentId, member.getId());
//        assertThat(exists).isTrue();
//    }

//    @Test
//    void 댓글에_좋아요를_취소할_수_있다() {
//        // Given
//        Long questionCommentId = 100L;
//
//        Member member = MemberFixture.일반_회원_생성1();
//        Question question = QuestionFixture.타인_질문_생성();
//        QuestionComment questionComment = CommentFixture.질문_최상위_댓글_생성(question);
//        questionCommentLikeService.registerLike(questionComment, member);
//
//        // When
//        questionCommentLikeService.cancelLike(questionComment, member.getId());
//
//        // Then
//        boolean exists = questionCommentLikeRepository.existsByQuestionCommentIdAndMemberId(questionCommentId, member.getId());
//        assertThat(exists).isFalse();
//    }

    @Test
    void 좋아요를_누르지_않은_댓글에_좋아요_취소시_예외를_발생시킨다() {
        // Given
        Question question = QuestionFixture.타인_질문_생성();
        QuestionComment questionComment = CommentFixture.질문_최상위_댓글_생성(question);
        Long memberId = 123L;

        // When & Then
        assertThatThrownBy(() -> questionCommentLikeService.cancelLike(questionComment, memberId))
                .isInstanceOf(ApiException.class)
                .hasMessageContaining(ErrorType.LIKE_NOT_FOUND.getMessage());
    }
}
