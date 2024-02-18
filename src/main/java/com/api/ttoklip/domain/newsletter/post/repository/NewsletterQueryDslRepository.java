package com.api.ttoklip.domain.newsletter.post.repository;

import com.api.ttoklip.domain.common.Category;
import com.api.ttoklip.domain.newsletter.comment.domain.NewsletterComment;
import com.api.ttoklip.domain.newsletter.post.domain.Newsletter;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NewsletterQueryDslRepository {

    Newsletter findByIdActivated(final Long newsletterId);

    Newsletter findByIdFetchJoin(final Long postId);

    List<NewsletterComment> findActiveCommentsByNewsletterId(final Long postId);

    Long findNewsletterCount();

    Page<Newsletter> getPaging(final Category category, final Pageable pageable);
}
