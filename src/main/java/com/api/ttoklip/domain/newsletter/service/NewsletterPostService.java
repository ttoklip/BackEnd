package com.api.ttoklip.domain.newsletter.service;

import com.api.ttoklip.domain.common.Category;
import com.api.ttoklip.domain.newsletter.domain.Newsletter;
import com.api.ttoklip.domain.newsletter.repository.post.NewsletterRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NewsletterPostService {

    private final NewsletterRepository newsletterRepository;

    @Transactional
    public void saveNewsletter(final Newsletter newsletter) {
        newsletterRepository.save(newsletter);
    }

    public Newsletter getNewsletter(final Long postId) {
        return newsletterRepository.findByIdActivated(postId);
    }

    public Long getEntityCount() {
        return newsletterRepository.findNewsletterCount();
    }

    public Newsletter findNewsletterWithDetails(final Long postId) {
        return newsletterRepository.findByIdFetchJoin(postId);
    }

    public Page<Newsletter> getPaging(final Category category, final Pageable pageable) {
        return newsletterRepository.getPaging(category, pageable);
    }

    public List<Newsletter> findRandom4ActiveNewsletters() {
        return newsletterRepository.findRandom4ActiveNewsletters();
    }

    public List<Newsletter> getRecent3() {
        return newsletterRepository.getRecent3();
    }
}
