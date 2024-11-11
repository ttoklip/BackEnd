package com.domain.question.service;

import com.api.ttoklip.domain.member.domain.Member;
import com.api.ttoklip.domain.question.domain.Question;
import com.api.ttoklip.domain.question.domain.QuestionImage;
import com.api.ttoklip.domain.question.domain.QuestionImageRepository;
import com.api.ttoklip.domain.question.application.QuestionImageService;
import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import com.domain.question.repository.FakeQuestionImageRepository;
import member.fixture.MemberFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import question.fixture.QuestionFixture;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

public class QuestionImageServiceTest {

    private QuestionImageService questionImageService;
    private QuestionImageRepository questionImageRepository;

    private static QuestionImage 질문_이미지_생성(final Question question) {
        return QuestionImage.builder()
                .question(question)
                .url("http://example.com/image1.png")
                .build();
    }

    @BeforeEach
    void setUp() {
        questionImageRepository = new FakeQuestionImageRepository();
        questionImageService = new QuestionImageService(questionImageRepository);
    }

    @Test
    void 이미지_등록_성공() {
        // given
        Question question = Question.builder().id(1L).title("Test").build();
        String uploadUrl = "http://example.com/image1.png";

        // when
        questionImageService.register(question, uploadUrl);

        // then
        boolean exists = questionImageRepository.existsByQuestionIdAndUrl(question.getId(), uploadUrl);

        assertSoftly(softly -> softly.assertThat(exists).isTrue());
    }

    @Test
    void 이미지_삭제_성공() {
        // given
        Member member = MemberFixture.일반_회원_생성1();
        Question question = QuestionFixture.본인_질문_생성(member);

        QuestionImage questionImage1 = 질문_이미지_생성(question);
        QuestionImage questionImage2 = 질문_이미지_생성(question);

        QuestionImage savedQuestionImage1 = questionImageRepository.save(questionImage1);
        QuestionImage savedQuestionImage2 = questionImageRepository.save(questionImage2);

        List<Long> imageIds = List.of(savedQuestionImage1.getId(), savedQuestionImage2.getId());
        Long memberId = question.getMember().getId();

        // when
        questionImageService.deleteImages(imageIds, memberId);

        // then
        boolean existsAfterDeletion = questionImageRepository.doAllImageIdsExist(imageIds);
        assertSoftly(softly -> softly.assertThat(existsAfterDeletion).isFalse());
    }

    @Test
    void 이미지_존재하지_않을_때_예외_발생() {
        // given
        Member member = MemberFixture.일반_회원_생성1();
        Question question = QuestionFixture.본인_질문_생성(member);

        List<Long> invalidImageIds = List.of(999L, 1000L); // 존재하지 않는 이미지 ID
        Long memberId = question.getMember().getId();

        // when/then
        assertThatThrownBy(() -> questionImageService.deleteImages(invalidImageIds, memberId))
                .isInstanceOf(ApiException.class)
                .hasMessageContaining(ErrorType.DELETE_INVALID_IMAGE_IDS.getMessage());
    }

    @Test
    void 이미지_소유자가_아닐_때_예외_발생() {
        // given
        Member member = MemberFixture.일반_회원_생성1();
        Member 다른회원 = MemberFixture.일반_회원_생성2();
        Question question = QuestionFixture.본인_질문_생성(member);

        QuestionImage questionImage = 질문_이미지_생성(question);
        QuestionImage savedQuestionImage = questionImageRepository.save(questionImage);

        List<Long> imageIds = List.of(savedQuestionImage.getId());
        Long 다른회원Id = 다른회원.getId();  // 다른 회원 ID

        // when/then
        assertThatThrownBy(() -> questionImageService.deleteImages(imageIds, 다른회원Id))
                .isInstanceOf(ApiException.class)
                .hasMessageContaining(ErrorType.INVALID_DELETE_IMAGE_OWNER.getMessage());
    }

    @Test
    void 이미지_삭제_중_예외_발생_시_롤백_확인() {
        // given
        Member member = MemberFixture.일반_회원_생성1();
        Question question = QuestionFixture.본인_질문_생성(member);

        QuestionImage questionImage1 = 질문_이미지_생성(question);
        QuestionImage questionImage2 = 질문_이미지_생성(question);

        QuestionImage savedQuestionImage1 = questionImageRepository.save(questionImage1);
        QuestionImage savedQuestionImage2 = questionImageRepository.save(questionImage2);

        List<Long> imageIds = List.of(savedQuestionImage1.getId(), Long.MAX_VALUE);
        Long memberId = question.getMember().getId();

        // when/then
        assertThatThrownBy(() -> questionImageService.deleteImages(imageIds, memberId))
                .isInstanceOf(ApiException.class)
                .hasMessageContaining(ErrorType.DELETE_INVALID_IMAGE_IDS.getMessage());

        boolean allImagesStillExist = questionImageRepository.doAllImageIdsExist(
                List.of(savedQuestionImage1.getId(), savedQuestionImage2.getId())
        );

        assertSoftly(softly -> {
            softly.assertThat(allImagesStillExist).isTrue();  // 롤백 확인: 이미지가 그대로 유지됨
        });
    }
}
