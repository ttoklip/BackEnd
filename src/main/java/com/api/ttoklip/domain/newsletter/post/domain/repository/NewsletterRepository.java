package com.api.ttoklip.domain.newsletter.post.domain.repository;

import com.api.ttoklip.domain.common.Category;
import com.api.ttoklip.domain.newsletter.post.domain.Newsletter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsletterRepository extends JpaRepository<Newsletter, Long> {
    List<Newsletter> findLatestNewslettersByCategory(Category category, int i);

    List<Newsletter> findRandomNewslettersByCategory(Category category, int i);
}
