package com.domain.newsletter.infrastructure;

import com.domain.newsletter.domain.NewsletterScrap;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsletterScrapJpaRepository extends JpaRepository<NewsletterScrap, Long> {

    Optional<NewsletterScrap> findByNewsletterIdAndMemberId(Long newsletterId, Long memberId);

    boolean existsByNewsletterIdAndMemberId(Long postId, Long memberId);
}
