package com.domain.question.service;

import com.api.ttoklip.domain.member.domain.Member;
import com.api.ttoklip.domain.question.domain.Question;
import com.api.ttoklip.domain.question.repository.post.QuestionRepository;
import com.api.ttoklip.domain.question.service.QuestionPostService;
import com.domain.question.repository.FakeQuestionPostRepository;
import member.fixture.MemberFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import question.fixture.QuestionFixture;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

@SuppressWarnings("NonAsciiCharacters") // 메서드명, 변수명 한글 가능
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class) // 테스트 결과에 언더스코어 공백으로 변환해서 출력
public class QuestionPostServiceTest {

    private QuestionPostService questionPostService;
    private QuestionRepository questionRepository;

    @BeforeEach
    void setBefore() {
        questionRepository = new FakeQuestionPostRepository();
        questionPostService = new QuestionPostService(null, questionRepository);
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



}
