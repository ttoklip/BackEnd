package com.domain.question.facade;

import com.api.ttoklip.domain.common.Category;
import com.api.ttoklip.domain.common.report.dto.ReportCreateRequest;
import com.api.ttoklip.domain.main.dto.response.CategoryPagingResponse;
import com.api.ttoklip.domain.main.dto.response.CategoryResponses;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;
import question.fixture.QuestionFixture;
import report.fixture.ReportFixture;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
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


    /* -------------------------------------------- REPORT METHOD CALL TEST -------------------------------------------- */
    @Test
    void 질문_게시글_신고_메서드_호출_성공() {
        // Given
        Question question = QuestionFixture.타인_질문_생성();
        ReportCreateRequest request = ReportFixture.신고_요청_픽스처();

        when(questionPostService.getQuestion(question.getId())).thenReturn(question);

        // When
        Message result = questionPostFacade.report(question.getId(), request);

        // Then
        assertSoftly(softly -> {
            softly.assertThat(result).isNotNull();
            softly.assertThat(result.getMessage()).contains("Question", "신고");
        });

        verify(questionPostService, times(1)).getQuestion(question.getId());
        verify(reportService, times(1)).reportQuestion(request, question);
    }

    /* -------------------------------------------- REPORT METHOD CALL TEST END -------------------------------------------- */


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


    /* -------------------------------------------- 카테고리별 메인 조회 메서드 테스트 -------------------------------------------- */
    @Test
    void 질문_카테고리별_메인_조회_메서드_호출_성공() {
        // Given
        int N = 10;
        List<Question> houseworkQuestions = QuestionFixture.질문_집안일_크기가_N인_리스트_생성(N);
        List<Question> recipeQuestions = QuestionFixture.질문_레시피_크기가_N인_리스트_생성(N);
        List<Question> safeLivingQuestions = QuestionFixture.질문_안전생활_크기가_N인_리스트_생성(N);
        List<Question> welfarePolicyQuestions = QuestionFixture.질문_복지정책_크기가_N인_리스트_생성(N);

        when(questionPostService.getHouseWork()).thenReturn(houseworkQuestions);
        when(questionPostService.getRecipe()).thenReturn(recipeQuestions);
        when(questionPostService.getSafeLiving()).thenReturn(safeLivingQuestions);
        when(questionPostService.getWelfarePolicy()).thenReturn(welfarePolicyQuestions);

        // When
        CategoryResponses response = questionPostFacade.getDefaultCategoryRead();

        // Then
        assertSoftly(softly -> {
            softly.assertThat(response).isNotNull();
            softly.assertThat(response.getHousework()).hasSize(houseworkQuestions.size());
            softly.assertThat(response.getCooking()).hasSize(recipeQuestions.size());
            softly.assertThat(response.getSafeLiving()).hasSize(safeLivingQuestions.size());
            softly.assertThat(response.getWelfarePolicy()).hasSize(welfarePolicyQuestions.size());
        });

        verify(questionPostService, times(1)).getHouseWork();
        verify(questionPostService, times(1)).getRecipe();
        verify(questionPostService, times(1)).getSafeLiving();
        verify(questionPostService, times(1)).getWelfarePolicy();
    }

    /* -------------------------------------------- 카테고리별 메인 조회 메서드 테스트 끝 -------------------------------------------- */


    /* -------------------------------------------- 카테고리별 페이징 조회 메서드 테스트 -------------------------------------------- */
    @Test
    void 질문_카테고리별_페이징_요청_메서드_호출_성공_집안일() {
        // Given
        int N = 10;
        PageRequest pageable = PageRequest.of(0, 10);
        List<Question> 집안일_질문_리스트 = QuestionFixture.질문_집안일_크기가_N인_리스트_생성(N);
        Page<Question> page = new PageImpl<>(집안일_질문_리스트, pageable, 집안일_질문_리스트.size());

        when(questionPostService.matchCategoryPaging(Category.HOUSEWORK, pageable)).thenReturn(page);

        // When
        CategoryPagingResponse response = questionPostFacade.matchCategoryPaging(Category.HOUSEWORK, pageable);

        // Then
        assertSoftly(softly -> {
            softly.assertThat(response).isNotNull();
            softly.assertThat(response.category()).isEqualTo(Category.HOUSEWORK);
            softly.assertThat(response.data()).hasSize(집안일_질문_리스트.size());
            softly.assertThat(response.totalElements()).isEqualTo(집안일_질문_리스트.size());
            softly.assertThat(response.totalPage()).isEqualTo(1);
            softly.assertThat(response.isFirst()).isTrue();
            softly.assertThat(response.isLast()).isTrue();
        });
    }

    @Test
    void 질문_카테고리별_페이징_요청_메서드_호출_성공_레시피() {
        // Given
        int N = 10;
        PageRequest pageable = PageRequest.of(0, 10);
        List<Question> 레시피_질문_리스트 = QuestionFixture.질문_레시피_크기가_N인_리스트_생성(N);
        Page<Question> page = new PageImpl<>(레시피_질문_리스트, pageable, 레시피_질문_리스트.size());

        when(questionPostService.matchCategoryPaging(Category.RECIPE, pageable)).thenReturn(page);

        // When
        CategoryPagingResponse response = questionPostFacade.matchCategoryPaging(Category.RECIPE, pageable);

        // Then
        assertSoftly(softly -> {
            softly.assertThat(response).isNotNull();
            softly.assertThat(response.category()).isEqualTo(Category.RECIPE);
            softly.assertThat(response.data()).hasSize(레시피_질문_리스트.size());
            softly.assertThat(response.totalElements()).isEqualTo(레시피_질문_리스트.size());
            softly.assertThat(response.totalPage()).isEqualTo(1);
            softly.assertThat(response.isFirst()).isTrue();
            softly.assertThat(response.isLast()).isTrue();
        });
    }

    @Test
    void 질문_카테고리별_페이징_요청_메서드_호출_성공_안전생활() {
        // Given
        int N = 10;
        PageRequest pageable = PageRequest.of(0, 10);
        List<Question> 안전생활_질문_리스트 = QuestionFixture.질문_안전생활_크기가_N인_리스트_생성(N);
        Page<Question> page = new PageImpl<>(안전생활_질문_리스트, pageable, 안전생활_질문_리스트.size());

        when(questionPostService.matchCategoryPaging(Category.SAFE_LIVING, pageable)).thenReturn(page);

        // When
        CategoryPagingResponse response = questionPostFacade.matchCategoryPaging(Category.SAFE_LIVING, pageable);

        // Then
        assertSoftly(softly -> {
            softly.assertThat(response).isNotNull();
            softly.assertThat(response.category()).isEqualTo(Category.SAFE_LIVING);
            softly.assertThat(response.data()).hasSize(안전생활_질문_리스트.size());
            softly.assertThat(response.totalElements()).isEqualTo(안전생활_질문_리스트.size());
            softly.assertThat(response.totalPage()).isEqualTo(1);
            softly.assertThat(response.isFirst()).isTrue();
            softly.assertThat(response.isLast()).isTrue();
        });
    }

    @Test
    void 질문_카테고리별_페이징_요청_메서드_호출_성공_복지정책() {
        // Given
        int N = 10;
        PageRequest pageable = PageRequest.of(0, 10);
        List<Question> 복지정책_질문_리스트 = QuestionFixture.질문_복지정책_크기가_N인_리스트_생성(N);
        Page<Question> page = new PageImpl<>(복지정책_질문_리스트, pageable, 복지정책_질문_리스트.size());

        when(questionPostService.matchCategoryPaging(Category.WELFARE_POLICY, pageable)).thenReturn(page);

        // When
        CategoryPagingResponse response = questionPostFacade.matchCategoryPaging(Category.WELFARE_POLICY, pageable);

        // Then
        assertSoftly(softly -> {
            softly.assertThat(response).isNotNull();
            softly.assertThat(response.category()).isEqualTo(Category.WELFARE_POLICY);
            softly.assertThat(response.data()).hasSize(복지정책_질문_리스트.size());
            softly.assertThat(response.totalElements()).isEqualTo(복지정책_질문_리스트.size());
            softly.assertThat(response.totalPage()).isEqualTo(1);
            softly.assertThat(response.isFirst()).isTrue();
            softly.assertThat(response.isLast()).isTrue();
        });
    }

    /* -------------------------------------------- 카테고리별 페이징 조회 메서드 테스트 끝 -------------------------------------------- */

}
