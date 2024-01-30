package com.api.ttoklip.domain.newsletter.post.domain.repository;

import com.api.ttoklip.domain.common.Category;
import com.api.ttoklip.domain.newsletter.post.domain.Newsletter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface NewsletterRepository extends JpaRepository<Newsletter, Long>, NewsletterQueryDslRepository {

}
