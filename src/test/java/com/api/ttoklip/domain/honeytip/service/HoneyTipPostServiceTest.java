package com.api.ttoklip.domain.honeytip.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.api.ttoklip.domain.common.Category;
import com.api.ttoklip.domain.honeytip.controller.dto.request.HoneyTipCreateRequest;
import com.api.ttoklip.domain.honeytip.domain.HoneyTip;
import com.api.ttoklip.domain.honeytip.repository.post.HoneyTipRepository;
import com.api.ttoklip.global.config.QuerydslConfig;
import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;


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
    void uploadImages() {
    }

    @Test
    @DisplayName("작성자가 아니면 수정하거나 삭제할 권한이 없다.")
    void checkEditPermissionForbidden() {
        // given
        Long honeyTipId = 900L; // SQL에서 삽입된 HoneyTip의 ID
        Long anotherMemberId = 1002L; // SQL에서 삽입된 다른 멤버의 ID
        HoneyTip honeytip = honeyTipPostService.getHoneytip(honeyTipId);

        assertThatThrownBy(
                () -> honeyTipPostService.checkEditPermission(honeytip, anotherMemberId)
        )
                .isInstanceOf(ApiException.class)
                .satisfies(ex -> {
                    ApiException apiException = (ApiException) ex;
                    assertThat(apiException.getErrorType()).isEqualTo(ErrorType.UNAUTHORIZED_EDIT_POST);
                });
    }

    @Test
    @DisplayName("작성자가 맞으면 수정하거나 삭제할 수 있다.")
    void checkEditPermissionSuccess() {
        // given
        Long honeyTipId = 900L;
        Long authorId = 3001L;
        HoneyTip honeytip = honeyTipPostService.getHoneytip(honeyTipId);

        // when & then
        honeyTipPostService.checkEditPermission(honeytip, authorId);
        // 예외가 발생하지 않으면 테스트 성공
        assertThat(honeytip.getMember().getId()).isEqualTo(authorId);
    }

    @Test
    @Transactional
    @DisplayName("HoneyTip 게시글을 저장한다.")
    void saveHoneyTipPost() {
        // given
        HoneyTipCreateRequest request = HoneyTipCreateRequest.builder()
                .title("꿀팁공유해요 제목 테스트ABCDSFGMLMLMLML")
                .content("내용")
                .category(Category.RECIPE.name())
                .build();

        HoneyTip honeyTip = HoneyTip.of(request, null);
        System.out.println("falg0 honeyTip.getId() = " + honeyTip.getId());

        // when
        honeyTipPostService.saveHoneyTipPost(honeyTip);
        System.out.println("falg1 honeyTip.getId() = " + honeyTip.getId());

        // then
        HoneyTip savedHoneyTip = honeyTipRepository.findById(honeyTip.getId()).orElse(null);
        System.out.println("falg2 honeyTip.getId() = " + honeyTip.getId());
        assertThat(savedHoneyTip).isNotNull();
        assertThat(savedHoneyTip.getTitle()).isEqualTo(request.getTitle());
        assertThat(savedHoneyTip.getContent()).isEqualTo(request.getContent());
        assertThat(savedHoneyTip.getCategory()).isEqualTo(Category.RECIPE);
    }

    @Test
    @Transactional
    @DisplayName("HoneyTip url, image가 존재하는 글을 FetchJoin 조회했을 때 함께 조회해온다.")
    void findHoneyTipWithDetails() {
        // given
        Long honeyTipId = 3200L; // SQL에서 삽입된 HoneyTip의 ID

        // when
        HoneyTip honeyTipWithDetails = honeyTipPostService.findHoneyTipWithDetails(honeyTipId);

        // then
        assertThat(honeyTipWithDetails).isNotNull();
        assertThat(honeyTipWithDetails.getHoneyTipImageList()).isNotEmpty();
        assertThat(honeyTipWithDetails.getHoneyTipUrlList()).isNotEmpty();
        assertThat(honeyTipWithDetails.getMember()).isNotNull();
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