package com.domain.honeytip.service;

import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.common.exception.ApiException;
import com.common.exception.ErrorType;
import com.domain.honeytip.application.HoneyTipImageService;
import com.domain.honeytip.domain.HoneyTip;
import com.domain.honeytip.domain.HoneyTipImage;
import com.domain.honeytip.domain.HoneyTipImageRepository;
import com.domain.honeytip.repository.FakeHoneyTipImageRepository;
import com.domain.member.domain.Member;
import honeytip.fixture.HoneyTipFixture;
import java.util.List;
import member.fixture.MemberFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HoneyTipImageServiceTest {

    private HoneyTipImageService honeyTipImageService;
    private HoneyTipImageRepository honeyTipImageRepository;

    private static HoneyTipImage 허니팁_이미지_생성(final HoneyTip honeyTip) {
        return HoneyTipImage.builder()
                .honeyTip(honeyTip)
                .url("http://example.com/image1.png")
                .build();
    }

    @BeforeEach
    void setUp() {
        honeyTipImageRepository = new FakeHoneyTipImageRepository();
        honeyTipImageService = new HoneyTipImageService(honeyTipImageRepository);
    }

    @Test
    void 이미지_등록_성공() {
        // given
        HoneyTip honeyTip = HoneyTip.builder().id(1L).title("Test").build();
        String uploadUrl = "http://example.com/image1.png";

        // when
        honeyTipImageService.register(honeyTip, uploadUrl);

        // then
        boolean exists = honeyTipImageRepository.existsByHoneyTipIdAndUrl(honeyTip.getId(), uploadUrl);

        assertSoftly(softly -> softly.assertThat(exists).isTrue());
    }

    @Test
    void 이미지_삭제_성공() {
        // given

        Member member = MemberFixture.일반_회원_생성1();
        HoneyTip honeyTip = HoneyTipFixture.본인_허니팁_생성(member);

        HoneyTipImage honeyTipImage1 = 허니팁_이미지_생성(honeyTip);
        HoneyTipImage honeyTipImage2 = 허니팁_이미지_생성(honeyTip);

        HoneyTipImage savedHoneyTipImage1 = honeyTipImageRepository.save(honeyTipImage1);
        HoneyTipImage savedHoneyTipImage2 = honeyTipImageRepository.save(honeyTipImage2);

        List<Long> imageIds = List.of(savedHoneyTipImage1.getId(), savedHoneyTipImage2.getId());
        Long memberId = honeyTip.getMember().getId();

        // when
        honeyTipImageService.deleteImages(imageIds, memberId);

        // then
        boolean existsAfterDeletion = honeyTipImageRepository.doAllImageIdsExist(imageIds);
        assertSoftly(
                softly -> softly.assertThat(existsAfterDeletion).isFalse()
        );
    }

    @Test
    void 이미지_존재하지_않을_때_예외_발생() {
        // given
        Member member = MemberFixture.일반_회원_생성1();
        HoneyTip honeyTip = HoneyTipFixture.본인_허니팁_생성(member);

        List<Long> invalidImageIds = List.of(999L, 1000L); // 존재하지 않는 이미지 ID
        Long memberId = honeyTip.getMember().getId();

        // when/then
        ApiException exception = assertThrows(ApiException.class, () -> {
            honeyTipImageService.deleteImages(invalidImageIds, memberId);
        });

        assertSoftly(
                softly -> softly.assertThat(exception.getErrorType())
                        .isEqualTo(ErrorType.DELETE_INVALID_IMAGE_IDS)
        );
    }

    @Test
    void 이미지_소유자가_아닐_때_예외_발생() {
        // given
        Member member = MemberFixture.일반_회원_생성1();
        Member 다른회원 = MemberFixture.일반_회원_생성2();
        HoneyTip honeyTip = HoneyTipFixture.본인_허니팁_생성(member);

        HoneyTipImage honeyTipImage = 허니팁_이미지_생성(honeyTip);
        HoneyTipImage savedHoneyTipImage = honeyTipImageRepository.save(honeyTipImage);

        List<Long> imageIds = List.of(savedHoneyTipImage.getId());
        Long 다른회원Id = 다른회원.getId();  // 다른 회원 ID

        // when/then
        ApiException exception = assertThrows(ApiException.class, () -> {
            honeyTipImageService.deleteImages(imageIds, 다른회원Id);
        });

        assertSoftly(softly -> softly.assertThat(exception.getErrorType())
                .isEqualTo(ErrorType.INVALID_DELETE_IMAGE_OWNER));
    }

    @Test
    void 이미지_삭제_중_예외_발생_시_롤백_확인() {
        // given
        Member member = MemberFixture.일반_회원_생성1();
        HoneyTip honeyTip = HoneyTipFixture.본인_허니팁_생성(member);

        HoneyTipImage honeyTipImage1 = 허니팁_이미지_생성(honeyTip);
        HoneyTipImage honeyTipImage2 = 허니팁_이미지_생성(honeyTip);

        HoneyTipImage savedHoneyTipImage1 = honeyTipImageRepository.save(honeyTipImage1);
        HoneyTipImage savedHoneyTipImage2 = honeyTipImageRepository.save(honeyTipImage2);

        List<Long> imageIds = List.of(savedHoneyTipImage1.getId(), Long.MAX_VALUE);
        Long memberId = honeyTip.getMember().getId();

        // when/then
        ApiException exception = assertThrows(ApiException.class, () -> {
            honeyTipImageService.deleteImages(imageIds, memberId);
        });

        boolean allImagesStillExist = honeyTipImageRepository.doAllImageIdsExist(
                List.of(savedHoneyTipImage1.getId(), savedHoneyTipImage2.getId())
        );

        assertSoftly(softly -> {
            softly.assertThat(exception.getErrorType()).isEqualTo(ErrorType.DELETE_INVALID_IMAGE_IDS);
            softly.assertThat(allImagesStillExist).isTrue();  // 롤백 확인: 이미지가 그대로 유지됨
        });
    }
}
