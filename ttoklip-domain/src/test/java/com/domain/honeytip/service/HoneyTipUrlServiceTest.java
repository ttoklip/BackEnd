package com.domain.honeytip.service;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.domain.honeytip.application.HoneyTipUrlService;
import com.domain.honeytip.domain.HoneyTip;
import com.domain.honeytip.domain.HoneyTipRepository;
import com.domain.honeytip.domain.HoneyTipUrl;
import com.domain.honeytip.domain.HoneyTipUrlRepository;
import com.domain.honeytip.repository.FakeHoneyTipPostRepository;
import com.domain.honeytip.repository.FakeHoneyTipUrlRepository;
import com.domain.member.domain.Member;
import honeytip.fixture.HoneyTipFixture;
import java.util.List;
import member.fixture.MemberFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class HoneyTipUrlServiceTest {

    private HoneyTipUrlService honeyTipUrlService;
    private HoneyTipUrlRepository honeyTipUrlRepository;

    private HoneyTipRepository postRepository;

    @BeforeEach
    void setUp() {
        honeyTipUrlRepository = new FakeHoneyTipUrlRepository();
        honeyTipUrlService = new HoneyTipUrlService(honeyTipUrlRepository);
        postRepository = new FakeHoneyTipPostRepository();
    }

    @Test
    void URL_등록_성공한다() {
        // given
        Member member = MemberFixture.일반_회원_생성1();
        HoneyTip honeyTipRequest = HoneyTipFixture.본인_허니팁_생성(member);
        HoneyTip honeyTip = postRepository.save(honeyTipRequest);

        String url = "http://example.com";

        // when
        honeyTipUrlService.register(honeyTip, url);

        // then
        List<HoneyTipUrl> savedUrls = honeyTipUrlRepository.findByHoneyTipId(honeyTip.getId());

        assertSoftly(softly -> {
            softly.assertThat(savedUrls.size()).isEqualTo(1);
            softly.assertThat(savedUrls.get(0).getUrl()).isEqualTo(url);
        });
    }

    @Test
    void URL_업데이트_기존_URL_삭제_및_새로운_URL_등록한다() {
        // given
        Member member = MemberFixture.일반_회원_생성1();
        HoneyTip honeyTipRequest = HoneyTipFixture.본인_허니팁_생성(member);
        HoneyTip honeyTip = postRepository.save(honeyTipRequest);

        List<String> newUrls = List.of("http://new-url1.com", "http://new-url2.com");

        // when
        honeyTipUrlService.updateHoneyTipUrls(honeyTip, newUrls);

        // then
        List<HoneyTipUrl> updatedUrls = honeyTipUrlRepository.findByHoneyTipId(honeyTip.getId());

        // 업데이트된 URL 확인
        assertSoftly(softly -> {
            softly.assertThat(updatedUrls.size()).isEqualTo(2); // 새로운 URL 2개만 있어야 함
            softly.assertThat(updatedUrls.get(0).getUrl()).isEqualTo("http://new-url1.com");
            softly.assertThat(updatedUrls.get(1).getUrl()).isEqualTo("http://new-url2.com");
        });
    }

    @Test
    void URL_업데이트_중복된_URL_제외하고_등록한다() {
        // given
        Member member = MemberFixture.일반_회원_생성1();
        HoneyTip honeyTipRequest = HoneyTipFixture.본인_허니팁_생성(member);
        HoneyTip honeyTip = postRepository.save(honeyTipRequest);

        honeyTipUrlService.register(honeyTip, "http://existing-url.com");

        List<String> newUrls = List.of("http://existing-url.com", "http://new-url.com");

        // when
        honeyTipUrlService.updateHoneyTipUrls(honeyTip, newUrls);

        // then
        List<HoneyTipUrl> updatedUrls = honeyTipUrlRepository.findByHoneyTipId(honeyTip.getId());

        assertSoftly(softly -> {
            softly.assertThat(updatedUrls.size()).isEqualTo(2);
            softly.assertThat(updatedUrls.stream().anyMatch(url -> url.getUrl().equals("http://existing-url.com")))
                    .isTrue();
            softly.assertThat(updatedUrls.stream().anyMatch(url -> url.getUrl().equals("http://new-url.com"))).isTrue();
        });
    }

    @Test
    void URL_업데이트_모두_삭제된_경우() {
        // given
        Member member = MemberFixture.일반_회원_생성1();
        HoneyTip honeyTipRequest = HoneyTipFixture.본인_허니팁_생성(member);
        HoneyTip honeyTip = postRepository.save(honeyTipRequest);

        honeyTipUrlService.register(honeyTip, "http://url1.com");
        honeyTipUrlService.register(honeyTip, "http://url2.com");

        List<String> newUrls = List.of();

        // when
        honeyTipUrlService.updateHoneyTipUrls(honeyTip, newUrls);

        // then
        List<HoneyTipUrl> updatedUrls = honeyTipUrlRepository.findByHoneyTipId(honeyTip.getId());

        assertSoftly(softly -> {
            softly.assertThat(updatedUrls.isEmpty()).isTrue();
        });
    }

}
