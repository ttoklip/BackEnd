package com.domain.honeytip.service;

import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.common.exception.ApiException;
import com.domain.honeytip.application.HoneyTipLikeService;
import com.domain.honeytip.domain.HoneyTip;
import com.domain.honeytip.domain.HoneyTipLikeRepository;
import com.domain.honeytip.repository.FakeHoneyTipLikeRepository;
import com.domain.member.domain.Member;
import honeytip.fixture.HoneyTipFixture;
import member.fixture.MemberFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HoneyTipLikeServiceTest {
    private HoneyTipLikeService honeyTipLikeService;
    private HoneyTipLikeRepository honeyTipLikeRepository;

    @BeforeEach
    void setBefore() {
        honeyTipLikeRepository = new FakeHoneyTipLikeRepository();
        honeyTipLikeService = new HoneyTipLikeService(honeyTipLikeRepository);
    }

    @Test
    void 좋아요가_정상적으로_등록된다() {
        // given
        HoneyTip honeyTip = HoneyTipFixture.타인_허니팁_생성();
        Member member = MemberFixture.일반_회원_생성1();

        // when
        honeyTipLikeService.register(honeyTip, member);

        // then
        Long likeCount = honeyTipLikeRepository.countHoneyTipLikesByHoneyTipId(honeyTip.getId());
        assertSoftly(softly -> {
            softly.assertThat(likeCount).isEqualTo(1);
            softly.assertThat(honeyTipLikeRepository.existsByHoneyTipIdAndMemberId(honeyTip.getId(), member.getId())).isTrue();
        });
    }

    @Test
    void 좋아요가_정상적으로_취소된다() {
        // given
        HoneyTip honeyTip = HoneyTipFixture.타인_허니팁_생성();
        Member member = MemberFixture.일반_회원_생성1();
        honeyTipLikeService.register(honeyTip, member);

        // when
        honeyTipLikeService.cancel(honeyTip, member.getId());

        // then
        Long likeCount = honeyTipLikeRepository.countHoneyTipLikesByHoneyTipId(honeyTip.getId());
        assertSoftly(softly -> {
            softly.assertThat(likeCount).isEqualTo(0);
            softly.assertThat(honeyTipLikeRepository.existsByHoneyTipIdAndMemberId(honeyTip.getId(), member.getId())).isFalse();
        });
    }

    @Test
    void 좋아요가_존재하지_않을때_취소시_에러발생() {
        // given
        HoneyTip honeyTip = HoneyTipFixture.타인_허니팁_생성();
        Long memberId = 123L;

        // when & then
        assertThrows(ApiException.class, () -> {
            honeyTipLikeService.cancel(honeyTip, memberId);
        });
    }

    @Test
    void 특정_허니팁의_좋아요_수를_정상적으로_반환한다() {
        // given
        HoneyTip honeyTip = HoneyTipFixture.타인_허니팁_생성();
        Member member1 = MemberFixture.일반_회원_생성1();
        Member member2 = MemberFixture.일반_회원_생성2();
        honeyTipLikeService.register(honeyTip, member1);
        honeyTipLikeService.register(honeyTip, member2);

        // when
        Long likeCount = honeyTipLikeService.countHoneyTipLikes(honeyTip.getId());

        // then
        assertSoftly(softly -> {
            softly.assertThat(likeCount).isEqualTo(2);
        });
    }
}
