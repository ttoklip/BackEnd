package com.domain.honeytip.service;

import com.common.exception.ApiException;
import com.domain.honeytip.application.HoneyTipScrapService;
import com.domain.honeytip.domain.HoneyTip;
import com.domain.honeytip.domain.HoneyTipScrapRepository;
import com.domain.honeytip.repository.FakeHoneyTipScrapRepository;
import com.domain.member.domain.Member;
import honeytip.fixture.HoneyTipFixture;
import member.fixture.MemberFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HoneyTipScrapServiceTest {

    private HoneyTipScrapService honeyTipScrapService;
    private HoneyTipScrapRepository honeyTipScrapRepository;

    @BeforeEach
    void setUp() {
        honeyTipScrapRepository = new FakeHoneyTipScrapRepository();
        honeyTipScrapService = new HoneyTipScrapService(honeyTipScrapRepository);
    }

    @Test
    void 스크랩이_정상적으로_등록된다() {
        // given
        HoneyTip honeyTip = HoneyTipFixture.타인_허니팁_생성();
        Member member = MemberFixture.일반_회원_생성1();

        // when
        honeyTipScrapService.register(honeyTip, member);

        // then
        assertSoftly(softly -> {
            softly.assertThat(honeyTipScrapRepository.countHoneyTipScrapsByHoneyTipId(honeyTip.getId())).isEqualTo(1);
            softly.assertThat(honeyTipScrapRepository.existsByHoneyTipIdAndMemberId(honeyTip.getId(), member.getId())).isTrue();
        });
    }

    @Test
    void 스크랩이_정상적으로_취소된다() {
        // given
        HoneyTip honeyTip = HoneyTipFixture.타인_허니팁_생성();
        Member member = MemberFixture.일반_회원_생성1();
        honeyTipScrapService.register(honeyTip, member);

        // when
        honeyTipScrapService.cancelScrap(honeyTip, member.getId());

        // then
        assertSoftly(softly -> {
            softly.assertThat(honeyTipScrapRepository.countHoneyTipScrapsByHoneyTipId(honeyTip.getId())).isEqualTo(0);
            softly.assertThat(honeyTipScrapRepository.existsByHoneyTipIdAndMemberId(honeyTip.getId(), member.getId())).isFalse();
        });
    }

    @Test
    void 스크랩이_존재하지_않을때_취소시_에러발생() {
        // given
        HoneyTip honeyTip = HoneyTipFixture.타인_허니팁_생성();
        Long memberId = 123L;

        // when & then
        assertThrows(ApiException.class, () -> {
            honeyTipScrapService.cancelScrap(honeyTip, memberId);
        });
    }

    @Test
    void 특정_허니팁의_스크랩_수를_정상적으로_반환한다() {
        // given
        HoneyTip honeyTip = HoneyTipFixture.타인_허니팁_생성();
        Member member1 = MemberFixture.일반_회원_생성1();
        Member member2 = MemberFixture.일반_회원_생성2();
        honeyTipScrapService.register(honeyTip, member1);
        honeyTipScrapService.register(honeyTip, member2);

        // when
        Long scrapCount = honeyTipScrapService.countHoneyTipScraps(honeyTip.getId());

        // then
        assertSoftly(softly -> {
            softly.assertThat(scrapCount).isEqualTo(2);
        });
    }
}
