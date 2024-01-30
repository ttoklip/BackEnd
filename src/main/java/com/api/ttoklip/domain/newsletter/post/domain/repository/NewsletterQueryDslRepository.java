package com.api.ttoklip.domain.newsletter.post.domain.repository;

import com.api.ttoklip.domain.common.Category;
import com.api.ttoklip.domain.newsletter.post.domain.Newsletter;

import java.util.List;

public interface NewsletterQueryDslRepository {
    List<Newsletter> findLatestNewslettersByCategory(Category category, int limit);

    List<Newsletter> findRandomNewslettersByCategory(Category category, int limit);
}
