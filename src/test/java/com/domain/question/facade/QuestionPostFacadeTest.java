package com.domain.question.facade;

import com.api.ttoklip.domain.question.controller.dto.request.QuestionCreateRequest;
import com.api.ttoklip.domain.question.controller.dto.response.QuestionSingleResponse;
import com.api.ttoklip.domain.question.domain.Question;
import com.api.ttoklip.domain.question.facade.QuestionPostFacade;
import com.api.ttoklip.global.success.Message;
import member.fixture.MemberFixture;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.web.multipart.MultipartFile;
import question.fixture.QuestionFixture;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class QuestionPostFacadeTest extends QuestionFacadeTestHelper {

    @InjectMocks
    private QuestionPostFacade questionPostFacade;

    /* -------------------------------------------- CREATE METHOD CALL TEST -------------------------------------------- */

    @Test
    void 질문_게시글_생성_사진_포함_메서드_호출_성공() {
        // Given
        QuestionCreateRequest request = 사진_포함된_게시글_요청_픽스처();
        var member = MemberFixture.일반_회원_생성1();
        when(memberService.findById(member.getId())).thenReturn(member);

        // S3 업로드 결과를 모킹하여 가짜 URL 리스트 반환
        List<String> fakeUrls = Arrays.asList("http://fake-url1.com", "http://fake-url2.com");
        when(questionPostService.uploadImages(any())).thenReturn(fakeUrls);

        // When
        Message result = questionPostFacade.register(request, member.getId());

        // Then
        assertSoftly(softly -> {
            softly.assertThat(result).isNotNull();
            softly.assertThat(result.getMessage()).contains("Question", "생성");
        });

        // 이미지 리스트의 크기만큼 register 메서드 호출 횟수 검증
        int imageCount = fakeUrls.size();
        verify(questionImageService, times(imageCount)).register(any(), any());
        verify(questionPostService, times(1)).saveQuestion(any());
    }

    @Test
    void 질문_게시글_생성_사진_포함_안됨_메서드_호출_성공() {
        // Given
        QuestionCreateRequest request = 사진_없는_게시글_요청_픽스처();
        var member = MemberFixture.일반_회원_생성1();
        when(memberService.findById(member.getId())).thenReturn(member);

        // When
        Message result = questionPostFacade.register(request, member.getId());

        // Then
        assertSoftly(softly -> {
            softly.assertThat(result).isNotNull();
            softly.assertThat(result.getMessage()).contains("Question", "생성");
        });

        verify(questionPostService, times(1)).saveQuestion(any());
    }

    private QuestionCreateRequest 사진_포함된_게시글_요청_픽스처() {
        List<MultipartFile> 이미지_리스트 = List.of(mock(MultipartFile.class), mock(MultipartFile.class));
        return QuestionCreateRequest.builder()
                .title("사진이 포함된 질문")
                .content("이 질문은 사진을 포함합니다.")
                .category("HOUSEWORK")
                .images(이미지_리스트)
                .build();
    }

    private QuestionCreateRequest 사진_없는_게시글_요청_픽스처() {
        return QuestionCreateRequest.builder()
                .title("사진 없는 질문")
                .content("이 질문은 사진을 포함하지 않습니다.")
                .category("HOUSEWORK")
                .images(null)
                .build();
    }

    /* -------------------------------------------- CREATE METHOD CALL TEST END -------------------------------------------- */


    /* -------------------------------------------- 단건 READ 메서드 테스트 -------------------------------------------- */
    @Test
    void 질문_게시글_단건_조회_메서드_호출_성공() {
        // Given
        var member = MemberFixture.일반_회원_생성1();
        Question question = QuestionFixture.본인_질문_생성(member);

        when(questionPostService.findByIdFetchJoin(question.getId())).thenReturn(question);
        when(questionCommentService.findQuestionCommentsByQuestionId(question.getId())).thenReturn(List.of());
        when(questionCommentLikeService.existsByQuestionCommentIdAndMemberId(anyLong(), eq(member.getId()))).thenReturn(false);

        // When
        QuestionSingleResponse response = questionPostFacade.getSinglePost(question.getId(), member.getId());

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getTitle()).isEqualTo(question.getTitle());
        verify(questionPostService, times(1)).findByIdFetchJoin(question.getId());
        verify(questionCommentService, times(1)).findQuestionCommentsByQuestionId(question.getId());
    }

    /* -------------------------------------------- 단건 READ 메서드 테스트 끝 -------------------------------------------- */

}
