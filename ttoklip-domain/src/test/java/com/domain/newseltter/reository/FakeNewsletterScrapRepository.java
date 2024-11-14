package com.domain.newseltter.reository;

import com.domain.newsletter.domain.Newsletter;
import com.domain.newsletter.domain.NewsletterScrap;
import com.domain.newsletter.domain.NewsletterScrapRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class FakeNewsletterScrapRepository implements NewsletterScrapRepository {

    private final HashMap<Long, NewsletterScrap> memoryRepository = new HashMap<>();
    private Long idCounter = 1L; // ID를 1부터 시작

    @Override
    public Optional<NewsletterScrap> findByNewsletterIdAndMemberId(final Long newsletterId, final Long memberId) {
        return memoryRepository.values().stream()
                .filter(newsletterScrap -> newsletterScrap.getNewsletter().getId().equals(newsletterId)
                        && newsletterScrap.getMember().getId().equals(memberId))
                .findFirst();
    }

    @Override
    public boolean existsByNewsletterIdAndMemberId(final Long newsletterId, final Long memberId) {
        return memoryRepository.values().stream()
                .anyMatch(newsletterScrap -> newsletterScrap.getNewsletter().getId().equals(newsletterId)
                        && newsletterScrap.getMember().getId().equals(memberId));
    }

    @Override
    public Long countNewsletterScrapsByNewsletterId(final Long newsletterId) {
        return memoryRepository.values().stream()
                .filter(newsletterScrap -> newsletterScrap.getNewsletter().getId().equals(newsletterId))
                .count();
    }

    @Override
    public NewsletterScrap save(final NewsletterScrap newsletterScrap) {
        NewsletterScrap savedScrap = NewsletterScrap.builder()
                .id(idCounter++)  // ID 자동 증가
                .member(newsletterScrap.getMember())
                .newsletter(newsletterScrap.getNewsletter())
                .build();
        memoryRepository.put(savedScrap.getId(), savedScrap);
        return savedScrap;
    }

    @Override
    public void deleteById(final Long id) {
        memoryRepository.remove(id);
    }

    @Override
    public Page<Newsletter> getScrapPaging(final Long memberId, final Pageable pageable) {
        List<Newsletter> filteredNewsletters = memoryRepository.values().stream()
                .filter(newsletterScrap -> newsletterScrap.getMember().getId().equals(memberId))
                .map(NewsletterScrap::getNewsletter)
                .filter(newsletter -> !newsletter.isDeleted())
                .sorted((n1, n2) -> n2.getId().compareTo(n1.getId())) // 최신순 정렬
                .toList();

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), filteredNewsletters.size());

        return new PageImpl<>(filteredNewsletters.subList(start, end), pageable, filteredNewsletters.size());
    }

}
