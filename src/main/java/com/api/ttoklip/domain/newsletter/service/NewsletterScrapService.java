package com.api.ttoklip.domain.newsletter.service;

import static com.api.ttoklip.global.util.SecurityUtil.getCurrentMember;

import com.api.ttoklip.domain.newsletter.domain.Newsletter;
import com.api.ttoklip.domain.newsletter.domain.NewsletterScrap;
import com.api.ttoklip.domain.newsletter.repository.NewsletterScrapRepository;
import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NewsletterScrapService {

    private final NewsletterScrapRepository newsletterScrapRepository;

    public boolean isNewsletterExists(final Long newsletterId) {
        Long currentMemberId = getCurrentMember().getId();
        return newsletterScrapRepository.existsByNewsletterIdAndMemberId(newsletterId, currentMemberId);
    }

    // 스크랩 생성
    public void register(final Newsletter newsletter) {
        NewsletterScrap newsletterScrap = NewsletterScrap.from(newsletter);
        newsletterScrapRepository.save(newsletterScrap);
    }

    // 스크랩 취소
    public void cancelScrap(final Newsletter newsletter) {
        // NewsletterId (게시글 ID)
        Long findNewsletterId = newsletter.getId();
        Long currentMemberId = getCurrentMember().getId();

        NewsletterScrap newsletterScrap = newsletterScrapRepository.findByNewsletterIdAndMemberId(findNewsletterId,
                        currentMemberId)
                .orElseThrow(() -> new ApiException(ErrorType.SCRAP_NOT_FOUND));

        // 자격 검증: 이 단계에서는 findByNewsletterIdAndMemberId 결과가 존재하므로, 현재 사용자가 스크랩을 누른 것입니다.
        // 별도의 자격 검증 로직이 필요 없으며, 바로 삭제를 진행할 수 있습니다.
        newsletterScrapRepository.deleteById(newsletterScrap.getId());
    }

    public Long countNewsletterScraps(final Long newsletterId) {
        return newsletterScrapRepository.countNewsletterScrapsByNewsletterId(newsletterId);
    }
}
