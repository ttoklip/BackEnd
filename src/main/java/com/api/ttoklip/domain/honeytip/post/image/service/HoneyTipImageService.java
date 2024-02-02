package com.api.ttoklip.domain.honeytip.post.image.service;

import com.api.ttoklip.domain.honeytip.post.image.domain.HoneyTipImage;
import com.api.ttoklip.domain.honeytip.post.image.repository.HoneyTipImageRepository;
import com.api.ttoklip.domain.honeytip.post.post.domain.HoneyTip;
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
}
