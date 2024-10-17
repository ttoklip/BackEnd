package com.domain.honeytip.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.api.ttoklip.domain.honeytip.domain.HoneyTip;
import com.api.ttoklip.domain.honeytip.repository.post.HoneyTipRepository;
import com.api.ttoklip.domain.honeytip.service.HoneyTipPostService;
import com.api.ttoklip.domain.member.domain.Member;
import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import com.domain.honeytip.repository.FakeHoneyTipPostRepository;
import honeytip.fixture.HoneyTipFixture;
import member.fixture.MemberFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class HoneyTipPostServiceTest {

    private HoneyTipPostService honeyTipPostService;

    private HoneyTipRepository honeyTipRepository;

    @BeforeEach
    void setBefore() {
        honeyTipRepository = new FakeHoneyTipPostRepository();
        honeyTipPostService = new HoneyTipPostService(null, honeyTipRepository);
    }

    @Test
    void 허니팁_게시글_저장_성공한다() {
        // Given
        Member member = MemberFixture.일반_회원_생성1();
        HoneyTip honeyTip = HoneyTipFixture.본인_허니팁_생성(member);

        // When
        HoneyTip saveHoneyTip = honeyTipPostService.saveHoneyTipPost(honeyTip);

        // Then
        HoneyTip savedHoneyTip = honeyTipRepository.findByIdActivated(saveHoneyTip.getId());
        assertSoftly(softly -> {
            softly.assertThat(savedHoneyTip).isNotNull();  // 저장된 객체가 null이 아님을 확인
            softly.assertThat(savedHoneyTip.getId()).isNotNull();  // 저장된 객체가 null이 아님을 확인
            softly.assertThat(savedHoneyTip.getTitle()).isEqualTo("지정된 멤버 허니팁 제목");  // 제목이 일치하는지 확인
            softly.assertThat(savedHoneyTip.getContent()).isEqualTo("지정된 멤버 허니팁 내용");  // 내용이 일치하는지 확인
        });

    }

    @Test
    void 존재하지_않는_허니팁_조회시_NOT_FOUND_ERROR_발생한다() {
        // Given
        Long nonExistentHoneyTipId = Long.MAX_VALUE;

        // When & Then
        assertSoftly(softly -> softly.assertThatThrownBy(() -> honeyTipRepository.findByIdActivated(nonExistentHoneyTipId))
                .isInstanceOf(ApiException.class)
                .hasMessageContaining(ErrorType.HONEY_TIP_NOT_FOUND.getMessage()));
    }

    @Test
    void 작성자가_아닌_사용자가_허니팁_수정시_UNAUTHORIZED_EDIT_POST_발생한다() {
        // Given
        Member writer = MemberFixture.일반_회원_생성1();
        HoneyTip honeyTip = HoneyTipFixture.본인_허니팁_생성(writer);

        Member reader = MemberFixture.일반_회원_생성2();

        // When & Then
        assertSoftly(softly -> softly.assertThatThrownBy(() -> honeyTipPostService.checkEditPermission(honeyTip, reader.getId()))
                .isInstanceOf(ApiException.class)  // 예외 발생 여부 확인
                .hasMessageContaining(ErrorType.UNAUTHORIZED_EDIT_POST.getMessage()));  // 예외 메시지 검증
    }

    @Test
    void URL과_이미지가_포함된_허니팁_상세_조회시_URL과_이미지를_FETCHJOIN으로_함께_조회된다() {
        // Given
        Member member = MemberFixture.일반_회원_생성1();
        HoneyTip honeyTip = HoneyTipFixture.본인_허니팁_사진_URL_포함하여_생성(member);
        HoneyTip savedHoneyTip = honeyTipPostService.saveHoneyTipPost(honeyTip);

        // When
        HoneyTip resultHoneyTip = honeyTipPostService.findHoneyTipWithDetails(savedHoneyTip.getId());

        // Then
        assertSoftly(softly -> {
            softly.assertThat(resultHoneyTip).isNotNull();
            softly.assertThat(resultHoneyTip.getId()).isEqualTo(savedHoneyTip.getId());
            softly.assertThat(resultHoneyTip.getTitle()).isEqualTo(savedHoneyTip.getTitle());
            softly.assertThat(resultHoneyTip.getContent()).isEqualTo(savedHoneyTip.getContent());

            // URL 검증
            softly.assertThat(resultHoneyTip.getHoneyTipUrls()).hasSize(2);
            softly.assertThat(resultHoneyTip.getHoneyTipUrls()).extracting("url")
                    .containsExactlyInAnyOrder("https://existing-url1.com", "https://existing-url2.com");

            // 이미지 검증
            softly.assertThat(resultHoneyTip.getHoneyTipImages()).hasSize(2);
            softly.assertThat(resultHoneyTip.getHoneyTipImages()).extracting("url")
                    .containsExactlyInAnyOrder("https://existing-image1.com", "https://existing-image2.com");
        });
    }
}
