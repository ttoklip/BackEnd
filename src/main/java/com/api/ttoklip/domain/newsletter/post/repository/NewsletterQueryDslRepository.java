package com.api.ttoklip.domain.newsletter.post.repository;

import com.api.ttoklip.domain.newsletter.comment.domain.NewsletterComment;
import com.api.ttoklip.domain.newsletter.post.domain.Newsletter;
import java.util.List;

public interface NewsletterQueryDslRepository {

    Newsletter findByIdFetchJoin(final Long postId);

    List<NewsletterComment> findActiveCommentsByNewsletterId(final Long postId);

    Long findNewsletterCount();
}
