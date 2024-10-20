package com.api.ttoklip.domain.newsletter.service;

import static com.api.ttoklip.global.util.SecurityUtil.getCurrentMember;

import com.api.ttoklip.domain.newsletter.domain.Newsletter;
import com.api.ttoklip.domain.newsletter.domain.NewsletterLike;
import com.api.ttoklip.domain.newsletter.repository.NewsletterLikeRepository;
import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NewsletterLikeService {

    private final NewsletterLikeRepository newsletterLikeRepository;

    public boolean isNewsletterExists(final Long newsletterId) {
        Long currentMemberId = getCurrentMember().getId();
        return newsletterLikeRepository.existsByNewsletterIdAndMemberId(newsletterId, currentMemberId);
    }

    // 좋아요 생성
    public void registerLike(final Newsletter newsletter) {
        NewsletterLike newsletterLike = NewsletterLike.from(newsletter);
        newsletterLikeRepository.save(newsletterLike);
    }

    // 좋아요 취소
    public void cancelLike(final Newsletter newsletter) {
        // NewsletterId (게시글 ID)
        Long findNewsletterId = newsletter.getId();
        Long currentMemberId = getCurrentMember().getId();

        NewsletterLike newsletterLike = newsletterLikeRepository.findByNewsletterIdAndMemberId(findNewsletterId,
                        currentMemberId)
                .orElseThrow(() -> new ApiException(ErrorType.LIKE_NOT_FOUND));

        // 자격 검증: 이 단계에서는 findByNewsletterIdAndMemberId 결과가 존재하므로, 현재 사용자가 스크랩을 누른 것입니다.
        // 별도의 자격 검증 로직이 필요 없으며, 바로 삭제를 진행할 수 있습니다.
        newsletterLikeRepository.deleteById(newsletterLike.getId());
    }

    public Long countNewsletterLikes(final Long newsletterId) {
        return newsletterLikeRepository.countNewsletterLikesByNewsletterId(newsletterId);
    }

}
