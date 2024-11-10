package com.domain.honeytip.service;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.api.ttoklip.domain.honeytip.domain.HoneyTip;
import com.api.ttoklip.domain.honeytip.repository.post.HoneyTipRepository;
import com.api.ttoklip.domain.honeytip.service.HoneyTipPostService;
import com.api.ttoklip.domain.member.domain.Member;
import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import com.domain.honeytip.repository.FakeHoneyTipPostRepository;
import honeytip.fixture.HoneyTipFixture;
import java.util.List;
import java.util.Random;
import member.fixture.MemberFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

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

    @Test
    void 집안일_카테고리_허니팁에서_최근10개를_조회한다() {
        // Given
        int n = getRandomN();
        List<HoneyTip> houseworkTips = HoneyTipFixture.허니팁_집안일_크기가_N인_리스트_생성(n);
        houseworkTips.forEach(honeyTipPostService::saveHoneyTipPost);

        // When
        List<HoneyTip> result = honeyTipPostService.findHouseworkTips();

        // Then
        assertSoftly(softly -> {
            softly.assertThat(result).isNotNull();
            softly.assertThat(result).hasSize(10);
            softly.assertThat(result.get(0).getCategory()).isEqualTo(Category.HOUSEWORK);
        });
    }

    @Test
    void 레시피_카테고리_허니팁에서_최근10개를_조회한다() {
        // Given
        int n = getRandomN();
        List<HoneyTip> recipeTips = HoneyTipFixture.허니팁_레시피_크기가_N인_리스트_생성(n);
        recipeTips.forEach(honeyTipPostService::saveHoneyTipPost);  // 각각의 허니팁을 저장

        // When
        List<HoneyTip> result = honeyTipPostService.findRecipeTips();

        // Then
        assertSoftly(softly -> {
            softly.assertThat(result).isNotNull();
            softly.assertThat(result).hasSize(10);
            softly.assertThat(result.get(0).getCategory()).isEqualTo(Category.RECIPE);
        });
    }

    @Test
    void 안전생활_카테고리_허니팁에서_최근10개를_조회한다() {
        // Given
        int n = getRandomN();
        List<HoneyTip> safeLivingTips = HoneyTipFixture.허니팁_안전생활_크기가_N인_리스트_생성(n);
        safeLivingTips.forEach(honeyTipPostService::saveHoneyTipPost);

        // When
        List<HoneyTip> result = honeyTipPostService.findSafeLivingTips();

        // Then
        assertSoftly(softly -> {
            softly.assertThat(result).isNotNull();
            softly.assertThat(result).hasSize(10);
            softly.assertThat(result.get(0).getCategory()).isEqualTo(Category.SAFE_LIVING);
        });
    }

    @Test
    void 복지정책_카테고리_허니팁에서_최근10개를_조회한다() {
        // Given
        int n = getRandomN();
        List<HoneyTip> welfarePolicyTips = HoneyTipFixture.허니팁_복지정책_크기가_N인_리스트_생성(n);
        welfarePolicyTips.forEach(honeyTipPostService::saveHoneyTipPost);

        // When
        List<HoneyTip> result = honeyTipPostService.findWelfarePolicyTips();

        // Then
        assertSoftly(softly -> {
            softly.assertThat(result).isNotNull();
            softly.assertThat(result).hasSize(10);
            softly.assertThat(result.get(0).getCategory()).isEqualTo(Category.WELFARE_POLICY);
        });
    }

    @Test
    void 카테고리별_페이징처리하여_복지정책_조회한다() {
        // Given
        int n = getRandomN();
        List<HoneyTip> welfarePolicyTips = HoneyTipFixture.허니팁_복지정책_크기가_N인_리스트_생성(n);
        welfarePolicyTips.forEach(honeyTipPostService::saveHoneyTipPost);

        int pageSize = 10;
        PageRequest pageable = PageRequest.of(0, pageSize);

        // When
        Page<HoneyTip> result = honeyTipPostService.matchCategoryPaging(Category.WELFARE_POLICY, pageable);

        // Then
        assertSoftly(softly -> {
            softly.assertThat(result).isNotNull();
            softly.assertThat(result.getContent()).hasSize(Math.min(pageSize, n));
            softly.assertThat(result.getTotalElements()).isEqualTo(n);
            softly.assertThat(result.getTotalPages()).isEqualTo((n + pageSize - 1) / pageSize);
            softly.assertThat(result.isFirst()).isTrue();
            softly.assertThat(result.isLast()).isEqualTo(n <= pageSize);
            softly.assertThat(result.getContent().get(0).getCategory()).isEqualTo(Category.WELFARE_POLICY);
        });
    }

    @Test
    void 가장_최근에_작성된_허니팁_3개를_조회한다() {
        // Given
        int n = getRandomN();
        List<HoneyTip> honeyTips = HoneyTipFixture.허니팁_복지정책_크기가_N인_리스트_생성(n);
        honeyTips.forEach(honeyTipPostService::saveHoneyTipPost);

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

    @Test
    void 인기순으로_5개를_조회한다() {
        // given: 5명의 회원과 10개의 랜덤 허니팁 생성
        List<Member> members = MemberFixture.회원_5명_생성();
        List<HoneyTip> honeyTips = HoneyTipFixture.랜덤_카테고리와_랜덤_개수의_댓글_좋아요_스크랩_포함한_허니팁_생성(members);
        honeyTips.forEach(honeyTipRepository::save);

        // when
        List<HoneyTip> top5HoneyTips = honeyTipPostService.getPopularityTop5();

        // then
        assertSoftly(softly -> {
            // 조회된 허니팁이 5개인지 확인
            softly.assertThat(top5HoneyTips.size()).isEqualTo(5);

            // 각 허니팁의 점수를 내림차순으로 확인
            for (int i = 0; i < top5HoneyTips.size() - 1; i++) {
                int currentScore = calculatePopularityScore(top5HoneyTips.get(i));
                int nextScore = calculatePopularityScore(top5HoneyTips.get(i + 1));
                softly.assertThat(currentScore)
                        .as("허니팁 %d의 점수는 다음 허니팁 %d의 점수보다 크거나 같아야 합니다", i, i + 1)
                        .isGreaterThanOrEqualTo(nextScore);
            }
        });
    }

    private int calculatePopularityScore(HoneyTip honeyTip) {
        return honeyTip.getHoneyTipComments().size()
                + honeyTip.getHoneyTipLikes().size()
                + honeyTip.getHoneyTipScraps().size();
    }

    private int getRandomN() {
        Random random = new Random();
        return random.nextInt(Short.MAX_VALUE / 10);
    }


}
