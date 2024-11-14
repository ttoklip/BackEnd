package com.domain.question.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.common.exception.ApiException;
import com.common.exception.ErrorType;
import com.domain.member.domain.Member;
import com.domain.question.application.QuestionPostService;
import com.domain.question.domain.Question;
import com.domain.question.domain.QuestionRepository;
import com.domain.question.repository.FakeQuestionPostRepository;
import member.fixture.MemberFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import question.fixture.QuestionFixture;

@SuppressWarnings("NonAsciiCharacters") // 메서드명, 변수명 한글 가능
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class) // 테스트 결과에 언더스코어 공백으로 변환해서 출력
public class QuestionPostServiceTest {

    private QuestionPostService questionPostService;
    private QuestionRepository questionRepository;

    @BeforeEach
    void setBefore() {
        questionRepository = new FakeQuestionPostRepository();
        questionPostService = new QuestionPostService(questionRepository);
    }

    @Test
    void 질문_게시글_저장_성공한다() {
        // Given
        Member member = MemberFixture.일반_회원_생성1();
        Question question = QuestionFixture.본인_질문_생성(member);

        // When
        Question savedQuestion = questionPostService.saveQuestionPost(question);

        // Then
        Question foundQuestion = questionRepository.findByIdActivated(savedQuestion.getId());
        assertSoftly(softly -> {
            softly.assertThat(foundQuestion).isNotNull();
            softly.assertThat(foundQuestion.getId()).isNotNull();
            softly.assertThat(foundQuestion.getTitle()).isEqualTo("지정된 멤버 질문 제목");
            softly.assertThat(foundQuestion.getContent()).isEqualTo("지정된 멤버 질문 내용");
        });
    }

    @Test
    void 질문_단건_조회한다() {
        // Given
        Question question = QuestionFixture.타인_질문_생성();
        questionRepository.save(question);

        // When
        Question foundQuestion = questionPostService.getQuestion(question.getId());

        // Then
        assertSoftly(softly -> {
            softly.assertThat(foundQuestion.getTitle()).isEqualTo(question.getTitle());
            softly.assertThat(foundQuestion.getContent()).isEqualTo(question.getContent());
            softly.assertThat(foundQuestion.getCategory()).isEqualTo(question.getCategory());
        });
    }

    @Test
    void 존재하지_않는_질문_조회시_NOT_FOUND_ERROR_발생한다() {
        // Given
        Long nonExistentQuestionId = Long.MAX_VALUE;

        // When & Then
        assertThatThrownBy(() -> questionPostService.getQuestion(nonExistentQuestionId))
                .isInstanceOf(ApiException.class)
                .hasMessageContaining(ErrorType.QUESTION_NOT_FOUND.getMessage());
    }

    @Test
    void 작성자가_아닌_사용자가_질문_수정시_UNAUTHORIZED_EDIT_POST_발생한다() {
        // Given
        Member writer = MemberFixture.일반_회원_생성1();
        Question question = QuestionFixture.본인_질문_생성(writer);

        Member reader = MemberFixture.일반_회원_생성2();

        // When & Then
        assertSoftly(softly -> softly.assertThatThrownBy(() -> questionPostService.checkEditPermission(question, reader.getId()))
                .isInstanceOf(ApiException.class)
                .hasMessageContaining(ErrorType.UNAUTHORIZED_EDIT_POST.getMessage()));
    }

}
