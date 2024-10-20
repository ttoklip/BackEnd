package com.domain.honeytip.repository;

import com.api.ttoklip.domain.honeytip.domain.HoneyTipImage;

import com.api.ttoklip.domain.honeytip.repository.image.HoneyTipImageRepository;
import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FakeHoneyTipImageRepository implements HoneyTipImageRepository {

    private final Map<Long, HoneyTipImage> honeyTipImageRepository = new HashMap<>();
    private Long honeyTipImageId = 0L;

    @Override
    public HoneyTipImage save(final HoneyTipImage honeyTipImage) {
        honeyTipImageId++;  // ID 자동 증가
        HoneyTipImage savedHoneyTipImage = HoneyTipImage.builder()
                .id(honeyTipImageId)
                .honeyTip(honeyTipImage.getHoneyTip())
                .url(honeyTipImage.getUrl())
                .build();

        honeyTipImageRepository.put(honeyTipImageId, savedHoneyTipImage);
        return savedHoneyTipImage;
    }

    @Override
    public boolean existsByHoneyTipIdAndUrl(final Long honeyTipId, final String url) {
        return honeyTipImageRepository.values().stream()
                .anyMatch(honeyTipImage -> honeyTipImage.getHoneyTip().getId().equals(honeyTipId) &&
                        honeyTipImage.getUrl().equals(url));
    }

    @Override
    public void verifyMemberIsImageOwner(final List<Long> imageIds, final Long memberId) {
        List<HoneyTipImage> honeyTipImages = honeyTipImageRepository.values().stream()
                .filter(honeyTipImage -> imageIds.contains(honeyTipImage.getId()))
                .toList();

        boolean isOwner = honeyTipImages.stream()
                .allMatch(image -> image.getHoneyTip().getMember().getId().equals(memberId));

        if (!isOwner) {
            throw new ApiException(ErrorType.INVALID_DELETE_IMAGE_OWNER);
        }
    }

    @Override
    public boolean doAllImageIdsExist(final List<Long> imageIds) {
        long count = honeyTipImageRepository.values().stream()
                .filter(image -> imageIds.contains(image.getId()))
                .count();

        return count == imageIds.size();
    }

    @Override
    public void deleteByImageIds(final List<Long> imageIds) {
        Map<Long, HoneyTipImage> backup = new HashMap<>(honeyTipImageRepository);

        try {
            imageIds.forEach(honeyTipImageRepository::remove);
        } catch (Exception e) {
            // 예외 발생 시 롤백
            honeyTipImageRepository.clear();
            honeyTipImageRepository.putAll(backup);

            throw new RuntimeException("이미지 삭제 중 예외 발생", e);
        }
    }
}
