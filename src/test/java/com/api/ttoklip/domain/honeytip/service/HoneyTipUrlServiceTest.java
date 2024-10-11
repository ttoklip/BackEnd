package com.api.ttoklip.domain.honeytip.service;

import com.api.ttoklip.domain.honeytip.domain.HoneyTip;
import com.api.ttoklip.domain.honeytip.domain.HoneyTipUrl;
import com.api.ttoklip.domain.honeytip.repository.url.HoneyTipUrlRepository;
import com.api.ttoklip.global.config.QuerydslConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
@Import(QuerydslConfig.class)
@TestPropertySource(properties = "JASYPT_ENCRYPTOR_PASSWORD=dummy")
@Sql("/sql/honeytip-urlservice-test-data.sql")
class HoneyTipUrlServiceTest {

    @Autowired
    private HoneyTipUrlService honeyTipUrlService;

    @Autowired
    private HoneyTipUrlRepository honeyTipUrlRepository;

    @Autowired
    private HoneyTipPostService honeyTipPostService;

    @Test
    void testRegisterNewUrl() {
        // 새로운 URL을 등록
        String newUrl = "http://example.com/3";
        HoneyTip honeytip = honeyTipPostService.getHoneytip(1L);
        honeyTipUrlService.register(honeytip, newUrl);

        // 등록된 URL 확인
        List<HoneyTipUrl> urls = honeyTipUrlRepository.findByHoneyTipId(honeytip.getId());
        assertThat(urls).hasSize(3);
        assertThat(urls.stream().anyMatch(url -> url.getUrl().equals(newUrl))).isTrue();
    }

    @Test
    void testUpdateHoneyTipUrls_AddNewAndRemoveOld() {
        HoneyTip honeytip = honeyTipPostService.getHoneytip(1L);

        // 새로운 URL 목록 설정
        List<String> newUrls = List.of("http://example.com/1", "http://example.com/3");
        honeyTipUrlService.updateHoneyTipUrls(honeytip, newUrls);

        // 업데이트된 URL 확인
        List<HoneyTipUrl> urls = honeyTipUrlRepository.findByHoneyTipId(honeytip.getId());
        assertThat(urls).hasSize(2);
        assertThat(urls.stream().anyMatch(url -> url.getUrl().equals("http://example.com/3"))).isTrue();
        assertThat(urls.stream().anyMatch(url -> url.getUrl().equals("http://example.com/2"))).isFalse();
    }
}
