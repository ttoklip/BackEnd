package com.domain.newsletter.application;

import com.common.exception.ApiException;
import com.common.exception.ErrorType;
import com.domain.member.domain.Member;
import com.domain.newsletter.domain.Newsletter;
import com.domain.newsletter.domain.NewsletterScrap;
import com.domain.newsletter.domain.NewsletterScrapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NewsletterScrapService {

    private final NewsletterScrapRepository newsletterScrapRepository;

    public boolean isNewsletterExists(final Long newsletterId, final Long memberId) {
        return newsletterScrapRepository.existsByNewsletterIdAndMemberId(newsletterId, memberId);
    }

    public void register(final Newsletter newsletter, final Member member) {
        NewsletterScrap newsletterScrap = NewsletterScrap.of(newsletter, member);
        newsletterScrapRepository.save(newsletterScrap);
    }

    public void cancelScrap(final Newsletter newsletter, final Long currentMemberId) {
        Long findNewsletterId = newsletter.getId();

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

    public Page<Newsletter> getScrapPaging(final Long memberId, final Pageable pageable) {
        return newsletterScrapRepository.getScrapPaging(memberId, pageable);
    }
}
