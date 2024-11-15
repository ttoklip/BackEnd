//package com.api.question.facade;
//
//import com.api.ttoklip.domain.common.comment.service.CommentService;
//import com.api.ttoklip.domain.member.domain.Member;
//import com.api.ttoklip.domain.question.domain.Question;
//import com.api.ttoklip.domain.question.domain.QuestionComment;
//import com.api.ttoklip.domain.question.facade.QuestionCommentFacade;
//import com.api.ttoklip.global.success.Message;
//import comment.fixture.CommentFixture;
//import member.fixture.MemberFixture;
//import org.junit.jupiter.api.DisplayNameGeneration;
//import org.junit.jupiter.api.DisplayNameGenerator;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import question.fixture.QuestionFixture;
//import report.fixture.ReportFixture;
//
//import java.util.Optional;
//
//import static org.assertj.core.api.SoftAssertions.assertSoftly;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//@SuppressWarnings("NonAsciiCharacters")
//@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
//public class QuestionCommentFacadeTest extends QuestionFacadeTestHelper {
//
//    @InjectMocks
//    private QuestionCommentFacade questionCommentFacade;
//
//    @Mock
//    private CommentService commentService;
//
//    /* -------------------------------------------- COMMENT CREATE TEST -------------------------------------------- */
//
//    @Test
//    void 최상위_댓글_등록_메서드_호출_성공() {
//        // Given
//        Member member = MemberFixture.일반_회원_생성1();
//        Question question = QuestionFixture.본인_질문_생성(member);
//        Long postId = question.getId();
//        Long memberId = member.getId();
//        var request = CommentFixture.최상위_댓글_생성_요청();
//
//        when(commentService.findParentComment(any())).thenReturn(Optional.empty());
//        when(questionPostService.getQuestion(postId)).thenReturn(question);
//        when(memberService.findById(memberId)).thenReturn(member);
//
//        // When
//        Message result = questionCommentFacade.register(postId, request, memberId);
//
//        // Then
//        assertSoftly(softly -> {
//            softly.assertThat(result).isNotNull();
//            softly.assertThat(result.getMessage()).contains("댓글");
//            softly.assertThat(result.getMessage()).contains("생성");
//        });
//
//        verify(commentService).register(any(QuestionComment.class));
//    }
//
//    @Test
//    void 대댓글_등록_메서드_호출_성공() {
//        // Given
//        Member member = MemberFixture.일반_회원_생성1();
//        Question question = QuestionFixture.본인_질문_생성(member);
//        Long postId = question.getId();
//        Long memberId = member.getId();
//        var parentComment = CommentFixture.질문_최상위_댓글_생성(question);
//
//        var request = CommentFixture.대댓글_생성_요청(parentComment);
//
//        when(commentService.findParentComment(parentComment.getId())).thenReturn(Optional.of(parentComment));
//        when(questionPostService.getQuestion(postId)).thenReturn(question);
//        when(memberService.findById(memberId)).thenReturn(member);
//
//        // When
//        Message result = questionCommentFacade.register(postId, request, memberId);
//
//        // Then
//        assertSoftly(softly -> {
//            softly.assertThat(result).isNotNull();
//            softly.assertThat(result.getMessage()).contains("댓글");
//            softly.assertThat(result.getMessage()).contains("생성");
//        });
//
//        verify(commentService).register(any(QuestionComment.class));
//    }
//
//    /* -------------------------------------------- COMMENT CREATE TEST END -------------------------------------------- */
//
//
//    /* -------------------------------------------- COMMENT DELETE TEST -------------------------------------------- */
//    @Test
//    void 댓글_삭제_메서드_호출_성공() {
//        // Given
//        Long commentId = 100L;
//
//        // When
//        Message result = questionCommentFacade.delete(commentId);
//
//        // Then
//        assertSoftly(softly -> {
//            softly.assertThat(result).isNotNull();
//            softly.assertThat(result.getMessage()).contains("삭제");
//        });
//
//        verify(commentService).deleteById(commentId);
//    }
//
//    /* -------------------------------------------- COMMENT DELETE TEST END -------------------------------------------- */
//
//
//    /* -------------------------------------------- COMMENT REPORT TEST -------------------------------------------- */
//    @Test
//    void 댓글_신고_메서드_호출_성공() {
//        // Given
//        Long commentId = 100L;
//
//        Member member = MemberFixture.일반_회원_생성1();
//        Question question = QuestionFixture.본인_질문_생성(member);
//        var comment = CommentFixture.질문_최상위_댓글_생성(question);
//        var reportRequest = ReportFixture.신고_요청_픽스처();
//
//        when(commentService.findComment(commentId)).thenReturn(comment);
//
//        // When
//        Message result = questionCommentFacade.report(commentId, reportRequest);
//
//        // Then
//        assertSoftly(softly -> {
//            softly.assertThat(result).isNotNull();
//            softly.assertThat(result.getMessage()).contains("신고");
//        });
//
//        verify(reportService).reportComment(reportRequest, comment);
//    }
//
//    /* -------------------------------------------- COMMENT REPORT TEST END -------------------------------------------- */
//}
