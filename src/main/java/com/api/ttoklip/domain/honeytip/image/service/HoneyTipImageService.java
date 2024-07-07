package com.api.ttoklip.domain.honeytip.image.service;

import static com.api.ttoklip.global.util.SecurityUtil.getCurrentMember;

import com.api.ttoklip.domain.honeytip.image.domain.HoneyTipImage;
import com.api.ttoklip.domain.honeytip.image.repository.HoneyTipImageRepository;
import com.api.ttoklip.domain.honeytip.post.domain.HoneyTip;
import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HoneyTipImageService {
    private final HoneyTipImageRepository honeyTipImageRepository;

    @Transactional
    public void register(final HoneyTip honeyTip, final String uploadUrl) {
        HoneyTipImage honeyTipImage = HoneyTipImage.of(honeyTip, uploadUrl);
        honeyTipImageRepository.save(honeyTipImage);
    }

    @Transactional
    public void deleteImages(final List<Long> imageIds) {
        validImagesExists(imageIds);
        honeyTipImageRepository.allImageOwner(imageIds, getCurrentMember().getId());
        honeyTipImageRepository.deleteByImageIds(imageIds);
    }

    // 기존 이미지가 DB에 존재하는 이미지들인지?
    private void validImagesExists(final List<Long> imageIds) {
        boolean allImageIdsExist = honeyTipImageRepository.doAllImageIdsExist(imageIds);
        if (!allImageIdsExist) {
            throw new ApiException(ErrorType.DELETE_INVALID_IMAGE_IDS);
        }
    }
}
