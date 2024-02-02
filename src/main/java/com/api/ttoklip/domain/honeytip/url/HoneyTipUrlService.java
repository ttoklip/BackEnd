package com.api.ttoklip.domain.honeytip.url;

import com.api.ttoklip.domain.honeytip.post.image.domain.HoneyTipImage;
import com.api.ttoklip.domain.honeytip.post.post.domain.HoneyTip;
import com.api.ttoklip.domain.honeytip.url.domain.HoneyTipUrl;
import com.api.ttoklip.domain.honeytip.url.repository.HoneyTipUrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HoneyTipUrlService {
    private final HoneyTipUrlRepository honeyTipUrlRepository;

    @Transactional
    public void register(final HoneyTip honeyTip, final String url) {
        HoneyTipUrl honeyTipUrl = HoneyTipUrl.of(honeyTip, url);
        honeyTipUrlRepository.save(honeyTipUrl);
    }
}
