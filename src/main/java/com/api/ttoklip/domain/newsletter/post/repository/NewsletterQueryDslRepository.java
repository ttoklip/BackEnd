package com.api.ttoklip.domain.newsletter.post.repository;

import com.api.ttoklip.domain.newsletter.comment.domain.NewsletterComment;
import com.api.ttoklip.domain.newsletter.post.domain.Newsletter;
import com.api.ttoklip.domain.town.community.post.entity.Community;

import java.util.List;

public interface NewsletterQueryDslRepository {

    Newsletter findByIdFetchJoin(final Long postId);

    List<NewsletterComment> findActiveCommentsByNewsletterId(final Long postId);

    Long findNewsletterCount();

    Newsletter findByIdActivated(final Long newsletterId);

    Long countNewsletterScrapsByCommunityId(final Long newsletterId);

}
