package com.api.ttoklip.domain.newsletter.like.service;

import static com.api.ttoklip.global.util.SecurityUtil.getCurrentMember;

import com.api.ttoklip.domain.newsletter.like.entity.NewsletterLike;
import com.api.ttoklip.domain.newsletter.like.repository.NewsletterLikeRepository;
import com.api.ttoklip.domain.newsletter.post.domain.Newsletter;
import com.api.ttoklip.domain.newsletter.post.service.NewsletterCommonService;
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
    private final NewsletterCommonService newsletterCommonService;

    // 좋아요 생성
    public void registerLike(final Long newsletterId) {
        Long currentMemberId = getCurrentMember().getId();

        boolean exists = newsletterLikeRepository.existsByNewsletterIdAndMemberId(newsletterId, currentMemberId);

        if (exists) {
            return; // 이미 좋아요가 존재하면 스크랩을 생성하지 않고 return
        }

        Newsletter findNewsletter = newsletterCommonService.getNewsletter(newsletterId);
        NewsletterLike newsletterLike = NewsletterLike.from(findNewsletter);
        newsletterLikeRepository.save(newsletterLike);
    }

    // 좋아요 취소
    public void cancelLike(final Long newsletterId) {
        // NewsletterId (게시글 ID)
        Newsletter findNewsletter = newsletterCommonService.getNewsletter(newsletterId);
        Long findNewsletterId = findNewsletter.getId();
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

    public boolean existsByNewsletterIdAndMemberId(final Long postId) {
        Long currentMemberId = getCurrentMember().getId();
        return newsletterLikeRepository.existsByNewsletterIdAndMemberId(postId, currentMemberId);
    }
}
