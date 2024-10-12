package com.api.ttoklip.domain.honeytip.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.api.ttoklip.domain.honeytip.domain.HoneyTip;
import com.api.ttoklip.domain.honeytip.repository.image.HoneyTipImageRepository;
import com.api.ttoklip.global.config.QuerydslConfig;
import com.api.ttoklip.global.exception.ApiException;
import java.util.List;
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
@Sql("/sql/honeytip-Imageservice-test-data.sql")
class HoneyTipImageServiceTest {

    @Autowired
    private HoneyTipImageService honeyTipImageService;

    @Autowired
    private HoneyTipPostService honeyTipPostService;

    @Autowired
    private HoneyTipImageRepository honeyTipImageRepository;

    @Test
    @Transactional
    void register() {
        // Given
        HoneyTip honeytip = honeyTipPostService.getHoneytip(3000L);
        String uploadUrl = "http://image-server.com/test-image.jpg";

        // When
        honeyTipImageService.register(honeytip, uploadUrl);

        // Then
        boolean imageExists = honeyTipImageRepository.existsByHoneyTipIdAndUrl(honeytip.getId(), uploadUrl);
        assertThat(imageExists).isTrue();
    }

    @Test
    @Transactional
    void deleteImages() {
        // Given
        Long honeyTipId = 3000L;  // 4000, 4001 이미지가 연결된 허니팁 ID
        HoneyTip honeytip = honeyTipPostService.getHoneytip(honeyTipId);
        List<Long> imageIdsToDelete = List.of(4000L, 4001L);  // 삭제할 이미지 ID 목록

        // When
        honeyTipImageService.deleteImages(imageIdsToDelete, honeytip.getMember().getId()); // 현재 허니팁 작성자의 ID로 삭제 시도

        // Then
        boolean imagesExistAfterDelete =
                honeyTipImageRepository.existsById(4000L) || honeyTipImageRepository.existsById(4001L);
        assertThat(imagesExistAfterDelete).isFalse();  // 이미지가 삭제되었는지 확인
    }


    @Test
    void deleteImages_withInvalidMemberThrowsException() {
        // Given
        List<Long> imageIdsToDelete = List.of(4000L, 4001L);
        Long invalidMemberId = 2001L;  // 다른 사용자가 삭제를 시도

        // When & Then
        assertThrows(ApiException.class, () -> honeyTipImageService.deleteImages(imageIdsToDelete, invalidMemberId));
    }
}
