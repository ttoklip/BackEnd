package com.api.ttoklip.domain.honeytip.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.api.ttoklip.domain.common.Category;
import com.api.ttoklip.domain.honeytip.domain.HoneyTip;
import com.api.ttoklip.domain.honeytip.repository.post.HoneyTipRepository;
import com.api.ttoklip.global.config.QuerydslConfig;
import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;


@ActiveProfiles("test")
@SpringBootTest
@Import(QuerydslConfig.class)
@TestPropertySource(properties = "JASYPT_ENCRYPTOR_PASSWORD=dummy")
@Sql("/sql/honeytip-reposiotry-test-data.sql")
class HoneyTipPostServiceTest {

    @Autowired
    private HoneyTipPostService honeyTipPostService;

    @Autowired
    private HoneyTipRepository honeyTipRepository;

    @Test
    @DisplayName("존재하는 HoneyTip 조회 요청을 했을 때, 조회한 HoneyTip은 활성화 상태이다.")
    void getHoneyTip() {
        HoneyTip honeytip = honeyTipPostService.getHoneytip(900L);
        assertThat(honeytip.isDeleted()).isFalse();
        assertThat(honeytip.getId()).isEqualTo(900L);
        assertThat(honeytip.getTitle()).isEqualTo("제목");
        assertThat(honeytip.getContent()).isEqualTo("내용");
        assertThat(honeytip.getCategory()).isEqualTo(Category.HOUSEWORK);
    }


    @Test
    @DisplayName("존재하지 않는 HoneyTip 조회 요청을 했을 때, ApiException이 발생하고, 에러 타입이 HONEY_TIP_NOT_FOUND이다.")
    void getHoneytipNotFound() {
        // given
        Long nonExistentHoneyTipId = 1001L;

        // when & then
        assertThatThrownBy(() -> honeyTipPostService.getHoneytip(nonExistentHoneyTipId))
                .isInstanceOf(ApiException.class)
                .satisfies(ex -> {
                    ApiException apiException = (ApiException) ex;
                    assertThat(apiException.getErrorType()).isEqualTo(ErrorType.HONEY_TIP_NOT_FOUND);
                });
    }

    @Test
    void checkEditPermission() {
    }

    @Test
    void saveHoneyTipPost() {
    }

    @Test
    void findHoneyTipWithDetails() {
    }

    @Test
    void findHouseworkTips() {
    }

    @Test
    void findRecipeTips() {
    }

    @Test
    void findSafeLivingTips() {
    }

    @Test
    void findWelfarePolicyTips() {
    }

    @Test
    void getPopularityTop5() {
    }

    @Test
    void findRecent3() {
    }

    @Test
    void matchCategoryPaging() {
    }
}