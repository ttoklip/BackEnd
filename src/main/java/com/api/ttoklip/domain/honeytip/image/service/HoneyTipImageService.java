package com.api.ttoklip.domain.honeytip.image.service;

import com.api.ttoklip.domain.honeytip.image.domain.HoneyTipImage;
import com.api.ttoklip.domain.honeytip.image.repository.HoneyTipImageRepository;
import com.api.ttoklip.domain.honeytip.post.domain.HoneyTip;
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
    public void deleteAllByPostId(final Long honeyTipId) {
        // 이미지 삭제
        honeyTipImageRepository.deleteAllByHoneyTipId(honeyTipId);
    }

}
