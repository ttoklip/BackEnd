package com.api.ttoklip.domain.honeytip.service;

import static org.assertj.core.api.Assertions.assertThat;

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
@Sql("/sql/honneytip-likeservice-test-data.sql")
class HoneyTipLikeServiceTest {

    @Autowired
    private HoneyTipLikeService honeyTipLikeService;

    @Autowired
    private HoneyTipPostService honeyTipPostService;

    @Autowired
    private MemberService memberService;

    @Test
    void isHoneyTipLikeExists() {
        Long memberId = 2000L;
        HoneyTip honeyTip = honeyTipPostService.getHoneytip(3000L);
        boolean exists = honeyTipLikeService.isHoneyTipLikeExists(honeyTip.getId(), memberId);

        // Then: 좋아요가 존재해야 합니다.
        assertThat(exists).isTrue();
    }

    @Test
    @Transactional
    void register() {
        Member member = memberService.findById(2000L);
        HoneyTip honeyTip = honeyTipPostService.getHoneytip(3000L);

        // When
        honeyTipLikeService.register(honeyTip, member);

        // Then
        boolean exists = honeyTipLikeService.isHoneyTipLikeExists(honeyTip.getId(), member.getId());
        assertThat(exists).isTrue();
    }

    @Test
    @Transactional
    void cancel() {
        // Given
        Member member = memberService.findById(2000L);
        HoneyTip honeyTip = honeyTipPostService.getHoneytip(3000L);

        // When
        honeyTipLikeService.cancel(honeyTip, member.getId());

        // Then
        boolean exists = honeyTipLikeService.isHoneyTipLikeExists(honeyTip.getId(), member.getId());
        assertThat(exists).isFalse();
    }

    @Test
    void countHoneyTipLikes() {
        // Given
        HoneyTip honeyTip = honeyTipPostService.getHoneytip(3000L);

        // When
        Long count = honeyTipLikeService.countHoneyTipLikes(honeyTip.getId());

        // Then
        assertThat(count).isEqualTo(1);
    }
}