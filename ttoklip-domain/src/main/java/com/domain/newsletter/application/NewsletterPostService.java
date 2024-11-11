package com.domain.newsletter.application;

import com.domain.common.vo.Category;
import com.domain.newsletter.domain.Newsletter;
import com.domain.newsletter.domain.NewsletterRepository;
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

    public List<Newsletter> findRandomActiveNewsletters(final int pageSize) {
        return newsletterRepository.findRandomActiveNewsletters(pageSize);
    }

    public List<Newsletter> getRecent3() {
        return newsletterRepository.getRecent3();
    }
}
