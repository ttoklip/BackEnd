package com.api.ttoklip.domain.honeytip.service;

import com.api.ttoklip.domain.honeytip.domain.HoneyTipComment;
import com.api.ttoklip.global.config.QuerydslConfig;
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
@Sql("/sql/honeytip-commentservice-test-data.sql")
class HoneyTipCommentServiceTest {

    @Autowired
    private HoneyTipCommentService honeyTipCommentService;

    @Test
    void findCommentsByHoneyTipId() {
        // Given
        Long honeyTipId = 1L;

        // When
        List<HoneyTipComment> comments = honeyTipCommentService.findCommentsByHoneyTipId(honeyTipId);

        // Then
        assertThat(comments).isNotEmpty();
        assertThat(comments.get(0).getHoneyTip().getId()).isEqualTo(honeyTipId);
        assertThat(comments.get(0).getMember()).isNotNull();
    }


}
