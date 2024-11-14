//package com.api.question.facade;
//
//import com.api.ttoklip.domain.question.facade.QuestionCommentLikeFacade;
//import com.api.ttoklip.global.success.Message;
//import comment.fixture.CommentFixture;
//import member.fixture.MemberFixture;
//import org.junit.jupiter.api.DisplayNameGeneration;
//import org.junit.jupiter.api.DisplayNameGenerator;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import question.fixture.QuestionFixture;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
//public class QuestionCommentLikeFacadeTest extends QuestionFacadeTestHelper {
//
//    @InjectMocks
//    private QuestionCommentLikeFacade questionCommentLikeFacade;
//
//    /* -------------------------------------------- LIKE REGISTER TEST -------------------------------------------- */
//    @Test
//    void 댓글_좋아요_등록_메서드_호출_성공() {
//        // Given
//        var member = MemberFixture.일반_회원_생성1();
//        var question = QuestionFixture.본인_질문_생성(member);
//        var comment = CommentFixture.질문_최상위_댓글_생성(question);
//        Long commentId = comment.getId();
//        Long memberId = member.getId();
//
//        // 좋아요가 존재하지 않는 경우 Mock 설정
//        when(questionCommentLikeService.isCommentLikeExists(commentId, memberId)).thenReturn(false);
//        when(questionPostService.getQuestionComment(commentId)).thenReturn(comment);
//        when(memberService.findById(memberId)).thenReturn(member);
//
//        // When
//        Message result = questionCommentLikeFacade.registerLike(commentId, memberId);
//
//        // Then
//        assertThat(result).isNotNull();
//        assertThat(result.getMessage()).contains("좋아요");
//        assertThat(result.getMessage()).contains("생성");
//
//        verify(questionCommentLikeService, times(1)).registerLike(comment, member);
//        verify(questionPostService, times(1)).getQuestionComment(commentId);
//        verify(memberService, times(1)).findById(memberId);
//    }
//
//    @Test
//    void 이미_존재하는_좋아요는_등록하지_않는_메서드_호출_성공() {
//        // Given
//        var member = MemberFixture.일반_회원_생성1();
//        var question = QuestionFixture.본인_질문_생성(member);
//        var comment = CommentFixture.질문_최상위_댓글_생성(question);
//        Long commentId = comment.getId();
//        Long memberId = member.getId();
//
//        // 이미 좋아요가 존재하는 경우 Mock 설정
//        when(questionCommentLikeService.isCommentLikeExists(commentId, memberId)).thenReturn(true);
//
//        // When
//        Message result = questionCommentLikeFacade.registerLike(commentId, memberId);
//
//        // Then
//        assertThat(result).isNotNull();
//        assertThat(result.getMessage()).contains("좋아요");
//
//        verify(questionCommentLikeService, times(0)).registerLike(comment, member); // 좋아요 등록 메서드는 호출되지 않음
//    }
//
//    /* -------------------------------------------- LIKE CANCEL TEST -------------------------------------------- */
//    @Test
//    void 댓글_좋아요_취소_메서드_호출_성공() {
//        // Given
//        var member = MemberFixture.일반_회원_생성1();
//        var question = QuestionFixture.본인_질문_생성(member);
//        var comment = CommentFixture.질문_최상위_댓글_생성(question);
//        Long commentId = comment.getId();
//        Long memberId = member.getId();
//
//        when(questionPostService.getQuestionComment(commentId)).thenReturn(comment);
//
//        // When
//        Message result = questionCommentLikeFacade.cancelLike(commentId, memberId);
//
//        // Then
//        assertThat(result).isNotNull();
//        assertThat(result.getMessage()).contains("좋아요");
//        assertThat(result.getMessage()).contains("삭제");
//
//        verify(questionCommentLikeService, times(1)).cancelLike(comment, memberId);
//        verify(questionPostService, times(1)).getQuestionComment(commentId);
//    }
//}
