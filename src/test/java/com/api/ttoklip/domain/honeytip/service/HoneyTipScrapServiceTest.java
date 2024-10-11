package com.api.ttoklip.domain.honeytip.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

import com.api.ttoklip.domain.honeytip.domain.HoneyTip;
import com.api.ttoklip.domain.member.domain.Member;
import com.api.ttoklip.domain.member.service.MemberService;
import com.api.ttoklip.global.config.QuerydslConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@SpringBootTest
@Import(QuerydslConfig.class)
@TestPropertySource(properties = "JASYPT_ENCRYPTOR_PASSWORD=dummy")
@Sql("/sql/honeytip-scrapservice-test-data.sql")
class HoneyTipScrapServiceTest {

    @Autowired
    private HoneyTipScrapService honeyTipScrapService;

    @Autowired
    private HoneyTipPostService honeyTipPostService;

    @Autowired
    private MemberService memberService;

    @Test
    void isHoneyTipScrapExists() {

        Long currentMemberId = 1L;
        HoneyTip honeytip = honeyTipPostService.getHoneytip(1L);
        boolean exists = honeyTipScrapService.isHoneyTipScrapExists(honeytip.getId(), currentMemberId);

        // Then: 스크랩이 존재해야 합니다.
        assertThat(exists).isTrue();
    }

    @Test
    @Transactional
    void register() {
        Member member = memberService.findById(1L);
        HoneyTip honeytip = honeyTipPostService.getHoneytip(1L);

        // When
        honeyTipScrapService.register(honeytip, member);

        // Then
        boolean exists = honeyTipScrapService.isHoneyTipScrapExists(honeytip.getId(), member.getId());
        assertThat(exists).isTrue();
    }

    @Test
    @Transactional
    void cancelScrap() {
        // Given
        Member member = memberService.findById(1L);
        HoneyTip honeytip = honeyTipPostService.getHoneytip(1L);

        // When
        honeyTipScrapService.cancelScrap(honeytip, member.getId());

        // Then
        boolean exists = honeyTipScrapService.isHoneyTipScrapExists(honeytip.getId(), member.getId());
        assertThat(exists).isFalse();
    }

    @Test
    void countHoneyTipScraps() {
        // Given
        HoneyTip honeytip = honeyTipPostService.getHoneytip(1L);

        // When
        Long count = honeyTipScrapService.countHoneyTipScraps(honeytip.getId());

        // Then
        assertThat(count).isEqualTo(1);
    }
}