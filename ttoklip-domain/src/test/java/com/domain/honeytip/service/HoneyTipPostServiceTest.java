package com.domain.honeytip.service;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.common.exception.ApiException;
import com.common.exception.ErrorType;
import com.domain.honeytip.application.HoneyTipPostService;
import com.domain.honeytip.domain.HoneyTip;
import com.domain.honeytip.domain.HoneyTipRepository;
import com.domain.honeytip.repository.FakeHoneyTipPostRepository;
import com.domain.member.domain.Member;
import honeytip.fixture.HoneyTipFixture;
import java.util.List;
import java.util.Random;
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
        honeyTipPostService = new HoneyTipPostService(honeyTipRepository);
    }

    @Test
    void 허니팁_게시글_저장_성공한다() {
        // Given
        Member member = MemberFixture.일반_회원_생성1();
        HoneyTip honeyTip = HoneyTipFixture.본인_허니팁_생성(member);

        // When
        HoneyTip saveHoneyTip = honeyTipPostService.save(honeyTip);

        // Then
        HoneyTip savedHoneyTip = honeyTipRepository.findByIdActivated(saveHoneyTip.getId());
        assertSoftly(softly -> {
            softly.assertThat(savedHoneyTip).isNotNull();
            softly.assertThat(savedHoneyTip.getId()).isNotNull();
            softly.assertThat(savedHoneyTip.getTitle()).isEqualTo("지정된 멤버 허니팁 제목");
            softly.assertThat(savedHoneyTip.getContent()).isEqualTo("지정된 멤버 허니팁 내용");
        });

    }

    @Test
    void 허니팁_단건_조회한다() {
        // given
        HoneyTip honeyTip = HoneyTipFixture.타인_허니팁_생성();
        honeyTipRepository.save(honeyTip);

        // when
        HoneyTip foundHoneyTip = honeyTipPostService.getHoneytip(honeyTip.getId());

        // then
        assertSoftly(softly -> {
            softly.assertThat(foundHoneyTip.getTitle()).isEqualTo(honeyTip.getTitle());
            softly.assertThat(foundHoneyTip.getContent()).isEqualTo(honeyTip.getContent());
            softly.assertThat(foundHoneyTip.getCategory()).isEqualTo(honeyTip.getCategory());
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
                .isInstanceOf(ApiException.class)
                .hasMessageContaining(ErrorType.UNAUTHORIZED_EDIT_POST.getMessage()));
    }

    @Test
    void URL과_이미지가_포함된_허니팁_상세_조회시_URL과_이미지를_FETCHJOIN으로_함께_조회된다() {
        // Given
        Member member = MemberFixture.일반_회원_생성1();
        HoneyTip honeyTip = HoneyTipFixture.본인_허니팁_사진_URL_포함하여_생성(member);
        HoneyTip savedHoneyTip = honeyTipPostService.save(honeyTip);

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

    @Test
    void 가장_최근에_작성된_허니팁_3개를_조회한다() {
        // Given
        int n = getRandomN();
        List<HoneyTip> honeyTips = HoneyTipFixture.허니팁_복지정책_크기가_N인_리스트_생성(n);
        honeyTips.forEach(honeyTipPostService::save);

        // When
        List<HoneyTip> result = honeyTipPostService.findRecent3();

        // Then
        assertSoftly(softly -> {
            softly.assertThat(result).hasSize(3);
            // createDate 로 비교해야하지만, JpaAduting때문에 수동설정 할 수 없으므로 불가피하게 id로 비교
            softly.assertThat(result.get(0).getId()).isGreaterThan(result.get(1).getId());
            softly.assertThat(result.get(1).getId()).isGreaterThan(result.get(2).getId());
        });

    }


    private int getRandomN() {
        Random random = new Random();
        return random.nextInt(Short.MAX_VALUE / 10);
    }


}
