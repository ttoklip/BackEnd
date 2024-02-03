package com.api.ttoklip.domain.honeytip.url.service;

import com.api.ttoklip.domain.honeytip.post.domain.HoneyTip;
import com.api.ttoklip.domain.honeytip.url.domain.HoneyTipUrl;
import com.api.ttoklip.domain.honeytip.url.repository.HoneyTipUrlRepository;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HoneyTipUrlService {
    private final HoneyTipUrlRepository honeyTipUrlRepository;

    public void register(final HoneyTip honeyTip, final String url) {
        HoneyTipUrl honeyTipUrl = HoneyTipUrl.of(honeyTip, url);
        honeyTipUrlRepository.save(honeyTipUrl);
    }

    public void updateHoneyTipUrls(final HoneyTip honeyTip, final List<String> newUrls) {
        Long honeyTipId = honeyTip.getId();
        List<HoneyTipUrl> existingUrls = honeyTipUrlRepository.findByHoneyTipId(honeyTipId);

        Set<String> existingUrlSet = existingUrls.stream()
                .map(HoneyTipUrl::getUrl)
                .collect(Collectors.toSet());

        registerNewUrl(honeyTip, newUrls, existingUrlSet);
        removeOriginUrl(newUrls, existingUrls);
    }

    private void registerNewUrl(final HoneyTip honeyTip, final List<String> newUrls, final Set<String> existingUrlSet) {
        // 새 URL 추가
        List<HoneyTipUrl> honeyTipUrlsToSave = newUrls.stream()
                .filter(url -> !existingUrlSet.contains(url))
                .map(url -> HoneyTipUrl.of(honeyTip, url))
                .toList();

        if (!honeyTipUrlsToSave.isEmpty()) {
            honeyTipUrlRepository.saveAll(honeyTipUrlsToSave);
        }
    }

    private void removeOriginUrl(final List<String> newUrls, final List<HoneyTipUrl> existingUrls) {
        List<Long> idsToDelete = existingUrls.stream()
                .filter(honeyTipUrl -> !newUrls.contains(honeyTipUrl.getUrl()))
                .map(HoneyTipUrl::getId)
                .toList();

        // 기존 URL 삭제
        if (!idsToDelete.isEmpty()) {
            honeyTipUrlRepository.deleteAllByIds(idsToDelete);
        }
    }

}
