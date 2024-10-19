package com.domain.honeytip.service;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.api.ttoklip.domain.honeytip.domain.HoneyTip;
import com.api.ttoklip.domain.honeytip.domain.HoneyTipUrl;
import com.api.ttoklip.domain.honeytip.repository.url.HoneyTipUrlRepository;
import com.api.ttoklip.domain.honeytip.service.HoneyTipUrlService;
import com.domain.honeytip.repository.FakeHoneyTipUrlRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class HoneyTipUrlServiceTest {

    private HoneyTipUrlService honeyTipUrlService;
    private HoneyTipUrlRepository honeyTipUrlRepository;

    @BeforeEach
    void setUp() {
        honeyTipUrlRepository = new FakeHoneyTipUrlRepository();
        honeyTipUrlService = new HoneyTipUrlService(honeyTipUrlRepository);
    }

    @Test
    void URL_등록_성공() {
        // given
        HoneyTip honeyTip = HoneyTip.builder().id(1L).title("Test").build();
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
    void URL_업데이트_기존_URL_삭제_및_새로운_URL_등록() {
        // given
        HoneyTip honeyTip = HoneyTip.builder().id(1L).title("Test").build();
        honeyTipUrlService.register(honeyTip, "http://old-url.com"); // 기존 URL 등록

        // 새로운 URL 리스트 준비
        List<String> newUrls = List.of("http://new-url1.com", "http://new-url2.com");

        // when
        honeyTipUrlService.updateHoneyTipUrls(honeyTip, newUrls);

        // then
        List<HoneyTipUrl> updatedUrls = honeyTipUrlRepository.findByHoneyTipId(honeyTip.getId());

        assertSoftly(softly -> {
            softly.assertThat(updatedUrls.size()).isEqualTo(2);
            softly.assertThat(updatedUrls.stream().anyMatch(url -> url.getUrl().equals("http://new-url1.com")))
                    .isTrue();
            softly.assertThat(updatedUrls.stream().anyMatch(url -> url.getUrl().equals("http://new-url2.com")))
                    .isTrue();
            softly.assertThat(updatedUrls.stream().anyMatch(url -> url.getUrl().equals("http://old-url.com")))
                    .isFalse();
        });
    }

    @Test
    void URL_업데이트_중복된_URL_제외하고_등록() {
        // given
        HoneyTip honeyTip = HoneyTip.builder().id(1L).title("Test").build();
        honeyTipUrlService.register(honeyTip, "http://existing-url.com"); // 기존 URL 등록

        // 기존 URL과 동일한 URL 포함한 새로운 리스트
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
        HoneyTip honeyTip = HoneyTip.builder().id(1L).title("Test").build();
        honeyTipUrlService.register(honeyTip, "http://url1.com");
        honeyTipUrlService.register(honeyTip, "http://url2.com");

        // 새로 업데이트할 URL은 비어 있음 (모두 삭제)
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
